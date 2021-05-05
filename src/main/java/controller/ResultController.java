package controller;

import mapper.ResultImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 类           Result
 * 类的作用:    获得推荐指标
 * Description:
 * @Date:       2021/4/6 4:25
 * @author:     32353
 * @version     V1.0.0
*/
@WebServlet("/static/html/getRecResult")
public class ResultController extends HttpServlet {
    private Logger log = Logger.getLogger(ResultController.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取流对象之前，设置流的默认编码
        resp.setCharacterEncoding("utf-8");
        //告诉浏览器，服务器发送的消息体数据的编码，建议浏览器使用该编码解码
        resp.setHeader("content-type","text/html;charset=utf-8");
        //简单的形式，设置编码
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter pw = resp.getWriter();

        String tableName = req.getParameter("algoName");
        ResultImpl resultImpl = new ResultImpl();
        ResultSet rs = resultImpl.getResultByName(tableName);
        try {
            while (rs.next()) {
                pw.print(rs.getDouble(2) + "&" +
                        + rs.getDouble(3)  + "&" +
                        + rs.getDouble(4)  + "&" +
                        + rs.getDouble(5));
            }
            log.info("Get data From MySQL Success! ----- Get Recommend Result");
        }
        catch (SQLException ex) {
            log.error(ex);
        }
        /// resultImpl.finish();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
