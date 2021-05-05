CREATE DATABASE movies_data2;

CREATE TABLE movies(
  movieId INT,
  title TEXT,
  genres TEXT,
  PRIMARY KEY(movieId)
);

CREATE TABLE ratings(
    userId INT,
    movieId INT,
    rating  DOUBLE,
    timestamp INT,
    PRIMARY KEY(userId, movieId)
);

CREATE TABLE tags(
    userId INT,
    movieId INT,
    tag TEXT,
    timestamp INT
);

-- 关于这些设置，是因为当导入文件过大时候容易断开连接
-- 但其实，主要修改的部分是edit > preferences > SQL Editor > MySQL Session下的数据，改长一点
  
set global max_allowed_packet=100000000; 
set global net_buffer_length=100000; 
SET GLOBAL  interactive_timeout=28800000;
SET GLOBAL  wait_timeout=28800000;
-- 关于路径：如果MySQL有一个“MySQL server is running with the –secure-file-priv” Error
-- 执行 SHOW VARIABLES LIKE "secure_file_priv"; 查看变量内容
-- 如果是NULL说明不允许从外部导入文件，需要修改配置，如果是一个文件夹，将要导入的文件放入该文件夹，然后导入（该文件夹的子文件夹也不行）如果是空，那就可以随意放在自己的位置，修改对应路径
-- 这里的text.csv路径是我的电脑上secure_file_priv变量的路径
-- 或者用MySQL Workbench的图形界面可视化操作
-- 建立数据库后右键选中load spamoviestial data，会方便很多
LOAD DATA INFILE "D:/ProgramData/MySQL/MySQL Server 8.0/Uploads/ratings.dat"
INTO TABLE movies_data.ratings
CHARACTER SET utf8
FIELDS TERMINATED BY "::"
-- ENCLOSED BY '"' -- 结尾符
LINES TERMINATED BY '\n' -- 换行

IGNORE 1 ROWS;		-- 忽略掉csv文件的第一行   

-- 一共需要导入ratings、tags、movies三个文件，修改位置与文件名，插入到对应表中即可 