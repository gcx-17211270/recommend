"""
Descripttion: 存储计算结果到MySQL数据库
version:
Author: 32353
Date: 2021-04-01 13:57:07
LastEditors: 32353
LastEditTime: 2021-04-01 14:26:47
"""
import sys
import json

import pymysql


class ConnMySQL:
    """
    获得数据库连接，包括从数据库中查询数据，以及从本地文件中向数据库中写入数据
    """

    def __init__(self, root, password, database, charset="utf8"):
        self.root = root
        self.password = password
        self.database = database
        self.charset = charset

    def getz_selection(self, sql):
        cursor = self.get_connection()
        try:
            result = cursor.execute(sql)
        except pymysql.Error:
            result = ""
            print(pymysql.Error)
        return result

    # 从本地加载字典格式的数据添加到数据库中，文件名即对应的数据库表名
    def store_data(self, filename):
        tableName = filename.split(".")[0];
        sql = "TRUNCATE TABLE " + tableName
        print(sql)
        conn = pymysql.connect(user=self.root, passwd=self.password, host="localhost",
                               database=self.database, charset=self.charset)
        cursor = conn.cursor()

        try:
            cursor.execute(sql)
        except pymysql.Error as e:
            conn.rollback()
            print(pymysql.Error)
            print("事务处理失败", e)
        else:
            conn.commit()
            print("数据表清理完成", cursor.rowcount)

        file = open('result/' + filename, 'r')
        js = file.read()
        # python对json数据的读取对象的每个属性都要有双引号，否则数据不能正常加载
        # 这里的一系列代换都是为了符合loads的要求
        js = js.replace(" ", "").replace("[", "\"[").replace("]", "]\"") \
            .replace("{\'", "{\"").replace("\'}", "\"}") \
            .replace("\",\'", "\",\"").replace("\':", "\":")
        dic = json.loads(js)
        i = int(0)
        for key in dic.keys():
            i = i + 1
            print(dic.get(key).strip())
            if dic.get(key).strip() == '[]':
                continue
            vals = dic.get(key)[2:-2].replace("\'", "").split("),(")
            for j in range(len(vals)):
                id = int(key)
                info = vals[j].split(",")
                rec = int(info[0])
                score = float(info[1])
                sql = "INSERT " + tableName + \
                      " VALUES(" + str(id) + "," + str(rec) + "," + str(score) + ")"
                # if i % 100 == 1 and j == 0:
                #     print("insert data for %d users" % i)
                #     print(sql)
                
                print("insert data for %d users" % i)
                print(sql)
                try:
                    cursor.execute(sql)

                except pymysql.Error as e:
                    conn.rollback()
                    print(pymysql.Error, sys.stderr)
                    print("事务处理失败", e, sys.stderr)
                else:
                    conn.commit()
                    # print("事务处理成功", cursor.rowcount)
        file.close()
        cursor.close()
        conn.close()

    def store_result(self, filename):
        file = open("result/" + filename, "r")
        algo = filename[0:-10]
        map = {}
        while True:
            line = file.readline()
            if line:
                lineArray = line.split('=')
                map[lineArray[0]] = lineArray[1].replace("\n", "")
            else:
                break
        sql = "INSERT result" + \
              " VALUES(\"" + algo + "\"," + map.get("precision") + "," + map.get("recall") \
              + "," + map.get("coverage") + "," + map.get("popularity") + ")"
        print(sql)
        conn = pymysql.connect(user=self.root, passwd=self.password, host="localhost",
                               database=self.database, charset=self.charset)
        cursor = conn.cursor()

        try:
            cursor.execute(sql)
        except pymysql.Error as e:
            conn.rollback()
            print(pymysql.Error)
            print("事务处理失败", e)
        else:
            conn.commit()
            print("数据表信息插入完成", cursor.rowcount)

    def clear_table(self, tableName):
        sql = "TRUNCATE TABLE " + tableName
        conn = pymysql.connect(user=self.root, passwd=self.password, host="localhost",
                               database=self.database, charset=self.charset)
        cursor = conn.cursor()

        try:
            cursor.execute(sql)
        except pymysql.Error as e:
            conn.rollback()
            print(pymysql.Error)
            print("事务处理失败", e)
        else:
            conn.commit()
            print("数据表清理完成", cursor.rowcount)


if __name__ == '__main__':
    # 前面几行是一个查询测试用例
    db = pymysql.connect(user="root", passwd="17211270", host="localhost",
                         database="movies_data2", charset="utf8")
    cursor = db.cursor()
    cursor.execute("SELECT * from movies LIMIT 10")
    data = cursor.fetchall()
    print(len(data))
    print(data[-1])
    db.close()

    # 对计算结果存储到数据库中
    # a = ConnMySQL("root", "17211270", "movies_data2")
    # # a.store_data("itemBasedCF.txt")
    # # a.store_data("userBasedCF.txt")
    # # a.store_data("LFM.txt")
    # # 将算法评价指标存储到数据库中
    # a.clear_table("Result")
    # a.store_result("itemBasedCFResult.txt")
    # a.store_result("userBasedCFResult.txt")
    # a.store_result("LFMResult.txt")

    # b是较大的数据集
    b = ConnMySQL("root", "17211270", "movies_data")
    # b.store_data("itemBasedCF.txt")
    # b.store_data("userBasedCF.txt")
    # b.store_data("LFM.txt")
    b.store_data("userBasedCF_big.txt")
    # 将算法评价指标存储到数据库中
    b.clear_table("Result")
    # b.store_result("itemBasedCFResult.txt")
    b.store_result("userBasedCFResult.txt")
    # b.store_result("LFMResult.txt")
    b.store_result("userBasedCFResult_big.txt")
