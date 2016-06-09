package supergame

import javax.swing.JLabel;
class ScalaSort {

  def sort(gamesInfo: Array[GameInfo], lenth: Int, timeText: JLabel): Array[GameInfo] = {
    val time = System.nanoTime()
    def swap(i: Int, j: Int) {
      val temp = gamesInfo(i)
      gamesInfo(i) = gamesInfo(j)
      gamesInfo(j) = temp
    }

    def qSort(left: Int, right: Int) {
      val a = gamesInfo((left + right) / 2).getScore
      var i = left
      var j = right
      while (i <= j) {

        while (a < gamesInfo(i).getScore) {
          i += 1
        }
        while (a > gamesInfo(j).getScore) {
          j -= 1
        }
        if (i <= j) {
          swap(i, j)
          i += 1
          j -= 1
        }
      }
      if (i < right) qSort(i, right)
      if (j > left) qSort(left, j)
    }

    qSort(0, lenth - 1)
    val time1: Float = System.nanoTime - time
    timeText.setText(Statistics.TEXT + String.valueOf(time1 / 1000000))
    gamesInfo
  }

}