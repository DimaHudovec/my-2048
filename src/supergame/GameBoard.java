package supergame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Random;




public class GameBoard {


	private Files file = new Files();
	private StringBuilder save = new StringBuilder();
	
	public static int ROWS = 4;
	public static int COLS = 4;

	private final int startingTiles = 2;
	protected Tile[][] board;
	protected boolean dead;
	protected boolean won;
	protected BufferedImage gameBoard;
	protected BufferedImage finalBoard;
	protected int x;
	protected int y;
	protected int score = 0;
	protected int hightScore = 0;
	protected int hightLoad = 0;
	protected Font scoreFont;
	private Thread bot;
	private boolean botHasStarted = false;

	private static int SPACING = 10;
	public static int BOARD_WIDTH = (COLS + 1) * SPACING + COLS * Tile.WIDTH;
	public static int BOARD_HEIGHT = (ROWS + 1) * SPACING + ROWS * Tile.HEIGHT;

	protected boolean hasStarted;

	protected String saveDataPath;
	private String fileName = "SaveData4";

	public GameBoard(){}
	public GameBoard(int x, int y) {
		try {
			saveDataPath = GameBoard.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
		} catch (Exception e) {
			e.printStackTrace();
		}

		scoreFont = Game.main.deriveFont(24f);
		this.x = x;
		this.y = y;
		board = new Tile[ROWS][COLS];
		gameBoard = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_RGB);
		finalBoard = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		loadHighScore();
		createBoardImage();
		start();
		
