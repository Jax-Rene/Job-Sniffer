import com.ibatis.sqlmap.client.SqlMapClient;
import com.mysql.jdbc.TimeUtil;
import com.zhuangjy.dao.BaseDao;
import com.zhuangjy.dao.IBatisDao;
import com.zhuangjy.entity.Job;
import com.zhuangjy.entity.PropertiesMap;
import com.zhuangjy.framework.config.AppsApplicationConfig;
import com.zhuangjy.service.AdminService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

/**
 * Created by johnny on 16/4/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppsApplicationConfig.class)
@WebAppConfiguration
public class MainTest {

    @Test
    public void startMockito(){
        List<String> list = mock(List.class);
        //设置模拟
        when(list.get(0)).thenReturn("hello");
        String result = list.get(0);
        verify(list,times(1)).get(0);
        Assert.assertEquals("hello",result);

        //return
//        result = returnTest();
//        Assert.assertEquals("world",result);

        //抛出异常
        when(list.get(1)).thenThrow(new RuntimeException("test exception"));
        list.get(1);

        //泛型
        when(list.get(anyInt())).thenReturn("anyInt");
        result = list.get(4);
//        verify(list,times(1)).get(anyInt()); 这里要写anyInt()
        Assert.assertEquals("anyInt",result);
    }
}
