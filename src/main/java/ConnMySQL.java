
import org.apache.log4j.Logger;

import java.sql.*;

/**
 * 类           ConnMySQL
 * 类的作用:    连接数据库，输入参数为数据库的名字
 * Description: 数据库的password在建立连接时硬编码写入。（安全性不高）
 * @Date:       2021/2/21 8:25
 * @author:     32353
 * @version     V1.0.0
*/

public class ConnMySQL {
    private static final Logger log = Logger.getLogger(ConnMySQL.class);
    public static Connection connectMovieData(String database) throws Exception {
        Connection conn = null;
        try {
            // 加载驱动类
            Class.forName("com.mysql.cj.jdbc.Driver");
            long start =System.currentTimeMillis();

            // 建立连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database,
                    "root", "17211270");
            long end = System.currentTimeMillis();
            log.info(conn);
            //为什么在Output中会出现乱码
            log.info("Connection established in " + (end - start) + " ms.");
//            log.info("建立连接耗时 " + (end - start) + "毫秒");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}