import com.zhuangjy.framework.config.AppsApplicationConfig;
import com.zhuangjy.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by johnny on 16/4/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppsApplicationConfig.class)
@WebAppConfiguration
public class MainTest {
    @Autowired
    private MailService mailService;

    @Test
    public void testMail(){
        mailService.sendEmails("test","<h1>单元测试</h1>");
    }

}
