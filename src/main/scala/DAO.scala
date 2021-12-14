import java.sql.DriverManager
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import java.sql.Statement
import scala.io.StdIn.readLine
//import scala.util.control.Breaks._


object DAO extends App {

  object JDBC2 {
    def entryJournal(s:String): String = {
      val ent = readLine("Enter what is on your mind: ")
      return s + ent
    }


    def mySQLMenu():Unit = {
      val menuOptions =
        s"""
           |Below is a list to choose from to edit your journal entry:
           |1. Add an entry
           |2. Update an entry
           |3. Delete an entry
           |4. Press q to exit
           |""".stripMargin
      println(menuOptions)

      }

    def main(args: Array[String]) {
      // connect to the database named "mysql" on the localhost
      //val driver = "com.mysql.jdbc.Driver"
      val driver = "com.mysql.cj.jdbc.Driver"
      val url = "jdbc:mysql://localhost:3306/Desktop"
      val username = "root"
      val password = "Password112921"
      var cont = true

      // there's probably a better way to do this
      var connection:Connection = null
      var stmt: Statement = null

      try {
        // make the connection
        Class.forName(driver)
        connection = DriverManager.getConnection(url, username, password)

        // create the statement, and run the select query
        stmt.executeUpdate("DROP TABLE IF EXISTS journalEntry")

        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT * FROM users;")
        while ( resultSet.next() ) {
          println(resultSet.getString(1)+", " +resultSet.getString(2) +", " +resultSet.getString(3))
        }
        do{
          mySQLMenu()
          val cmd = readLine("Enter a number 1-3 to select a menu option or 4 to quit: ")
          var cmdInt = cmd.toInt

          cmdInt match{
            case 1 => {
              //Implement method for add or insert
            }
            case 2 =>{
              //Implement method for update
            }
            case 3 => {
              //implement method for delete
            }
            case 4 => cont = false
            case _=> {
              println("Invalid entry please enter a number between 1 - 4")
              cmdInt = cmd.toInt
            }

          }
        }while(cont)
      } catch {
        case e: Throwable => e.printStackTrace
      }
      connection.close()
    }
  }

}
