package Mapper;

import POJO.Recommendation;

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
    List<Recommendation> getRecommendation();
}
