package mred.m8.uf3.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import mred.m8.uf3.helpers.AssetManager;

public class Apple {
    private Vector2 position = new Vector2();
    private static final int CELL_SIZE = 32;
    private float scale = 0.8f;

    public Apple(Snake snake) {
        respawn(snake);
    }

    public void respawn(Snake snake) {
        float x, y;
        do {

            x = MathUtils.random(0, 19);
            y = MathUtils.random(0, 14);
            position.set(x, y);
        } while (isAppleOnSnake(snake));
    }

    private boolean isAppleOnSnake(Snake snake) {

        for (Vector2 part : snake.getBody()) {
            if (part.x == position.x && part.y == position.y) {
                return true;
            }
        }
        return false;
    }
    public Vector2 getPosition() {
        return position;
    }

    public void draw(SpriteBatch batch) {
        Texture appleTexture = AssetManager.getApple();
        if (appleTexture != null) {
            float width = CELL_SIZE * scale;
            float height = CELL_SIZE * scale;
            float offsetX = (CELL_SIZE - width) / 2;
            float offsetY = (CELL_SIZE - height) / 2;

            batch.draw(appleTexture,
                position.x * CELL_SIZE + offsetX,
                position.y * CELL_SIZE + offsetY,
                width, height);
        }
    }
}
