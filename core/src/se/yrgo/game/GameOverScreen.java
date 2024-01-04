package se.yrgo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * Screen for game over. Shows the points for the last game
 * and will start a new game for any keypress. 
 * 
 */
public class GameOverScreen extends ScreenAdapter implements InputProcessor {
    private AlienGame alienGame;
    private SpriteBatch batch;
    private AnimatedSprite alienHead;
    private BitmapFont bigFont;
    private BitmapFont smallFont;
    private float elapsedTime = 0;

    public GameOverScreen(AlienGame alienGame) {
        int width = Gdx.graphics.getWidth();

        this.alienGame = alienGame;
        this.batch = new SpriteBatch();
        this.bigFont = new BitmapFont();
        this.alienHead = new AnimatedSprite("alienhead.png", (width/2) - (219/2), 130, 219, 240);
        
        // should maybe use Label instead of drawing with fonts directly
        // also, scaling bitmap fonts are really ugly

        final Color fontColor = Color.FIREBRICK;

        this.bigFont.setColor(fontColor);
        this.bigFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        this.bigFont.getData().setScale(2.5f);

        this.smallFont = new BitmapFont();
        this.smallFont.setColor(fontColor);
        this.smallFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        this.smallFont.getData().setScale(1.5f);
    }

    @Override
    public void dispose() {
        bigFont.dispose();
        smallFont.dispose();
        alienHead.dispose();
        batch.dispose();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void render(float delta) {
        // we need elapsed time for our animations
        elapsedTime += delta;

        ScreenUtils.clear(0.75f, 0.75f, 0.75f, 1);

        batch.begin();
        bigFont.draw(batch, "Game Over!", 100, 100, 600, Align.center, false);

        String points = String.format("You scored: %d", alienGame.getPoints());
        smallFont.draw(batch, points, 100, 50, 600, Align.center, false);

        alienHead.draw(batch, elapsedTime);
        batch.end();
    }

    @Override
    public void show() {
        elapsedTime = 0;
        Gdx.input.setInputProcessor(this);
    }
    
    @Override
    public boolean keyTyped(char character) {
        // wait a second before accepting key strokes
        // since the player may hammer the keyboard as part of playing
        if (elapsedTime > 1) {
            alienGame.newGame();
        }
        
        return true;
    }
    
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

	@Override
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
		return false;
	}
    
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
