package supergame
import scala.collection.mutable.ArrayBuffer

class ScalaStatistics {
  def max(gamesInfo: Array[GameInfo]): Int = {
    var max = 0
    val stepBuffer: List[Int] =
      for (iterator <- gamesInfo.toList) yield {
        var start = 0
        while (iterator.getSteps.charAt(start * 5) == '0') {
          start += 1
        }
        (iterator.getSteps.length() / 5 - start)
      }
    stepBuffer.min
  }

  def argScore(gamesInfo: Array[GameInfo]): Int = {
    val scoreBuffer: List[Int] =
      for (iterator <- gamesInfo.toList) yield iterator.getScore
    var argScore = scoreBuffer.sum
    argScore / scoreBuffer.length
  }

  def fourStatistic(gamesInfo: Array[GameInfo]): Float = {
    var all = 0
    var four: Float = 0
    def stepsBuffer =
      for (iterator <- gamesInfo.toList) yield {
        for (iterator_2 <- iterator.getSteps.split(";")) yield iterator_2
      }

    for (iterator <- stepsBuffer) {
      for (iterator_2 <- iterator) {
        if (iterator_2.charAt(1) == '2') four += 1
        all += 1
      }
    }

    four / all * 100

  }
  
    def not(gamesInfo:GameInfo) {
    for (iterator <- gamesInfo.getSteps.split(";"))yield  {
          if (iterator.charAt(0) == '0') 
          print("")
          if (iterator.charAt(0) == '1') 
          print(" Move to the LEFT ")
          if (iterator.charAt(0) == '2') 
          print(" Move to the RIGHT ")
          if (iterator.charAt(0) == '3') 
          print(" Move to the UP ")
          if (iterator.charAt(0) == '4') 
          print(" Move to the DOWN ")
          print(" Add " + iterator.charAt(1) + " X = " + iterator.charAt(2) + " Y = " + iterator.charAt(3))
          println()       
    }

  }

}