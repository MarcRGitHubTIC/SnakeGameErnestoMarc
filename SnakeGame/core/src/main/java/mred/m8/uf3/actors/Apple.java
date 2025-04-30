package mred.m8.uf3.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Apple {
    private Vector2 position;

    public Apple() {
        respawn();
    }

    public void respawn() {
        position = new Vector2(MathUtils.random(0, 30), MathUtils.random(0, 30));
    }

    public Vector2 getPosition() {
        return position;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(new Texture("apple.png"), position.x * 16, position.y * 16, 16, 16);
    }
}

