package se.yrgo.game;

import com.badlogic.gdx.Game;

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
