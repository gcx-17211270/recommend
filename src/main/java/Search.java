import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 类           Search
 * 类的作用:    搜索框内容的响应
 * Description:
 * @Date:       2021/2/20 1:41
 * @author:     32353
 * @version     V1.0.0
*/
@WebServlet("/Search")
public class Search extends HttpServlet{
    private static final Logger log = Logger.getLogger(Search.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置中文
        resp.setCharacterEncoding("UTF-8");
        OutputStream outputStream = resp.getOutputStream();
        String s = req.getParameter("SearchText");
        byte[] sBytes = s.getBytes("UTF-8");
        outputStream.write(sBytes);
        log.info("Search Succeed");
//        PrintWriter out = resp.getWriter();
//        out.println("待搜索的电影名为" + req.getParameter("SearchText"));
//        out.println("Request get!");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
