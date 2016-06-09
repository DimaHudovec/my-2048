package supergame;

import javax.swing.JLabel;

public class JavaSort {

  private GameInfo[] gamesInfo;

  /**
   * sort information about games
   * 
   * @param games informations
   * @param
   * @return sorted information games
   */
  public GameInfo[] sort(GameInfo[] gamesInfo, JLabel timeText) {
    this.gamesInfo = gamesInfo;

    long time = System.nanoTime();
    qSort(0, gamesInfo.length - 1);
    float timeFinal = (float) (System.nanoTime() - time) / 1000000;

    timeText.setText(Statistics.TEXT + timeFinal);
    return gamesInfo;
  }

  /**
   * quicksort
   * 
   * @param left border
   * @param right border
   */
  private void qSort(int left, int right) {
    int i = left;
    int j = right;
    int standard = gamesInfo[(i + j) / 2].getScore();
    while (i <= j) {
      while (standard < gamesInfo[i].getScore()) {
        i++;
      }
      while (standard > gamesInfo[j].getScore()) {
        j--;
      }

      if (i <= j) {
        swap(i, j);
        i++;
        j--;
      }
    }
    if (i < right) {
      qSort(i, right);
    }
    if (j > left) {
      qSort(left, j);
    }
  }

  /**
   * swap items
   * 
   * @param left item
   * @param right item
   */
  private void swap(int i, int j) {
    GameInfo temp = gamesInfo[i];
    gamesInfo[i] = gamesInfo[j];
    gamesInfo[j] = temp;
  }
}
