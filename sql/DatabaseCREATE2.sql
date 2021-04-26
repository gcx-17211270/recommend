use movies_data;

LOAD DATA INFILE "D:/ProgramData/MySQL/MySQL Server 8.0/Uploads/ratings.csv"
INTO TABLE ratings
CHARACTER SET utf8
FIELDS TERMINATED BY ","
ENCLOSED BY '"' -- 结尾符
LINES TERMINATED BY '\n' -- 换行

IGNORE 1 ROWS;		-- 忽略掉csv文件的第一行 

-- SHOW VARIABLES LIKE "secure_file_priv";

CREATE TABLE userBasedCF_big(
	id int,
    movieId INT,
    score double
);

CREATE TABLE result(
	algo VARCHAR(45),
    prec double,
    recall double,
    coverage double,
    popularity double
);