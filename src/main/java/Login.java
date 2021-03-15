
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 类           Login
 * 类的作用:    获得登录时的用户名、密码
 * Description:
 * @Date:       2021/2/20 9:30
 * @author:     32353
 * @version     V1.0.0
*/
@WebServlet("/src/html/login")
public class Login extends HttpServlet {
    private static final Logger log = Logger.getLogger(Login.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);

        String name = req.getParameter("name");
        ServletOutputStream os = resp.getOutputStream();
        os.print("Hello, " + name);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        String s = req.getParameter("name");
        log.info("name = " + s);
        log.info("DoPost");
        doGet(req, resp);
    }
}
