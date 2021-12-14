import com.fasterxml.jackson.databind.util.RawValue
import org.apache.spark.sql.types.VarcharType

import java.sql.DriverManager
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import scala.io.StdIn.readLine
object P0_Trial{





  /*create table users (
    id int unsigned auto_increment not null,
  journal_entry varchar(32) not null,
  date_created timestamp default now(),
  primary key (id)
  );*/




    def main(args: Array[String]) {
      // connect to the database named "mysql" on the localhost
      //val driver = "com.mysql.jdbc.Driver"

      val driver = "com.mysql.cj.jdbc.Driver"
      val url = "jdbc:mysql://localhost:3306/project0database"
      val table = ""
      val username = "root"
      val password = "Password11292021"

      /*val jdbcDF = (spark.read.format("jdbc")
        .option("url", url).option("dbtable", table).option("user", username)
        .option("password", password).option("driver", driver).load())*/

      // there's probably a better way to do this
      var connection:Connection = null
      println("Hello world")
      try {
        // make the connection
        Class.forName(driver)
        connection = DriverManager.getConnection(url, username, password)

        // create the statement, and run the select query
        val statement = connection.createStatement()
         val num = readLine().toInt
        val new_entry = readLine()




          val sql = s"INSERT INTO journalentry VALUES ($num,'$new_entry', '2021-11-12');"
          statement.executeUpdate(sql)



        val resultSet = statement.executeQuery("SELECT * FROM journalentry;")
        while ( resultSet.next() ) {


          println(resultSet.getString(1)+", " +resultSet.getString(2) +", " +resultSet.getString(3))
        }
      } catch {
        case e: Throwable => e.printStackTrace
      }
      connection.close()
    }
    println("Hello")
  }


