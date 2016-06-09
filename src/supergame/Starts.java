package supergame;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Создания главного экрана
 * @author hudov
 *
 */
public class Starts {
	
	public static String path;
	/**
	 * Функция Main
	 * @param args
	 */
	public static void main(String[] args){
		
		JFrame window = new JFrame("2048");
		try {
		      String systemLook = UIManager.getSystemLookAndFeelClassName();
		      UIManager.setLookAndFeel(systemLook);
		      SwingUtilities.updateComponentTreeUI(window);
		    } catch (UnsupportedLookAndFeelException e) {
		      System.err.println("Can't use this LookAndFell");
		    } catch (Exception e) {
		      System.err.println("Error");
		    }

	    JFileChooser dialog = new JFileChooser(new File("SAVE"));
	    dialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
	    dialog.setApproveButtonText("Open");
	    dialog.setDialogTitle("Open save");
	    dialog.setDialogType(JFileChooser.OPEN_DIALOG);
	    dialog.setMultiSelectionEnabled(false);

	    if (dialog.showOpenDialog(window) == JFileChooser.APPROVE_OPTION) {
	      path = dialog.getSelectedFile().getName();
	      System.out.println(path);
	    } else {
	      return;
	    }
	    Game game = new Game();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.add(game);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		game.start();
	}
}
