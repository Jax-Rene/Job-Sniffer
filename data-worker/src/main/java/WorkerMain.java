import com.zhuangjy.common.JobName;
import com.zhuangjy.worker.LaGouRobotWorker;

import java.util.List;

/**
 * Created by zhuangjy on 2016/1/8.
 */
public class WorkerMain {
    public static void main(String[] args) {
        try {
            String city = "";
            List<String> jobs = JobName.returnAllJobName();
            for(String job:jobs){
                new Thread(new LaGouRobotWorker(job,city,"/Users/johnny/Desktop/data.txt")).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
