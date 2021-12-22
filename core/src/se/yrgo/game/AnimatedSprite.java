package se.yrgo.game;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * A sprite that is animated and moves. 
 * 
 * The sprite keeps track of its size and position and moves when
 * updated. If a bound is given it never moves outside that bound.
 * 
 */
public class AnimatedSprite {
    private Texture texture;
    private TextureRegion[] regions;
    private Animation<TextureRegion> animation;
    private Rectangle position;
    private Rectangle bounds;
    private float deltaX;
    private float deltaY;

    /**
     * Create a new animated sprite from an image file.
     * 
     * The image file will be split into regions based on the given height
     * and width and used to animate the sprite
     * 
     * @param filename the image file used as texture
     * @param x the initial x position
     * @param y the initial y position
     * @param width the width of the sprite
     * @param height the height of the sprite
     */
    public AnimatedSprite(String filename, int x, int y, int width, int height) {
        texture = new Texture(filename);
        position = new Rectangle(x, y, width, height);
        regions = createRegions(texture, width, height);
        animation = new Animation<>(0.15f, regions);
        bounds = null;
    }
    
    /**
     * Create a new animated sprite from a texture.
     * 
     * The texture will be split into regions based on the given height
     * and width and used to animate the sprite
     * 
     * @param texture the texture to use
     * @param x the initial x position
     * @param y the initial y position
     * @param width the width of the sprite
     * @param height the height of the sprite
     */
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

    /**
     * Update the position of the sprite according to the
     * delta x and delta y.
     * 
     * If a bound has been set the sprite is never moved outside
     * these bounds by this method.
     * 
     * @param deltaTime the number of seconds since last update
     */
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

    /**
     * Setting a bound make sure that the sprite is never
     * moved outside that bound.
     * 
     * Setting the bounds to null will remove any restrictions on
     * the sprite's movements.
     * 
     * @param bounds rectangle to keep the sprite within
     */
    public void setBounds(Rectangle bounds) {
        this.bounds = new Rectangle(bounds);
    }

    /**
     * Set the position of the sprite. This method will ignore any bounds.
     *
     * @param x the horizontal position
     * @param y the vertical position
     */
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

    /**
     * Set the delta x speed for this sprite in pixels per second.
     * 
     * @param deltaX horizontal movement in pixels per second
     */
    public void setDeltaX(float deltaX) {
        this.deltaX = deltaX;
    }

    public float getDeltaY() {
        return deltaY;
    }

    /**
     * Set the delta y speed for this sprite in pixels per second.
     * 
     * @param deltaY vertical movement in pixels per second
     */
    public void setDeltaY(float deltaY) {
        this.deltaY = deltaY;
    }

    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }

    /**
     * Does this sprite overlap the other in any way with the given sprite.
     * 
     * @param other sprite to check for collision
     * @return true if they overlap, false otherwise
     */
    public boolean overlaps(AnimatedSprite other) {
        return position.overlaps(other.position);
    }
}
