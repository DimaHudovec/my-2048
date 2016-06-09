package supergame;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * Класс потока для генерации нажатия клавиш
 * @author hudov
 *
 */
public class BotTread extends Thread {

	@Override
	public void run() {
		while(true){
			Random random = new Random();
			int value = random.nextInt(4);
			if (value == 0){
				try {
				      Robot rb=new Robot();
				      rb.keyPress(KeyEvent.VK_LEFT);
				      rb.keyRelease(KeyEvent.VK_LEFT);
				    }
				    catch (AWTException ex) {System.err.println("Robot error");};
			}
			if (value == 1){
				try {
				      Robot rb=new Robot();
				      rb.keyPress(KeyEvent.VK_UP);
				      rb.keyRelease(KeyEvent.VK_UP);
				    }
				    catch (AWTException ex) {System.err.println("Robot error");};
			}
			if (value == 2){
				try {
				      Robot rb=new Robot();
				      rb.keyPress(KeyEvent.VK_DOWN);
				      rb.keyRelease(KeyEvent.VK_DOWN);
				    }
				    catch (AWTException ex) {System.err.println("Robot error");};
			}
			if (value == 3){
				try {
				      Robot rb=new Robot();
				      rb.keyPress(KeyEvent.VK_RIGHT);
				      rb.keyRelease(KeyEvent.VK_RIGHT);
				    }
				    catch (AWTException ex) {System.err.println("Robot error");};
			}

		}
		
	}
	

}
