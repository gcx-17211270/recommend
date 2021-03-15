import  javax.servlet.ServletException;
import  javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import  javax.servlet.http.HttpServlet;
import  javax.servlet.http.HttpServletRequest;
import  javax.servlet.http.HttpServletResponse;

import  java.io.IOException;

/**
 * 类           SimpleAjaxServlet
 * 类的作用:    一个简单的Ajax响应测试
 * Description:
 * @Date:       2021/2/20 1:41
 * @author:     32353
 * @version     V1.0.0
*/
@WebServlet("/SimpleAjax")
public class SimpleAjaxServlet extends HttpServlet
{
    private static final long serialVersionUID = -668635463669389981L;

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
        String name = req.getParameter("name");
        ServletOutputStream os = resp.getOutputStream();
        os.print("Hello, " + name);
        System.out.println("Hello, AJAX!");
    }
}