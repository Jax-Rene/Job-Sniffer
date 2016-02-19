import java.sql.{ResultSet, DriverManager}
import com.zhuangjy.common.JobType
import org.apache.spark.SparkContext
import org.apache.spark.rdd.JdbcRDD

/**
  * Created by zhuangjy on 2016/2/17.
  */
object SparkToJDBC {
  def main(args: Array[String]) {
    val conn_str = "jdbc:mysql://127.0.0.1:3306/jobs?user=root&password="
    fasfda
    val sc = new SparkContext("local", "mysql")
    val section = loadSection(conn_str)
    val min:Long = section(0).map
    val keyWords = JobType.keyWords()
    val res = calAreaCount(section(0),section(1),"北京",sc)
    println(calAreaCount("北京"))
    sc.stop()
  }

  /**
    * 获取id区间
    */
  def loadSection(src: String) = {
    classOf[com.mysql.jdbc.Driver]
    val res = new Array[Any](2)
    val conn = DriverManager.getConnection(src)
    val statement = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
    var rs = statement.executeQuery("select id from job ORDER BY id limit 1")
    if (rs.next()) {
      res(0) = rs.getString("id");
    }
    rs = statement.executeQuery("select id from job ORDER BY id desc limit 1")
    if (rs.next()) {
      res(1) = rs.getString("id")
    }
    res
  }

  /**
    * 计算指定地区的工作需求量
    * @param min
    * @param max
    * @param area
    * @param sc
    * @return
    */
  def calAreaCount(min: Long, max: Long, area: String,sc:SparkContext): Long = {
    val rdd = new JdbcRDD(
      sc,
      ()=>{
        Class.forName("com.mysql.jdbc.Driver").newInstance()
        DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/jobs", "root", "")
      },
      "SELECT * FORM job WHERE id >=? AND id <= ?",
      min,max,3,
      r => r.getString(4)).cache()
      rdd.filter(_.contains(area)).count()
  }


  /**
    * 获取指定工作的数量
    *
    * @param min
    * @param max
    * @param jobName
    * @param sc
    * @return
    */
  def generateJobCount(min: Int, max: Int, jobName: String, sc: SparkContext): Long = {
    val rdd = new JdbcRDD(
      sc,
      () => {
        Class.forName("com.mysql.jdbc.Driver").newInstance()
        DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/jobs", "root", "")
      },
      "SELECT * FROM job WHERE id >=? AND id <= ?",
      min, max, 3,
      //第二列job_name
      r => r.getString(2)).cache()
    rdd.filter(_.contains(jobName)).count()
  }
}
