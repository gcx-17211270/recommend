package mapper;

import POJO.result;
import org.apache.log4j.Logger;
import service.ConnMySQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ResultImpl implements ResultDao {
    Logger log = Logger.getLogger(MostRatingsImpl.class);
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    @Override
    public ResultSet getResultByName(String algorithmName) {
        String sql = "SELECT * FROM result WHERE algo = \"" + algorithmName + "\"";
        try {
            conn = ConnMySQL.connectMovieData("movies_data2");
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            return rs;
        }
        catch (SQLException SQLex){
            log.error(SQLex);
        }
        catch (Exception ex) {
            log.error(ex);
        }
        return null;
    }

    public void finish() {
        try {
            rs.close();
            stmt.close();
            /// 对于仍然需要对这个数据库查询的语句来说，提前关闭数据库会造成并行时的影响
            /// conn.close();
        } catch (SQLException e) {
            log.error(e);
        }
    }
}
