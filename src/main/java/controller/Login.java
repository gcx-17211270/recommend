package controller;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 类           Login
 * 类的作用:    获得登录时的用户名、密码
 * Description:
 * @Date:       2021/2/20 9:30
 * @author:     32353
 * @version     V1.0.0
*/
@WebServlet("/login")
public class Login extends HttpServlet {
    private static final Logger log = Logger.getLogger(Login.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String pass = req.getParameter("pass");
        ServletOutputStream os = resp.getOutputStream();

        if (inData(name, pass)) {
            os.print("ok");
        }
        else {
            log.info("登陆失败");
            os.print("false");
        }
    }

    private boolean inData(String name, String pass) {
        /**
         * 仅仅是做一个示范，之后的账户密码肯定不能这么简单处理
         * */
        return ("admin".equals(name) && "12345".equals(pass));
    }
}
