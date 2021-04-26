package mapper;

import org.apache.log4j.Logger;
import service.ConnMySQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * 类           MostRatingsImpl
 * 类的作用:    获取评分数据的一个具体实现类
 * Description: getResult()方法实现了接口中定义的方法
 *              finish()方法关闭了与数据库的各种连接
 * @Date:       2021/4/3 16:35
 * @author:     32353
 * @version     V1.0.0
*/

public class MostRatingsImpl implements MostRatingsDao {

    Logger Log = Logger.getLogger(MostRatingsImpl.class);
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    @Override
    public ResultSet getResult() {

        //从ratings表中，得出被评分最多的8部电影，作为最多观看(MySQL练习
        String sql =
                "SELECT  m.title, m.genres, COUNT(*) AS rated " +
                "FROM ratings r " +
                "JOIN movies m " +
                "WHERE r.movieId = m.movieId " +
                "GROUP BY r.movieId " +
                "ORDER BY rated DESC " +
                "LIMIT 8; ";

        try {
            conn = ConnMySQL.connectMovieData("movies_data2");
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs != null) {
                //查询其实不需要这一步，但是增删改就需要判断是否成功，然后提交事务了
                //记一下，以后好找
                conn.commit();
            }
            return rs;
        }
        catch (SQLException SQLex){
            Log.error(SQLex);
        }
        catch (Exception ex) {
            Log.error(ex);
        }
        return null;
    }

    public void finish()  {
        try {
            rs.close();
            stmt.close();
            /// conn.close();
            ConnMySQL.finish();
        }
        catch (SQLException ex) {
            Log.error(ex);
        }
    }
}
