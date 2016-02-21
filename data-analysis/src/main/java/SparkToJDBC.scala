import java.sql.{ResultSet, DriverManager}
import com.zhuangjy.common.JobType
import org.apache.spark.SparkContext
import org.apache.spark.api.java.JavaRDD
import org.apache.spark.rdd.JdbcRDD
import com.zhuangjy.util.readProperties

/**
  * Created by zhuangjy on 2016/2/17.
  */
object SparkToJDBC {
  def main(args: Array[String]) {
    val conn_str = "jdbc:mysql://127.0.0.1:3306/jobs?user=root&password="
    val sc = new SparkContext("local", "mysql")
    val section = loadSection(conn_str)
    val min: Long = section(0)
    val max: Long = section(1)
    val keyWords = JobType.keyWords()
    var areaCountMap: Map[String, Long] = Map()
    val areas: String = readProperties.readFromClassPath("analysis.properties", "area")
    val rdd: JdbcRDD[String] = calAreaCount(min, max, sc)
    for (s <- areas.split(",")) {
      val count:Long = rdd.filter(_.contains(s)).count()
      areaCountMap +=  (s -> count)
    }
    println(areaCountMap)
    sc.stop()
  }

  /**
    * 获取id区间
    */
  def loadSection(src: String) = {
    classOf[com.mysql.jdbc.Driver]
    val res = new Array[Long](2)
    val conn = DriverManager.getConnection(src)
    val statement = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
    var rs = statement.executeQuery("select id from job ORDER BY id limit 1")
    if (rs.next()) {
      res(0) = rs.getLong("id");
    }
    rs = statement.executeQuery("select id from job ORDER BY id desc limit 1")
    if (rs.next()) {
      res(1) = rs.getLong("id")
    }
    res
  }

  /**
    * 计算指定地区的工作需求量
    *
    * @param min
    * @param max
    * @return
    */
  def calAreaCount(min: Long, max: Long, sc: SparkContext): JdbcRDD[String] = {
    val rdd = new JdbcRDD(
      sc,
      () => {
        Class.forName("com.mysql.jdbc.Driver").newInstance()
        DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/jobs", "root", "")
      },
      "SELECT * FROM job WHERE id >=? AND id <= ?",
      min, max, 3,
      r => r.getString(4)).cache()
    rdd
  }


  /**
    * 获取指定工作的数量
    *
    * @param min
    * @param max
    * @param jobName
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
