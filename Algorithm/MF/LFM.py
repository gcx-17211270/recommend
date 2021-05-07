#!/usr/bin/env python
# -*- coding: utf-8 -*-

import collections
import os
import random
import sys
from operator import itemgetter

import math

from collections import defaultdict

from MF.utils import ModelManager
from MF.utils import LogTime


class LFM:
    """
    Latent Factor Model.
    """

    def __init__(self, K, epochs, alpha, lamb, n_rec_movie=10, save_model=True):
        """
        Init LFM with K, T, alpha, lamb
        :param K: Latent Factor dimension
        :param epochs: epochs to go
        :param alpha: study rate
        :param lamb: regular params
        :param save_model: save model
        """
        print("LFM start...\n")
        self.K = K
        self.epochs = epochs
        self.alpha = alpha
        self.lamb = lamb
        self.n_rec_movie = n_rec_movie
        self.save_model = save_model
        self.users_set, self.items_set = set(), set()
        self.items_list = list()
        self.P, self.Q = None, None
        self.trainset = None
        self.testset = None
        self.item_popular, self.items_count = None, None
        self.model_name = 'K={}-epochs={}-alpha={}-lamb={}'.format(self.K, self.epochs, self.alpha, self.lamb)

    def init_model(self, users_set, items_set, K):
        """
        Init model, set P and Q with random numbers.
        :param users_set: Users set
        :param items_set: Items set
        :param K: Latent factor dimension.
        :return: None
        """
        self.P = dict()
        self.Q = dict()
        for user in users_set:
            self.P[user] = [random.random() / math.sqrt(K) for _ in range(K)]
        for item in items_set:
            self.Q[item] = [random.random() / math.sqrt(K) for _ in range(K)]

    def init_users_items_set(self, trainset):
        """
        Get users set and items set.
        :param trainset: train dataset
        :return: Basic users and items set, etc.
        """
        users_set, items_set = set(), set()
        items_list = []
        item_popular = defaultdict(int)
        for user, movies in trainset.items():
            for item in movies:
                item_popular[item] += 1
                users_set.add(user)
                items_set.add(item)
                items_list.append(item)
        items_count = len(items_set)
        return users_set, items_set, items_list, item_popular, items_count

    def gen_negative_sample(self, items: dict):
        """
        Generate negative samples
        :param items: Original items, positive sample
        :return: Positive and negative samples
        """
        samples = dict()
        for item, rate in items.items():
            samples[item] = 1
        for i in range(len(items) * 11):
            item = self.items_list[random.randint(0, len(self.items_list) - 1)]
            if item in samples:
                continue
            samples[item] = 0
            if len(samples) >= 10 * len(items):
                break
        # print(samples)
        return samples

    def predict(self, user, item):
        """
        Predict the rate for item given user and P and Q.
        :param user: Given a user
        :param item: Given a item to predict the rate
        :return: The predict rate
        """
        rate_e = 0
        for k in range(self.K):
            Puk = self.P[user][k]
            Qki = self.Q[item][k]
            rate_e += Puk * Qki
        return rate_e

    def train(self, trainset):
        """
        Train model.
        :param trainset: Origin trainset.
        :return: None
        """
        for epoch in range(self.epochs):
            print('epoch:', epoch)
            for user in trainset:
                samples = self.gen_negative_sample(trainset[user])
                for item, rui in samples.items():
                    eui = rui - self.predict(user, item)
                    for k in range(self.K):
                        self.P[user][k] += self.alpha * (eui * self.Q[item][k] - self.lamb * self.P[user][k])
                        self.Q[item][k] += self.alpha * (eui * self.P[user][k] - self.lamb * self.Q[item][k])
            self.alpha *= 0.9
            # print(self.P)
            # print(self.Q)

    def fit(self, trainset):
        self.trainset = trainset
        self.users_set, self.items_set, self.items_list, self.item_popular, self.items_count = \
            self.init_users_items_set(trainset)
        model_manager = ModelManager()
        try:
            self.P = model_manager.load_model(self.model_name + '-P')

            self.Q = model_manager.load_model(self.model_name + '-Q')
            # 这段将P、Q矩阵输出重定向的txt文件
            ''' 
            filepath = os.path.join('result', self.model_name)
            print(filepath)
            oldStdout = sys.stdout
            try:
                file = open(filepath + '-P-10m.txt', 'w')
                sys.stdout = file
                print(self.P)
                file.close()

                file = open(filepath + '-Q-1m.txt', 'w')
                sys.stdout = file
                print(self.Q)
                file.close()
            except IOError:
                print(IOError)
            sys.stdout = oldStdout
            '''
            print('User origin similarity model has saved before.\nLoad model success...\n')
        except OSError:
            print('No model saved before.\nTrain a new model...')
            self.init_model(self.users_set, self.items_set, self.K)
            self.train(self.trainset)
            print('Train a new model success.')
            if self.save_model:
                model_manager.save_model(self.P, self.model_name + '-P')
                model_manager.save_model(self.Q, self.model_name + '-Q')
            print('The new model has saved success.\n')
        return self.P, self.Q

    def recommend(self, user):
        """
        Recommend N movies for the user.
        :param user: The user we recommend movies to.
        :return: the N best score movies
        """
        rank = collections.defaultdict(float)
        interacted_items = self.trainset[user]
        for item in self.items_set:
            if item in interacted_items.keys():
                continue
            for k, Qik in enumerate(self.Q[item]):
                rank[item] += self.P[user][k] * Qik

        a = [movie for movie, _ in sorted(rank.items(), key=itemgetter(1), reverse=True)][:self.n_rec_movie]
        movie_arr = []
        for movie in a:
            movie_tuple = (str(movie), str(rank[movie]))
            movie_arr.append(movie_tuple)

        return [movie for movie, _ in sorted(rank.items(), key=itemgetter(1), reverse=True)][:self.n_rec_movie], \
               movie_arr

    def test(self, testset):
        """
        Test the recommendation system by recommending scores to all users in testset.
        :param testset: test dataset
        :return: None
        """
        self.testset = testset
        print('Test recommendation system start...')
        #  varables for precision and recall
        hit = 0
        rec_count = 0
        test_count = 0
        # varables for coverage
        all_rec_movies = set()
        # varables for popularity
        popular_sum = 0

        # record the calculate time has spent.
        test_time = LogTime(print_step=1000)
        movie_json = {}
        for user in self.users_set:
            test_movies = self.testset.get(user, {})
            rec_movies, movie_arr = self.recommend(user)  # type:list
            for movie in rec_movies:
                if movie in test_movies.keys():
                    hit += 1
                all_rec_movies.add(movie)
                popular_sum += math.log(1 + self.item_popular[movie])
                # log steps and times.

            rec_count += self.n_rec_movie
            test_count += len(test_movies)
            # print time per 500 times.
            test_time.count_time()

            movie_json[user] = movie_arr
        old_stdout = sys.stdout
        file = open("result/rec/LFM-" + self.model_name + ".txt", "w")
        sys.stdout = file
        print(movie_json)
        file.close()
        sys.stdout = old_stdout

        precision = hit / (1.0 * rec_count)
        recall = hit / (1.0 * test_count)
        coverage = len(all_rec_movies) / (1.0 * self.items_count)
        popularity = popular_sum / (1.0 * rec_count)
        print('Test recommendation system success.')
        test_time.finish()

        oldstdout = sys.stdout
        file = open("result/result/LFMResult-" + self.model_name + ".txt", 'w')
        sys.stdout = file
        print('precision=%.4f\nrecall=%.4f\ncoverage=%.4f\npopularity=%.4f' %
               (precision, recall, coverage, popularity))
        file.close()
        sys.stdout = oldstdout

        print('precision=%.4f\trecall=%.4f\t\ncoverage=%.4f\t\tpopularity=%.4f\n' %
              (precision, recall, coverage, popularity))
