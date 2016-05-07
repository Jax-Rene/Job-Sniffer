import com.ibatis.sqlmap.client.SqlMapClient;
import com.mysql.jdbc.TimeUtil;
import com.zhuangjy.dao.BaseDao;
import com.zhuangjy.dao.IBatisDao;
import com.zhuangjy.entity.Job;
import com.zhuangjy.entity.PropertiesMap;
import com.zhuangjy.framework.config.AppsApplicationConfig;
import com.zhuangjy.service.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.sql.Date;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

/**
 * Created by johnny on 16/4/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppsApplicationConfig.class)
@WebAppConfiguration
public class MainTest {
    @Autowired
    private BaseDao<Job> baseDao;

    @Autowired
    private IBatisDao<Job> iBatisDao;

    @Test
    public void testMail() throws SQLException {
        System.out.println(iBatisDao.queryForObject("jobSql.getLastDate"));
    }


}
