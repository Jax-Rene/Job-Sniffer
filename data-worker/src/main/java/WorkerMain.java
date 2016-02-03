import com.zhuangjy.common.JobName;
import com.zhuangjy.worker.LaGouRobotWorker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Created by zhuangjy on 2016/1/8.
 */
public class WorkerMain {
    private static final Logger LOGGER = LogManager.getLogger(WorkerMain.class);

    public static void main(String[] args) {
        try {
            String city = "";
            List<String> jobs = JobName.returnAllJobName();
            for(String job:jobs){
                new Thread(new LaGouRobotWorker(job,city,"C:\\Users\\iamjo\\Desktop\\data.txt")).start();
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }
}
