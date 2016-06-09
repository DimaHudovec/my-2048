package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import supergame.Bot;
import supergame.DrawUtils;
import supergame.Game;
import supergame.GameBoard;

/**
 * Класс для игровой панели бота
 * @author hudov
 *
 */
public class PlayPanelTest extends GuiPanel {

	private Bot board;
	private BufferedImage info;

	// game over
	private GuiButton tryAgain;
	private GuiButton mainMenu;
	private GuiButton screenShot;
	private int smallButtonWidth = 160;
	private int spacing = 20;
	private int largeButtonWidth = smallButtonWidth * 2 + spacing;
	private int buttonHeght = 50;
	private boolean added;
	private int alpha;
	private Font gameOverFont;
	private boolean screenshot;

	/**
	 * Конструктор класса игровой панели бота
	 */
	public PlayPanelTest() {
		gameOverFont = Game.main.deriveFont(70f);
		board = new Bot(Game.WIDTH / 2 - GameBoard.BOARD_WIDTH / 2, Game.HEIGHT - GameBoard.BOARD_HEIGHT - 10);
		info = new BufferedImage(Game.WIDTH, 200, BufferedImage.TYPE_INT_RGB);

		mainMenu = new GuiButton(Game.WIDTH / 2 - largeButtonWidth / 2, 450, largeButtonWidth, buttonHeght);
		tryAgain = new GuiButton(mainMenu.getX(), mainMenu.getY() - spacing - buttonHeght, smallButtonWidth,
				buttonHeght);
		screenShot = new GuiButton(tryAgain.getX() + tryAgain.getWidth() + spacing, tryAgain.getY(), smallButtonWidth,
				buttonHeght);

		tryAgain.setText("Retry");
		screenShot.setText("ScreenShot");
		mainMenu.setText("Back to Main Menu");

		tryAgain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.reset();
				alpha = 0;

				remove(tryAgain);
				remove(screenShot);
				remove(mainMenu);

				added = false;
			}
		});

		screenShot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screenshot = true;

			}
		});

		mainMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GuiScreen.getInstance().setCurrentPanel("Menu");
			}
		});
	}

	/**
	 * Функция отображения фона панели
	 * @param g
	 */
	private void drawGui(Graphics2D g) {
		Graphics2D g2d = (Graphics2D) info.getGraphics();
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, info.getWidth(), info.getHeight());
		g2d.dispose();
		g.drawImage(info, 0, 0, null);
	}

	/**
	 * Функция отображения понели "Конец игры"
	 * @param g
	 */
	public void drawGameOver(Graphics2D g) {
		g.setColor(new Color(222, 222, 222, alpha));
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		g.setColor(Color.red);
		g.setFont(gameOverFont);
		g.drawString("Game Over", Game.WIDTH / 2 - DrawUtils.getMessageWidth("Game Over", gameOverFont, g) / 2, 180);

	}

	/**
	 * Функция обновления панели
	 */
	@Override
	public void update() {
		board.update();
		if (board.isDead()) {
			alpha++;
			if (alpha > 170)
				alpha = 170;
		}
	}

	
	/**
	 * Функция воспроизведения панели
	 */
	@Override
	public void render(Graphics2D g) {
		drawGui(g);
		board.render(g);
		if (screenshot) {
			BufferedImage bl = new BufferedImage(Game.WIDTH, Game.HEIGHT, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = (Graphics2D) bl.getGraphics();
			g2d.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
			drawGui(g2d);
			board.render(g2d);
			try {
				ImageIO.write(bl, "gif", new File(System.getProperty("user.home") + "\\Desctop",
						"screenshot" + System.nanoTime() + ".gif"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			screenshot  = false;
		}
		if(board.isDead()){
			if(!added){
				added = true;
				board.writeInFile();
				add(mainMenu);
				add(screenShot);
				add(tryAgain);
			}
			drawGameOver(g);
		}
		super.render(g);
	}
}
