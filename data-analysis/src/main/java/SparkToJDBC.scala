import java.sql.DriverManager
import org.apache.spark.SparkContext
import org.apache.spark.rdd.JdbcRDD
import com.zhuangjy.bean.Job;
/**
  * Created by zhuangjy on 2016/2/17.
  */
object SparkToJDBC {
  def main(args: Array[String]) {
    val jobName = "Java"
    val count = generateJobCount(jobName)
    println(count)
  }

  def generateJobCount(jobName:String):Long = {
    val sc = new SparkContext("local","mysql")
    val rdd = new JdbcRDD(
      sc,
      () => {
        Class.forName("com.mysql.jdbc.Driver").newInstance()
        DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/jobs","root","")
      },
      "SELECT * FROM job WHERE id >=? AND id <= ?",
      605521,2151334,3,
      r => r.getString(2)).cache()

    val count = rdd.filter(_.contains(jobName)).count()
    sc.stop()
    return count
  }
}
