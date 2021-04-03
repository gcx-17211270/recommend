package Utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * 类           RecommendationUtils
 * 类的作用:    获取sqlSessionFactory,操作Recommendation对象的工具类
 * Description:
 * @Date:       2021/4/3 15:09
 * @author:     32353
 * @version     V1.0.0
*/

public class RecommendationUtils {
    private static SqlSessionFactory sqlSessionFactory;
    /**
     *直接使用对象类的静态方法连接数据库获取对象，请勿创建对象实例
     */
    private RecommendationUtils(){}

    static {
        Logger Log = Logger.getLogger(RecommendationUtils.class);
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
            Log.error(e);
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession();
    }
}
