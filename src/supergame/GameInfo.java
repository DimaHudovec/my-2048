package supergame;

public class GameInfo {
  private String name;
  private int score;
  private String steps;

  public GameInfo() {
    setName(null);
    setScore(0);
    setSteps(null);
  }

  public String getSteps() {
    return steps;
  }

  public void setSteps(String steps) {
    this.steps = steps;
  }

  public GameInfo(String name, int score, String steps) {
    setName(name);
    setScore(score);
    setSteps(steps);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }
}
