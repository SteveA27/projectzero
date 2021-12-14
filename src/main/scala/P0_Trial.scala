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
      var connection: Connection = null

      try {
        // make the connection
        Class.forName(driver)
        connection = DriverManager.getConnection(url, username, password)

        // create the statement, and run the select query
        val statement = connection.createStatement()
        var cont = true


        do{

          println(Console.BOLD)
          println("Menu Options")
          println(Console.RESET)
          println("Option 1: Insert new data to the journal for your story: ")
          println("Option 2: Update data to the journal")
          println("Option 3: Delete data from the journal")
          println("Option 4: View the journal")
          println("Option 5: Quit/Exit the journal")
          println("Option 6: View User information")
          println("Option 7: Change Username")
          println("Option 8: Update City")
          println("Option 9: View User Basic Information")
          println("Option 10: Please enter the user information of your character, make sure you create your character entry first before using this option: ")
          println("Option 11: Please enter the user general information of your character, do this last, after user information and character journal entry: ")
          println("Option 12: Delete character user information, don't do this before the basic information is deleted")
          println("Option 13: Delete character basic information, do this first, before any of the deletes")

          println("Remember when creating a character you should follow the instructions when given")
          println("Please enter an option, from 1 - 13: ")
          var inputSelected = readLine().toInt
          if(inputSelected == 1){
            println("Please enter your new journal entry you want to add: ")
            val new_entry = readLine()
            println("Please enter a number that starts at 200, remember the number you pick, because you can't repeat it, unless you delete your entry: ")

            var newID = readLine().toInt

            println("Please input the today's date in the format: 'YYYY-MM-DD'")
            var entryJournalDate = readLine()



            val sql = s"INSERT INTO journalentry VALUES ($newID,'$new_entry', '$entryJournalDate');"
            statement.executeUpdate(sql)
          }
          if(inputSelected == 2){
            println("Please enter the journal ID for the journal entry you want to update: ")
            var inTableID = readLine().toInt
            println("Enter your new journal entry here: ")
            val updatedJournalEntry = readLine()
            val updateSQL = s"UPDATE journalentry SET entryJournal = '$updatedJournalEntry' WHERE journalID = $inTableID;"
            statement.executeUpdate(updateSQL)
          }
          if(inputSelected == 3){
            println("Please enter the ID of the journal entry you want to delete")
            var deleteID = readLine().toInt
            val deleteSQL = s"DELETE FROM journalentry WHERE journalID = $deleteID;"
            statement.executeUpdate(deleteSQL)
          }
          if(inputSelected == 4){
            val resultSet = statement.executeQuery("SELECT * FROM journalentry;")
            while (resultSet.next()) {


              println(resultSet.getString(1) + ", " + resultSet.getString(2) + ", " + resultSet.getString(3))
            }
          }
          if(inputSelected == 5){
            cont = false
          }
          if(inputSelected == 6){
            println("Enter your journal ID: ")
            var userID = readLine().toInt
            val userResult = statement.executeQuery(s"SELECT userinformation.userID, userinformation.userName, userinformation.journalID, journalentry.entryDate  FROM  journalentry, userinformation  WHERE userinformation.journalID = $userID AND userinformation.journalID = journalentry.journalID;")

            while(userResult.next()){
              println(userResult.getString("userinformation.userID") + "," + userResult.getString("userinformation.userName")+ "," + userResult.getString("userinformation.journalID")+ "," + userResult.getString("journalentry.entryDate"))

            }
          }
          if(inputSelected == 7){
            println("Enter user ID: ")
            var userID = readLine().toInt
            println("Enter your new crazy name, must be unique: ")
            val new_crazy_name = readLine()
            val updateUsrInfo = s"UPDATE userinformation SET userName = '$new_crazy_name' WHERE userID = $userID;"
            statement.executeUpdate(updateUsrInfo)

          }
          if(inputSelected== 8){
            println("Enter User Basic ID: ")
            var userBasicID = readLine().toInt
            println("Update the city you are living in now: ")
            val newCity = readLine()
            val updateCity = s"UPDATE usergeneral SET City = '$newCity' WHERE userBasicID = $userBasicID;"
            statement.executeUpdate(updateCity)
          }
          if(inputSelected == 9){
            println("Enter user ID: ")
            var userID = readLine().toInt
            println("Enter your journal ID: ")
            var journalID = readLine().toInt
            val UserBasicQuery = statement.executeQuery(s"SELECT userinformation.userName, usergeneral.City, usergeneral.jobTitle, usergeneral.userBasicID, journalentry.entryDate FROM journalentry, userinformation, usergeneral WHERE userinformation.userID = usergeneral.userID and userinformation.userID = $userID and userinformation.journalID = journalentry.journalID and userinformation.journalID = $journalID;")
            while(UserBasicQuery.next()){
             println(UserBasicQuery.getString("userinformation.userName")+ "," +UserBasicQuery.getString("usergeneral.City")+ "," +UserBasicQuery.getString("usergeneral.userBasicID")+ "," +UserBasicQuery.getString("journalentry.entryDate"))
            }

          }
          if(inputSelected == 10){
            println("Please enter a number that starts at 200, remember the number you pick, because you can't repeat it, unless you delete your entry: ")

            var newID = readLine().toInt
            println("Please enter the user ID for you character: ")
            var userIDForCharacter = readLine().toInt
            println("Please enter a username or crazy name for your character: ")
            val newUsername = readLine()
            val userSQL = s"INSERT INTO userinformation VALUES ($userIDForCharacter, '$newUsername',$newID);"
            statement.executeUpdate(userSQL)

          }
          if(inputSelected == 11){

            println("Please enter the user ID for you character: ")
            var userIDForCharacter = readLine().toInt
            println("Please enter your character's basic information ID: ")
            var userBID = readLine().toInt
            println("Please enter your character's city the character is from: ")
            val charCity = readLine()
            println("Please enter your character's job: ")
            val charJob = readLine()



            val basicSQL = s"INSERT INTO usergeneral VALUES($userIDForCharacter, '$charCity', '$charJob', $userBID);"
            statement.executeUpdate(basicSQL)

          }
          if(inputSelected == 12){
            println("Please enter the ID of the journal entry you want to delete")
            var deleteID = readLine().toInt
            val deleteSQL = s"DELETE FROM userinformation WHERE journalID = $deleteID;"
            statement.executeUpdate(deleteSQL)
          }
          if(inputSelected == 13){
            println("Please enter the user ID of the character you want to delete")
            var deleteID = readLine().toInt
            val deleteSQL = s"DELETE FROM usergeneral WHERE userID = $deleteID;"
            statement.executeUpdate(deleteSQL)
          }

        }while(cont)
        //val num = readLine().toInt
        //val new_entry = readLine()


        //val sql = s"INSERT INTO journalentry VALUES ($num,'$new_entry', '2021-11-12');"
        //statement.executeUpdate(sql)




      } catch {
        case e: Throwable => e.printStackTrace
      }
      connection.close()
    }


  }


