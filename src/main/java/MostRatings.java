import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 类           MostRatings
 * 类的作用:    通过数据库连接向html页面发送数据
 * Description: 显示有最多评分的电影信息
 * @Date:       2021/2/20 21:29
 * @author:     32353
 * @version     V1.0.0
*/
@WebServlet("/MostRatings")
public class MostRatings extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        ServletOutputStream os = resp.getOutputStream();
        try {
            conn = ConnMySQL.connectMovieData("movies_data2");
            stmt = conn.createStatement();
            //从ratings表中，得出被评分最多的8部电影，作为最多观看(MySQL练习
            String sql =
                        "SELECT  m.title, m.genres, COUNT(*) AS rated " +
                        "FROM ratings r " +
                        "JOIN movies m " +
                        "WHERE r.movieId = m.movieId " +
                        "GROUP BY r.movieId " +
                        "ORDER BY rated DESC " +
                        "LIMIT 8; ";
            rs = stmt.executeQuery(sql);
            os.println("<table style='width:100%;border-collapse:collapse;border-style: solid;border-width: 1px;margin:auto;'><tr><th>movie_title</th><th>movie_genres</th><th>ratings_num</th></tr>");
            while (rs.next()) {
                os.println("<tr><td>"+rs.getString(1) + "</td><td>" + rs.getString(2)
                        + "</td><td>" + rs.getInt(3) + "</td></tr>");
            }
            os.println("</table>");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
        System.out.println("Get data From MySQL Success!");
    }
}