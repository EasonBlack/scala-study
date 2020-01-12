
import scala.util.parsing.json.JSON
import scala.util.parsing.json.JSONObject
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util
import java.util.{Calendar, Date}


object Month {
    def main(args: Array[String]): Unit = {
      val startDate = 1572566400000L
      val calendar =  Calendar.getInstance
      calendar.setTime(new Date(startDate))
      val monthCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
      println(monthCount)
      for(i <- 1 to monthCount) {     
        calendar.get(Calendar.DAY_OF_WEEK)
        println(s"${calendar.get(Calendar.YEAR)}-${calendar.get(Calendar.MONTH) + 1}-${calendar.get(Calendar.DATE)}")
        println(calendar.getTime())
        calendar.add(Calendar.DATE,1)
      }
        
       
    }
}