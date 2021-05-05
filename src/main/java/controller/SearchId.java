package controller;

import POJO.movie;
import POJO.rating;
import POJO.tag;
import mapper.SearchMovieMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import service.SearchMovieByName;
import utils.RecommendationUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 类           SearchId
 * 类的作用:    根据电影ID返回电影信息、用户评分信息、用户标签信息
 * Description:
 * @Date:       2021/4/5 19:56
 * @author:     32353
 * @version     V1.0.0
*/
@WebServlet("/static/html/SearchId")
public class SearchId extends HttpServlet {
    Logger log = Logger.getLogger(SearchId.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取流对象之前，设置流的默认编码
        resp.setCharacterEncoding("utf-8");
        //告诉浏览器，服务器发送的消息体数据的编码，建议浏览器使用该编码解码
        resp.setHeader("content-type","text/html;charset=utf-8");
        //简单的形式，设置编码
        resp.setContentType("text/html;charset=utf-8");

        int id = Integer.parseInt(req.getParameter("movieId"));

        SqlSession sqlSession = RecommendationUtils.getSqlSession();
        SearchMovieMapper smm = sqlSession.getMapper(SearchMovieMapper.class);
        List<movie> movies = smm.searchMovieIdForInfo(id);
        List<rating> ratings = smm.searchMovieIdForRatings(id);
        List<tag> tags = smm.searchMovieIdForTags(id);

        StringBuffer str = new StringBuffer();
        str.append(SearchMovieByName.List2Array(movies) + "&");
        str.append(SearchMovieByName.List2Array(ratings) + "&");
        str.append(SearchMovieByName.List2Array(tags));
        log.info(str.toString());

        PrintWriter pw = resp.getWriter();
        pw.print(str.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
