# -*- coding: utf-8 -*-

'''
Descripttion: 
version: 
Author: 32353
Date: 2021-05-06 09:14:46
LastEditors: 32353
LastEditTime: 2021-05-06 22:20:13
'''

from MF.utils import ModelManager
from MF.utils import LogTime
from MF.LFM import LFM
from MF.dataset import DataSet
from usercf import UserBasedCF
from itemcf import ItemBasedCF
import os


def run_model(model_name, dataset_name, test_size=0.3, clean=False):
    print('*' * 70)
    print('\tThis is %s model trained on %s with test_size = %.2f' % (model_name, dataset_name, test_size))
    print('*' * 70 + '\n')

    model_manager = ModelManager(dataset_name, test_size)
    try:
        trainset = model_manager.load_model('trainset')
        testset = model_manager.load_model('testset')
    except OSError:
        ratings = DataSet.load_dataset(name=dataset_name)
        trainset, testset = DataSet.train_test_split(ratings, test_size=test_size)
        model_manager.save_model(trainset, 'trainset')
        model_manager.save_model(testset, 'testset')
    '''Do you want to clean workspace and retrain model again?'''
    '''if you want to change test_size or retrain model, please set clean_workspace True'''
    model_manager.clean_workspace(clean)

    if model_name == 'LFM':
        # K, epochs, alpha, lamb, n_rec_movie
        lfm = LFM(10, 10, 0.018 , 0.01, 10)
        lfm.fit(trainset)
        # recommend_test(lfm, [1, 100, 233])
        lfm.test(testset)
    elif model_name == 'userCF':
        usercf = UserBasedCF(trainset=trainset, testset=testset, dataset_name=dataset_name)
        usercf.generate_dataset()
        usercf.calc_user_sim()
        rec_result = usercf.evaluate()
    elif model_name == 'itemCF':
        itemcf = ItemBasedCF(trainset=trainset, testset=testset, dataset_name=dataset_name)
        itemcf.generate_dataset()
        itemcf.calc_movie_sim()
        rec_result = itemcf.evaluate()
    else:
        raise ValueError('No model named ' + model_name)

def recommend_test(model, user_list):
    for user in user_list:
        recommend, movie_arr = model.recommend(str(user))
        print("recommend for userid = %s:" % user)
        print(recommend)
        print()


if __name__ == '__main__':
    main_time = LogTime(words="Main Function")
    dataset_name = 'ml-1m'
    '''LFM、userCF、itemCF可选'''
    model_type = 'LFM'
    test_size = 0.3
    run_model(model_type, dataset_name, test_size, False)
    main_time.finish()
