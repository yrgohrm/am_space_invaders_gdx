package se.yrgo.game;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class AnimatedSprite {
    private Texture texture;
    private TextureRegion[] regions;
    private Animation<TextureRegion> animation;
    private Rectangle position;
    private Rectangle bounds;
    private float deltaX;
    private float deltaY;

    public AnimatedSprite(String filename, int x, int y, int width, int height) {
        texture = new Texture(filename);
        position = new Rectangle(x, y, width, height);
        regions = createRegions(texture, width, height);
        animation = new Animation<>(0.15f, regions);
        bounds = null;
    }
    
    public AnimatedSprite(Texture texture, int x, int y, int width, int height) {
        position = new Rectangle(x, y, width, height);
        regions = createRegions(texture, width, height);
        animation = new Animation<>(0.15f, regions);
        bounds = null;
    }

    private TextureRegion[] createRegions(Texture texture, int width, int height) {
        TextureRegion[][] regs = TextureRegion.split(texture, width, height);
        List<TextureRegion> res = new ArrayList<>();
        for (int i = 0; i < regs.length; i++) {
            for (int j = 0; j < regs[i].length; j++) {
                res.add(regs[i][j]);
            }
        }
        return res.toArray(new TextureRegion[res.size()]);
    }

    public void update(float deltaTime) {
        position.x += deltaX * deltaTime;
        position.y += deltaY * deltaTime;

        if (bounds != null) {
            if (position.x < bounds.x) {
                position.x = bounds.x;
            }
            else if (position.x + position.width > bounds.x + bounds.width) {
                position.x =  bounds.x + bounds.width - position.width;
            }

            if (position.y < bounds.y) {
                position.y = bounds.y;
            }
            else if (position.y + position.height > bounds.y + bounds.height) {
                position.y =  bounds.y + bounds.height - position.height;
            }
        }
    }

    public void draw(SpriteBatch batch, float elapsedTime) {
        TextureRegion region = animation.getKeyFrame(elapsedTime, true);
        batch.draw(region, position.getX(), position.getY());
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = new Rectangle(bounds);
    }

    public void setPosition(int x, int y) {
        position.setPosition(x, y);
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getDeltaX() {
        return deltaX;
    }

    public void setDeltaX(float deltaX) {
        this.deltaX = deltaX;
    }

    public float getDeltaY() {
        return deltaY;
    }

    public void setDeltaY(float deltaY) {
        this.deltaY = deltaY;
    }

    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }

    public boolean overlaps(AnimatedSprite other) {
        return position.overlaps(other.position);
    }
}
