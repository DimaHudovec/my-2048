package supergame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Statistics {

  private JButton scalaSort;
  private JButton javaSort;
  private JButton scalaNot;
  private ScalaSort scala = new ScalaSort();
  private JavaSort java = new JavaSort();
  private JTable table;
  private DefaultTableModel model;
  private GameInfo[] gamesInfo;
  private JFrame frame;

  public final int WIDTH = 320;
  public final int HEIGHT = 480;

  private JLabel timeText;

  private final int TIME_X = 20;
  private final int TIME_Y = 310;
  private final int TIME_WIDTH = 100;
  private final int TIME_HEIGHT = 20;
  public static final String TEXT = "TIME: ";

  private final int SCALA_SORT_B_X = 160;
  private final int SCALA_SORT_B_Y = 350;
  private final int SCALA_SORT_B_WIDTH = 100;
  private final int SCALA_SORT_B_HEIGHT = 30;
  
  private final int SCALA_NOT_B_X = 20;
  private final int SCALA_NOT_B_Y = 400;
  private final int SCALA_NOT_B_WIDTH = 240;
  private final int SCALA_NOT_B_HEIGHT = 30;

  private final int JAVA_SORT_B_X = 20;
  private final int JAVA_SORT_B_Y = 350;
  private final int JAVA_SORT_B_WIDTH = 100;
  private final int JAVA_SORT_B_HEIGHT = 30;

  private final int SCROLL_X = 10;
  private final int SCROLL_Y = 140;
  private final int SCROLL_WIDTH = 280;
  private final int SCROLL_HEIGHT = 160;

  private final int MAX_STEPS_X = 20;
  private final int MAX_STEPS_Y = 20;
  private final int MAX_STEPS_WIDTH = 280;
  private final int MAX_STEPS_HEIGHT = 30;
  private final String MAX_STEPS_TEXT = "The minimal number of moves: ";

  private final int ARG_SCORES_X = 20;
  private final int ARG_SCORES_Y = 50;
  private final int ARG_SCORES_WIDTH = 280;
  private final int ARG_SCORES_HEIGHT = 30;
  private final String ARG_SCORES_TEXT = "The average Hightscores: ";

  private final int FOUR_PERSENT_X = 20;
  private final int FOUR_PERSENT_Y = 80;
  private final int FOUR_PERSENT_WIDTH = 280;
  private final int FOUR_PERSENT_HEIGHT = 30;
  private final String FOUR_PERSENT_TEXT = "Persent of 2: ";

  public Statistics() {
    frame = new JFrame("Statistic");
    frame.setSize(WIDTH, HEIGHT);
    frame.setLayout(null);
    frame.setLocationRelativeTo(null);
    frame.setResizable(false);

    model = new DefaultTableModel(new Object[] {"HightScore"}, 0);
    table = new JTable(model);

    gamesInfo = Files.getInfoFiles();

    for (int i = 0; i < gamesInfo.length; i++) {
      model.addRow(new Object[] {gamesInfo[i].getScore()});
    }

    timeText = new JLabel(TEXT);
    timeText.setBounds(TIME_X, TIME_Y, TIME_WIDTH, TIME_HEIGHT);
    frame.add(timeText);

    scalaSort = new JButton("Scala");
    scalaSort.setBounds(SCALA_SORT_B_X, SCALA_SORT_B_Y, SCALA_SORT_B_WIDTH, SCALA_SORT_B_HEIGHT);
    frame.add(scalaSort);

    javaSort = new JButton("Java");
    javaSort.setBounds(JAVA_SORT_B_X, JAVA_SORT_B_Y, JAVA_SORT_B_WIDTH, JAVA_SORT_B_HEIGHT);
    frame.add(javaSort);
    
    scalaNot = new JButton("Notation");
    scalaNot.setBounds(SCALA_NOT_B_X, SCALA_NOT_B_Y, SCALA_NOT_B_WIDTH, SCALA_NOT_B_HEIGHT);
    frame.add(scalaNot);
    
    JScrollPane scroll = new JScrollPane(table);
    scroll.setBounds(SCROLL_X, SCROLL_Y, SCROLL_WIDTH, SCROLL_HEIGHT);
    frame.add(scroll);

    setStatistics();
    frame.setVisible(true);
    setEvents();

  }
/**
 * shows the statistics
 */
  private void setStatistics() {
    JLabel maxSteps = new JLabel(MAX_STEPS_TEXT + new ScalaStatistics().max(gamesInfo));
    maxSteps.setBounds(MAX_STEPS_X, MAX_STEPS_Y, MAX_STEPS_WIDTH, MAX_STEPS_HEIGHT);
    frame.add(maxSteps);

    JLabel argScores = new JLabel(ARG_SCORES_TEXT + new ScalaStatistics().argScore(gamesInfo));
    argScores.setBounds(ARG_SCORES_X, ARG_SCORES_Y, ARG_SCORES_WIDTH, ARG_SCORES_HEIGHT);
    frame.add(argScores);

    JLabel fourPersent =
        new JLabel(FOUR_PERSENT_TEXT + new ScalaStatistics().fourStatistic(gamesInfo));
    fourPersent.setBounds(FOUR_PERSENT_X, FOUR_PERSENT_Y, FOUR_PERSENT_WIDTH, FOUR_PERSENT_HEIGHT);
    frame.add(fourPersent);
  }

  /**
   * set Event for button
   */
  private void setEvents() {

    javaSort.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {

        gamesInfo = Files.getInfoFiles();
        gamesInfo = java.sort(gamesInfo, timeText);
        model.setRowCount(0);
        for (int i = 0; i < gamesInfo.length; i++) {
          model.addRow(new Object[] { gamesInfo[i].getScore()});
        }
      }

    });

    scalaSort.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {

        gamesInfo = Files.getInfoFiles();
        gamesInfo = scala.sort(gamesInfo, gamesInfo.length, timeText);
        model.setRowCount(0);
        for (int i = 0; i < gamesInfo.length; i++) {
          model.addRow(new Object[] { gamesInfo[i].getScore()});
        }
      }
    });
    
    scalaNot.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent arg0) {
        	if(table.getSelectedRow() != -1)
        	new ScalaStatistics().not(gamesInfo[table.getSelectedRow()]);
        }
      });
  }

}
