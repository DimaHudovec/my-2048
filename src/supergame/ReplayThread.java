package supergame;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class ReplayThread extends Thread {
	public static String path;
	
	JFrame change = new JFrame();
	public void run() {
	JFileChooser dialog = new JFileChooser(new File("SAVE"));
    dialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
    dialog.setApproveButtonText("Open");
    dialog.setDialogTitle("Open save");
    dialog.setDialogType(JFileChooser.OPEN_DIALOG);
    dialog.setMultiSelectionEnabled(false);

    if (dialog.showOpenDialog(change) == JFileChooser.APPROVE_OPTION) {
      path = dialog.getSelectedFile().getName();
      System.out.println(path);
    } else {
      return;
    }
	}

}
