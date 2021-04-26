package controller;

import mapper.MostRatingsImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 类           MostRatingsController
 * 类的作用:    通过数据库连接向html页面发送数据
 * Description: 显示有最多评分的电影信息,数据来源于MostRatingsImpl类提供
 * @Date:       2021/2/20 21:29
 * @author:     32353
 * @version     V1.0.0
*/
@WebServlet("/MostRatings")
public class MostRatingsController extends HttpServlet
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
        Logger Log = Logger.getLogger(MostRatingsController.class);
        ServletOutputStream os = resp.getOutputStream();
        MostRatingsImpl mostRatings = null;

        os.println("<table style='border:solid gray;'>" +
                "<tr style='border:1px solid gray; text-align:center;'>" +
                "<th>movie_title</th><th>movie_genres</th><th>rating(s)</th>" +
                "</tr>");
        try {
            mostRatings = new MostRatingsImpl();
            ResultSet rs = mostRatings.getResult();
            while (rs.next()) {
                os.println("<tr style='border:1px solid gray;'>" +
                            "<td>"+rs.getString(1) + "</td>" +
                            "<td>" + rs.getString(2) + "</td>" +
                            "<td>" + rs.getInt(3) + "</td>" +
                        "</tr>");
            }
            os.println("</table>");
            Log.info("Get data From MySQL Success!");
        }
        catch (SQLException ex) {
            Log.error(ex);
        }
        finally {
            /// mostRatings.finish();
        }
    }
}