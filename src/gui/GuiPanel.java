package gui;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GuiPanel {

	private ArrayList<GuiButton> buttons;

	public GuiPanel() {
		buttons = new ArrayList<GuiButton>();
	}

	public void update() {
		for (GuiButton b : buttons) {
			b.update();
		}
	}

	public void render(Graphics2D g) {
		
		try{
			for (GuiButton b : buttons) {
				b.render(g);}
			} catch(Exception e1){
				System.out.println();
			}
		}
		

	public void add(GuiButton button) {
		buttons.add(button);
	}

	public void remove(GuiButton button) {
		buttons.remove(button);
	}

	public void mousePressed(MouseEvent e) {
		for (GuiButton b : buttons) {
			b.mousePressed(e);
		}
	}

	public void mouseReleased(MouseEvent e) {
		try{
			for (GuiButton b : buttons) {
		
			
			b.mouseReleased(e);}
			} catch(Exception e1){
				System.out.println();
			}
		}
	

	public void mouseDragged(MouseEvent e) {
		for (GuiButton b : buttons) {
			b.mouseDragged(e);
		}
	}

	public void mouseMoved(MouseEvent e) {
		for (GuiButton b : buttons) {
			b.mouseMoved(e);
		}

	}
}