		dead = checkDead();
		won = checkWon();

	}
	/**
	 * Функция очистки игровой панели
	 */
	public void reset(){
		board = new Tile[ROWS][COLS];
		start();
		score = 0;
		dead = false;
		won = false;
		hasStarted = false;
	}

	private void createSaveData(){
		try{
			File file = new File(saveDataPath, fileName);
			
			FileWriter output = new FileWriter(file);
			BufferedWriter writer = new BufferedWriter(output);
			writer.write("" + 0);
			writer.close();
		}
		catch(Exception e){
			e.printStackTrace() ;
		} 
	}

	/**
	 * Получение Hide score
	 */
	protected void loadHighScore() {
		try{
			File f = new File(saveDataPath,fileName);
			if(!f.isFile()){
				createSaveData();
			}
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			hightScore = Integer.parseInt(reader.readLine());
			hightLoad = hightScore;
			reader.close();
		}
		catch(Exception e){
			e.printStackTrace() ;
		}
	}

	/**
	 * Сохранение Hide score
	 */
	private void setHighScore() {
		FileWriter output = null;
		try{
			File f = new File(saveDataPath,fileName);
			output = new FileWriter(f);
			BufferedWriter writer = new BufferedWriter(output);
			
			writer.write("" + score);	
					
			writer.close();
		}
		catch(Exception e){
			e.printStackTrace() ;
		}
	}

	/**
	 * Создание изображения игровой панели
	 */
	protected void createBoardImage() {
		Graphics2D g = (Graphics2D) gameBoard.getGraphics();
		g.setColor(Color.darkGray);
		g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
		g.setColor(Color.lightGray);

		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				int x = SPACING + SPACING * col + Tile.WIDTH * col;
				int y = SPACING + SPACING * row + Tile.HEIGHT * row;
				g.fillRoundRect(x, y, Tile.WIDTH, Tile.HEIGHT, Tile.ARC_WIDTH, Tile.ARC_HEIGHT);
			}
		}
	}

	protected void start() {
		for (int i = 0; i < startingTiles; i++) {
			spawnRandom(Direction.NULL);
		}
	}
	
	/**
	 * Генерация новых чисел на игровой панели
	 * @param dir
	 */
	private void spawnRandom(Direction dir) {
		Random random = new Random();
		boolean notValid = true;
		while (notValid) {
			int location = random.nextInt(ROWS * COLS);
			int row = location / ROWS;
			int col = location % COLS;
			Tile current = board[row][col];
			if (current == null) {
				int value = random.nextInt(10) < 9 ? 2 : 4;
				Tile tile = new Tile(value, getTileX(col), getTileY(row));
				board[row][col] = tile;
				notValid = false;
				save.append(convertToString(dir, value, row, col));
			}
		}
	}

	public int getTileX(int col) {
		return SPACING + col * Tile.WIDTH + col * SPACING;
	}

	public int getTileY(int row) {
		return SPACING + row * Tile.HEIGHT + row * SPACING;
	}

	/**
	 * Воспроизведение игрового процесса
	 * @param g
	 */
	public void render(Graphics2D g) {
		Graphics2D g2d = (Graphics2D) finalBoard.getGraphics();
		g2d.drawImage(gameBoard, 0, 0, null);

		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				Tile current = board[row][col];
				if (current == null)
					continue;
				current.render(g2d);
			}
		}

		g.drawImage(finalBoard, x, y, null);
		g2d.dispose();
		g.setColor(Color.lightGray);
		g.setFont(scoreFont);
		g.drawString("" + score, 30, 40);
		g.setColor(Color.red);
		g.drawString("Best: " + hightScore, Game.WIDTH - DrawUtils.getMessageWidth("Best: " + hightScore, scoreFont, g) - 20, 40);
	}

	/**
	 * Обновление игрового процесса
	 */
	public void update() {
		checkKeys();
		if(score >= hightScore){
			hightScore = score;
			hightLoad = hightScore;
		}

		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				Tile current = board[row][col];
				if (current == null)
					continue;
				current.update();
				resetPositin(current, row, col);
				if (current.getValue() == 2048) {
					setWon(true);
				}
			}
		}
	}

	private void resetPositin(Tile current, int row, int col) {
		if (current == null)
			return;

		int x = getTileX(col);
		int y = getTileY(row);

		int distX = current.getX() - x;
		int distY = current.getY() - y;

		if (Math.abs(distX) < Tile.SLIDE_SPEED) {
			current.setX(current.getX() - distX);
		}

		if (Math.abs(distY) < Tile.SLIDE_SPEED) {
			current.setY(current.getY() - distY);
		}

		if (distX < 0) {
			current.setX(current.getX() + Tile.SLIDE_SPEED);
		}

		if (distY < 0) {
			current.setY(current.getY() + Tile.SLIDE_SPEED);
		}

		if (distX > 0) {
			current.setX(current.getX() - Tile.SLIDE_SPEED);
		}

		if (distY > 0) {
			current.setY(current.getY() - Tile.SLIDE_SPEED);
		}
	}

	/**
	 * Перемещение плитки в заданную сторону
	 * @param row
	 * @param col
	 * @param horizontalDirection
	 * @param verticalDirection
	 * @param dir
	 * @return
	 */
	protected boolean move(int row, int col, int horizontalDirection, int verticalDirection, Direction dir) {
		boolean canMove = false;

		Tile current = board[row][col];
		if (current == null)
			return false;
		boolean move = true;
		int newCol = col;
		int newRow = row;
		while (move) {
			newCol += horizontalDirection;
			newRow += verticalDirection;
			if (checOutOfBounds(dir, newRow, newCol))
				break;
			if (board[newRow][newCol] == null) {
				board[newRow][newCol] = current;
				board[newRow - verticalDirection][newCol - horizontalDirection] = null;
				board[newRow][newCol].setSlideTo(new Point(newRow, newCol));
				canMove = true;
			} else if (board[newRow][newCol].getValue() == current.getValue() && board[newRow][newCol].canCombine()) {
				board[newRow][newCol].setCanCombine(false);
				board[newRow][newCol].setValue(board[newRow][newCol].getValue() * 2);
				canMove = true;
				board[newRow - verticalDirection][newCol - horizontalDirection] = null;
				board[newRow][newCol].setSlideTo(new Point(newRow, newCol));
				board[newRow][newCol].setCombineAnimation(true);
				score += board[newRow][newCol].getValue();
			} else {
				move = false;
			}
		}
		return canMove;
	}

	/**
	 * Проверка положения плитки
	 * @param dir
	 * @param row
	 * @param col
	 * @return
	 */
	private boolean checOutOfBounds(Direction dir, int row, int col) {
		if (dir == Direction.LEFT) {
			return col < 0;
		} else if (dir == Direction.RIGHT) {
			return col > COLS - 1;
		} else if (dir == Direction.UP) {
			return row < 0;
		} else if (dir == Direction.DOWN) {
			return row > ROWS - 1;
		}
		return false;
	}

	/**
	 * Перемещение каждой плитки
	 * @param dir
	 */
	public void moveTiles(Direction dir) {
		boolean canMove = false;
		int horizontalDirection = 0;
		int verticalDirection = 0;

		if (dir == Direction.LEFT) {
			horizontalDirection = -1;
			for (int row = 0; row < ROWS; row++) {
				for (int col = 0; col < COLS; col++) {
					if (!canMove) {
						canMove = move(row, col, horizontalDirection, verticalDirection, dir);
					} else
						move(row, col, horizontalDirection, verticalDirection, dir);
				}
			}
		}

		else if (dir == Direction.RIGHT) {
			horizontalDirection = 1;
			for (int row = 0; row < ROWS; row++) {
				for (int col = COLS - 1; col >= 0; col--) {
					if (!canMove) {
						canMove = move(row, col, horizontalDirection, verticalDirection, dir);
					} else
						move(row, col, horizontalDirection, verticalDirection, dir);
				}
			}
		}

		else if (dir == Direction.UP) {
			verticalDirection = -1;
			for (int row = 0; row < ROWS; row++) {
				for (int col = 0; col < COLS; col++) {
					if (!canMove) {
						canMove = move(row, col, horizontalDirection, verticalDirection, dir);
					} else
						move(row, col, horizontalDirection, verticalDirection, dir);
				}
			}
		}

		else if (dir == Direction.DOWN) {
			verticalDirection = 1;
			for (int row = ROWS - 1; row >= 0; row--) {
				for (int col = 0; col < COLS; col++) {
					if (!canMove) {
						canMove = move(row, col, horizontalDirection, verticalDirection, dir);
					} else
						move(row, col, horizontalDirection, verticalDirection, dir);
				}
			}
		}

		else {
			System.out.println(dir + "is not a valid direction.");
		}

		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				Tile current = board[row][col];
				if (current == null)
					continue;
				current.setCanCombine(true);
			}
		}
		if (canMove) {
			spawnRandom(dir);
			setDead(checkDead());
		}
	}

	/**
	 * Проверка конца игры
	 * @return
	 */
	public boolean checkDead() {
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				if (board[row][col] == null)
					return false;
				boolean canCombine = checkSurroundingTiles(row, col, board[row][col]);
				if (canCombine) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Проверка победы в игре
	 * @return
	 */
	protected boolean checkWon() {
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				if (board[row][col] == null) continue;
				if (board[row][col].getValue() >= 2048) return true;
			}
		}
		return false;
	}

	/**
	 * Проверка соседних плиток
	 * @param row
	 * @param col
	 * @param current
	 * @return
	 */
	private boolean checkSurroundingTiles(int row, int col, Tile current) {
		if (row > 0) {
			Tile check = board[row - 1][col];
			if (check == null)
				return true;
			if (current.getValue() == check.getValue())
				return true;
		}
		if (row < ROWS - 1) {
			Tile check = board[row + 1][col];
			if (check == null)
				return true;
			if (current.getValue() == check.getValue())
				return true;
		}
		if (col > 0) {
			Tile check = board[row][col - 1];
			if (check == null)
				return true;
			if (current.getValue() == check.getValue())
				return true;
		}
		if (col < COLS - 1) {
			Tile check = board[row][col + 1];
			if (check == null)
				return true;
			if (current.getValue() == check.getValue())
				return true;
		}
		return false;
	}

	/**
	 * Проверка нажатых клавиш
	 */
	@SuppressWarnings("deprecation")
	protected void checkKeys() {
		if (Keyboard.typed(KeyEvent.VK_LEFT)) {
			// move tiles left
			moveTiles(Direction.LEFT);
			if (!hasStarted)
				hasStarted = !dead;
		}
		if (Keyboard.typed(KeyEvent.VK_RIGHT)) {
			// move tiles right
			moveTiles(Direction.RIGHT);
			if (!hasStarted)
				hasStarted = !dead;
		}
		if (Keyboard.typed(KeyEvent.VK_UP)) {
			// move tiles up
			moveTiles(Direction.UP);
			if (!hasStarted)
				hasStarted = !dead;
		}
		if (Keyboard.typed(KeyEvent.VK_DOWN)) {
			// move tiles down
			moveTiles(Direction.DOWN);

			if (!hasStarted)
				hasStarted = !dead;
		}
		if (Keyboard.typed(KeyEvent.VK_B)) {
			if(!botHasStarted){
				bot = new BotTread();
			bot.start();
			botHasStarted = true;
			}
			else{
				bot.stop();
				botHasStarted = false;
			}
		}
		if(dead && botHasStarted){
			bot.stop();
			//bot.destroy();
			botHasStarted = false;
		}
		
	}

	public boolean isDead() {
		return dead;
	}
	
	public void setDead(boolean dead) {
		if(!this.dead && dead){
			if(score >= hightScore){
			setHighScore();
			}
		}
		this.dead = dead;
	}
	
	public boolean isWon() {
		return won;
	}
	
	public void setWon(boolean won) {
		if(!this.won && won){
			if(score >= hightScore){
				setHighScore();
				}
		}
		this.won = won;
	}
	
	public int getScore() {
		return score;		
	}
	
	public void setScore(int score) {
		this.score = score;		
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	

	
	public boolean checkToDown() {
		Tile current;
		Tile check;
		int count = 0;
		for (int row = 1; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				current = board[row][col];
				if (current == null)
					continue;
				check = board[row - 1][col];
				if (check == null)
					continue;
				if (current.getValue() == check.getValue())
					count++;
			}
		}
		if (count > 0)
			return true;
		return false;
	}
	public static void setROWS(int rOWS) {
		ROWS = rOWS;
	}
	public static void setCOLS(int cOLS) {
		COLS = cOLS;
	}
	
	/**
	 * Конвертация информации в String для записи в файл
	 * @param dir
	 * @param value
	 * @param line
	 * @param column
	 * @return
	 */
	private String convertToString(Direction dir, int value, int line, int column) {
	    String string = new String();
	    switch (dir) {

	      case LEFT:
	        string =
	            "1" + Integer.toString(value) + Integer.toString(line) + Integer.toString(column) + ';';
	        break;
	      case RIGHT:
	        string =
	            "2" + Integer.toString(value) + Integer.toString(line) + Integer.toString(column) + ';';
	        break;
	      case UP:
	        string =
	            "3" + Integer.toString(value) + Integer.toString(line) + Integer.toString(column) + ';';
	        break;
	      case DOWN:
	        string =
	            "4" + Integer.toString(value) + Integer.toString(line) + Integer.toString(column) + ';';
	        break;
	      default:
	        string =
	            "0" + Integer.toString(value) + Integer.toString(line) + Integer.toString(column) + ';';
	        break;
	    }

	    return string;
	  }

	/**
	 * Запись в файл
	 */
	public void writeInFile() {
	    file.writeSave(save.toString(), score);
	  }
}