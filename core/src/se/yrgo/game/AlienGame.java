package se.yrgo.game;

import com.badlogic.gdx.Game;

/**
 * This is the class for the main game. It controlls the different
 * screens that exist. Screens lets us divide our program into
 * separate entities that need different handling such as
 * menus, different game modes, end screens etc.
 */
public class AlienGame extends Game {
	private GameScreen gameScreen;
	private GameOverScreen gameOverScreen;

	private int points;

	@Override
    public void create () {
		gameScreen = new GameScreen(this);
		gameOverScreen = new GameOverScreen(this);
        newGame();
    }

    @Override
    public void dispose () {
        gameScreen.dispose();
    }

	public void addPoints(int points) {
		this.points += points;
	}

	public int getPoints() {
		return points;
	}

	public void newGame() {
		points = 0;
		setScreen(gameScreen);
	}

	public void gameOver() {
		setScreen(gameOverScreen);
	}
}
