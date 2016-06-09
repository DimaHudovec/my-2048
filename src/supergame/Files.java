package supergame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Files {
  /**
   * Класс для работы с файлами
   */
  private static final String SCORE = "SCORE";
  private String nameFile;

  public Files() {
    StringBuilder string = new StringBuilder();
    string.append(new SimpleDateFormat().format(Calendar.getInstance().getTime()));

    nameFile = 
        "Save" + File.separator + string.toString().replaceAll("\\:|\\.|\\ |", "") + ".save";
    File file = new File("Save");
    
    if (!file.exists()) {
      file.mkdirs();
    }
  }

  /**
   * Чтения сохранений из файла
   * 
   * @param path Имя файла
   * @return Строка из файла
   */
  public static String readSave(String path) {
    File file = new File("Save" + File.separator + path);
    StringBuilder string = new StringBuilder();
    String save;
    if (!file.exists()) {
      return null;
    }
    try {
      BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
      try {
        while ((save = in.readLine()) != null) {
          string.append(save);
        }
      } finally {
        in.close();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
   // GameBoardReplay.amount = amount - 1;
    return string.toString();
  }

  /**
   * Запись Hidescore в файл
   * 
   * @param score Набранные очки
   */
  public static void writeScore(int score) {
    File file = new File(SCORE);
    try {
      if (file.exists()) {
        file.delete();
      }
      file.createNewFile();

      PrintWriter out = new PrintWriter(file.getAbsoluteFile());
      try {
        out.print(Integer.toString(score));
      } finally {
        out.close();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Чтение hightscore из файла
   * 
   * @return hightscore Рекорд
   */
  public static int readScore() {
    String score;
    File file = new File(SCORE);
    if (!file.exists()) {
      return 0;
    }
    try {
      BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
      try {
        score = in.readLine();
      } finally {
        in.close();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return Integer.valueOf(score);
  }

  /**
   * Запись шагов в файл
   * @param save Сохранение
   * @param score Очки
   */
  public void writeSave(String save, int score) {
	  StringBuilder string = new StringBuilder();
	    string.append(new SimpleDateFormat().format(Calendar.getInstance().getTime()));
	    nameFile = 
	        "Save" + File.separator + string.toString().replaceAll("\\:|\\.|\\ |", "") + score + ".save";

    File file = new File(nameFile);
    try {
      if (file.exists()) {
        file.delete();
      }
      PrintWriter out = new PrintWriter(file.getAbsoluteFile());
      try {
        out.println(Integer.toString(score) + ";");
        out.print(save);
      } finally {
        out.close();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Получение информации о играх из файлов
   * 
   * @return Информация о играх
   */
  public static GameInfo[] getInfoFiles() {

    File directory = new File("Save");
    File[] files = directory.listFiles();

    GameInfo[] gamesInfo = new GameInfo[files.length];
    for (int i = 0; i < files.length; i++) {
      gamesInfo[i] = new GameInfo();
      gamesInfo[i].setName(files[i].getName());

      File file = new File("Save" + File.separator + gamesInfo[i].getName());
      if (!file.exists()) {
        return null;
      }
      try {
        BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
        try {
          gamesInfo[i].setScore(Integer.valueOf(in.readLine().replaceAll(";", "")));
          gamesInfo[i].setSteps(in.readLine());
        } finally {
          in.close();
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    return gamesInfo;
  }
}
