package supergame;

import java.awt.image.BufferedImage;



public class GameBoardReplay extends GameBoard {

	String[] save;
	  public static int amount = 0;
	  private int count;

	  /**
	   * 
	   * @param path Имя файла для воспроизведения
	   */
	  public GameBoardReplay(int x, int y, String path) {
	    super();
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
	    save = Files.readSave(path).split(";");
	    count = 1;
	    while (save[count].charAt(0) == '0') {
	      spawn(Integer.valueOf(save[count].charAt(1) + ""),
	          Integer.valueOf(save[count].charAt(2) + ""),
	          Integer.valueOf(save[count].charAt(3) + ""));

	      count++;
	      if (count == 3) {
	        break;
	      }
	    }
		
		dead = checkDead();
		won = checkWon();
	  }

	  /**
	   * Создание плитки
	   * 
	   * @param value tile
	   * @param row, where tail is located
	   * @param col
	   */
	  private void spawn(int value, int row, int col) {
	    board[row][col] = new Tile(value, getTileX(col), getTileY(row));
	  }


	  /**
	   * Получение нажатой клвиши из файла
	   */
	  protected void checkKeys() {

	    switch (save[count].charAt(0)) {
	      case '1':
	        movingTiles(Direction.LEFT);
	        break;
	      case '2':
	        movingTiles(Direction.RIGHT);
	        break;
	      case '3':
	        movingTiles(Direction.UP);
	        break;
	      case '4':
	        movingTiles(Direction.DOWN);
	        break;
	    }
	  }

	  /**
	   * Перемещение плиток
	   * 
	   * @param dir - direction
	   */
	  private void movingTiles(Direction dir) {
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
				 spawn(Integer.valueOf(save[count].charAt(1) + ""),
				          Integer.valueOf(save[count].charAt(2) + ""), Integer.valueOf(save[count].charAt(3) + ""));
				setDead(checkDead());
				if (!checkDead()){
				count++;
				}
			}
		

	   }

	}
