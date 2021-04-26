package mapper;

import POJO.Recommendation;
import POJO.Recommendation2;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * 类           RecommendationMapper
 * 类的作用:    规范获取数据格式
 * Description:
 * @Date:       2021/4/3 16:35
 * @author:     32353
 * @version     V1.0.0
*/

public interface RecommendationMapper {

    //获得itemBasedCF表中的所有用户1的推荐结果列表
    List<Recommendation> getRecommendation();
    List<Recommendation2> getRecommendation2(int id);

    /**
     * 在指定表中查询指定用户的所有推荐结果
     * @Param   String tableName 数据库表名
     * @Param   int id    用户id
     * @Return  List<Recommendation2> 返回结果列表
     * */
    List<Recommendation2> getRecommendation2(@Param("tableName") String tableName, @Param("id") int id);

    List<Recommendation2> getNthRecommendation(@Param("tableName") String tableName, @Param("id") int id, @Param("Nth") int N);

    Recommendation2 getOne(@Param("tableName")String tableName, @Param("id")int id, @Param("rec")int recommendation);

    //增删改对于推荐结果并不推荐，但是如果需要作弊的话，可以调用这里面的方法
    int addRecommendation(@Param("tableName")String tableName, @Param("rec")Recommendation rec);
    int deleteRecommendation(@Param("tableName")String tableName, @Param("id")int id, @Param("rec")int recommendation);
    int updateRecommendation(@Param("tableName")String tableName,  @Param("rec")Recommendation rec);
}
