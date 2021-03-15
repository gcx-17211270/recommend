import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

public class ConnMySQLTest {
    @Test
    public void testConnMySQL() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConnMySQL.connectMovieData("movies_data2");

            // 创建Statement对象
            stmt = conn.createStatement();
            // 执行SQL语句
            rs = stmt.executeQuery("select * from movies LIMIT 50");
            System.out.println("movieId\ttitle\tgenres\t\t");
            while (rs.next()) {
                System.out.println(rs.getInt(1) + "\t" + rs.getString(2)
                        + "\t\t" + rs.getString(3) + "\t\t" );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {           //关闭所有连接
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        assertNotNull(conn);
    }
}