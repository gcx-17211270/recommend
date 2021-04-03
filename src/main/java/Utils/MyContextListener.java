package Utils;

/**
 * @Description 监听侦：用于解决tomcat停止出现内存泄漏。
 * 作者：CSDN博主「Felix``」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/qq_40860565/article/details/105547194
 * 问题描述：03-Apr-2021 19:05:44.481 警告 [main] org.apache.catalina.loader.WebappClassLoaderBase.clearReferencesThreads The web application [ROOT] is still processing a request that has yet to finish. This is very likely to create a memory leak. You can control the time allowed for requests to finish by using the unloadDelay attribute of the standard Context implementation.
 * @Author ICHENY
 */

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyContextListener implements ServletContextListener {
    private final Logger log = Logger.getLogger(MyContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        log.info("WebService start");
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        log.info("WebService stop");
        try {
            while(DriverManager.getDrivers().hasMoreElements()) {
                DriverManager.deregisterDriver(DriverManager.getDrivers().nextElement());
            }
            log.info("jdbc Driver close");
            AbandonedConnectionCleanupThread.uncheckedShutdown();
            log.info("clean thread success");
        } catch (SQLException e) {
            log.error(e);
//            e.printStackTrace();
        }
    }
}
