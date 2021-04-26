package controller;

import POJO.Recommendation2;
import mapper.RecommendationMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import utils.RecommendationUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * 类           RecommendController
 * 类的作用:    用来返回对特定用户id的推荐结果查询
 * Description: 传递数据格式为JSON数组,例如这样：[{"id":"23", "recommendation":"Lord of the Rings: The Two Towers, The (2002)", "score":"1.9582799673080444"},{"id":"23", "recommendation":"Sixth Sense, The (1999)", "score":"1.9437600374221802"},{"id":"23", "recommendation":"L.A. Confidential (1997)", "score":"1.9246900081634521"}]
 * @Date:       2021/4/5 16:12
 * @author:     32353
 * @version     V1.0.0
*/
@WebServlet("/recommend_for_user")
public class RecommendController extends HttpServlet {
    Logger log = Logger.getLogger(RecommendController.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取流对象之前，设置流的默认编码
        resp.setCharacterEncoding("utf-8");
        //告诉浏览器，服务器发送的消息体数据的编码，建议浏览器使用该编码解码
        resp.setHeader("content-type","text/html;charset=utf-8");
        //简单的形式，设置编码
        resp.setContentType("text/html;charset=utf-8");
        int id = Integer.parseInt(req.getParameter("userId"));
        String tableName = req.getParameter("tableName");
        List<Recommendation2> list = null;
        PrintWriter writer = resp.getWriter();

        try (SqlSession sqlSession = RecommendationUtils.getSqlSession()) {
            RecommendationMapper rm = sqlSession.getMapper(RecommendationMapper.class);
            list = rm.getRecommendation2(tableName, id);
        }
        catch (Exception ex) {
            log.error(ex);
        }
        if (list == null || list.isEmpty()) {
            String string = "未查找到相关用户信息";
            writer.println(string);
        }
        else {
            StringBuffer out = new StringBuffer("[");
            for (Recommendation2 recommendation2 : list) {
                if (out.toString().equals("[")) {
                    out.append(recommendation2.toString());
                }
                else {
                    out.append("," + recommendation2.toString());
                }
            }
            out.append("]");
            String out2 = out.toString();
            System.out.println(out2);
            writer.print(out2);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
