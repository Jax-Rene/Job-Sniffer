import com.wskj.bigdata.RobotWorker;
import com.wskj.bigdata.common.JobName;
import java.util.List;

/**
 * Created by zhuangjy on 2016/1/8.
 */
public class Main {
    public static void main(String[] args) {
        try {
            String city = "";
            List<String> jobs = JobName.returnAllJobName();
            for(String job:jobs){
                new RobotWorker(job,city,"D:\\Project\\person\\bigdata\\jobs.txt").run();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
