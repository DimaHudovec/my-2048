package supergame;

import java.util.Random;

/**
 * Класс для работы бот
 * @author hudov
 *
 */
public class Bot extends GameBoard  {
	public static int DELAY = 20;
	public Bot(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	

	/**
	 * Функция иммитации нажатия кнопок
	 */
	@Override
	protected void checkKeys() {
		if(DELAY == 0){
		Random random = new Random();
				int value = random.nextInt(4);
				
				if(value == 0){
					//move tiles left
					moveTiles(Direction.LEFT);
					if(!hasStarted)hasStarted = true;
				}
				if(value == 1){
					//move tiles right
					moveTiles(Direction.RIGHT);
					if(!hasStarted)hasStarted = true;
				}
				if(value == 2){
					//move tiles up
					moveTiles(Direction.UP);
					if(!hasStarted)hasStarted = true;
				}
				if(value == 3){
					//move tiles down
					moveTiles(Direction.DOWN);

					if(!hasStarted)hasStarted = true;
				}
			DELAY = 0;	
		}
		else
			DELAY --;
				
	  }
}

