import com.zhuangjy.dao.IBatisDao;
import com.zhuangjy.entity.Job;
import com.zhuangjy.framework.config.AppsApplicationConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by johnny on 16/5/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppsApplicationConfig.class)
@WebAppConfiguration
public class iBatisDaoTest {
    @Autowired
    private IBatisDao<Job> iBatisDao;
    @Test
    public void loadData() throws SQLException {
        Map<String,Object> hs = new HashMap<>();
        hs.put("jobName","java");
        List<Job> list = iBatisDao.queryForList("jobSql.loadData",hs,0,100);
        System.out.println(list);
    }
}
