package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import supergame.DrawUtils;
import supergame.Game;
/**
 * Класс для главного экрана 
 * @author hudov
 *
 */
public class MainMenuPanel extends GuiPanel {

	private Font tileFont = Game.main.deriveFont(100f);
	private Font creatorFont = Game.main.deriveFont(24f);
	private String title = "2048";
	private String creator = "© Dima Hudovec";
	private int buttonWidth = 220;
	private int buttonHeight = 60;
	private int spacing = 90;

	  /**
	   * Конструктор экрана главного меню 
	   */
	public MainMenuPanel() {
		super();
		GuiButton playButton4 = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, 220, buttonWidth / 2, buttonHeight);
		GuiButton playButton5 = new GuiButton(Game.WIDTH / 2 , 220, buttonWidth / 2, buttonHeight);
		GuiButton replyButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, playButton5.getY() + spacing, buttonWidth / 2,
				buttonHeight);
		GuiButton autoButton = new GuiButton(Game.WIDTH / 2 , playButton5.getY() + spacing, buttonWidth / 2,
				buttonHeight);
		GuiButton quitButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, autoButton.getY() + spacing, buttonWidth / 2,
				buttonHeight);
		GuiButton statButton = new GuiButton(Game.WIDTH / 2 , autoButton.getY() + spacing, buttonWidth / 2,
				buttonHeight);

		playButton4.setText("Play 4x4");
		playButton5.setText("Play 5x5");
		replyButton.setText("Replay");
		autoButton.setText("Auto");
		quitButton.setText("Quit");
		statButton.setText("Statistic");

		playButton4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GuiScreen.getInstance().setCurrentPanel("Play4x4");

			}
		});
		playButton5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GuiScreen.getInstance().setCurrentPanel("Play5x5");

			}
		});

		replyButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				/*Thread change = new ReplayThread();
				change.start();
				try {
					change.join();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}*/
				GuiScreen.getInstance().setCurrentPanel("Replay");
			}
		});
		
		autoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GuiScreen.getInstance().setCurrentPanel("Auto");
			}
		});

		quitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		statButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new supergame.Statistics();
			}
		});

		add(playButton4);
		add(playButton5);
		add(replyButton);
		add(autoButton);
		add(quitButton);
		add(statButton);
		
	}
	/**
	 * Функция для воспроизведегия главного экрана
	 */
	@Override
	public void render(Graphics2D g) {
		super.render(g);
		g.setFont(tileFont);
		g.setColor(Color.black);
		g.drawString(title, Game.WIDTH / 2 - DrawUtils.getMessageWidth(title, tileFont, g) / 2, 150);
		g.setFont(creatorFont);
		g.drawString(creator, 20, Game.HEIGHT - 10);
	}

}
