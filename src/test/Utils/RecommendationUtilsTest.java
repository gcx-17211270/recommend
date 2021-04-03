package Utils;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.Test;

import static org.junit.Assert.*;
/**
 * 类           RecommendationUtilsTest
 * 类的作用:    使用该工具类，从数据库中获取一个Recommendation对象实例
 * Description:
 * @Date:       2021/4/3 15:14
 * @author:     32353
 * @version     V1.0.0
*/

public class RecommendationUtilsTest {
    Logger log = Logger.getLogger(RecommendationUtilsTest.class);
    @Test
    public void testRecommendation(){
        SqlSession sqlSession = RecommendationUtils.getSqlSession();
        log.info(sqlSession);
        sqlSession.close();
    }
}