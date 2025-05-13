package mred.m8.uf3.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Snake {
    private Array<Vector2> body = new Array<>();
    private Vector2 direction = new Vector2(1, 0);
    private float timer = 0;
    private float moveTime = 0.2f;
    private boolean grow = false;
    private Direction currentDirection = Direction.RIGHT;

    public Snake() {
        body.add(new Vector2(5, 5));
    }

    public void update(float delta) {
        timer += delta;
        if (timer >= moveTime) {
            timer = 0;
            move();
        }

        // Configuración CORRECTA para landscape (horizontal)
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) direction.set(0, 1);    // Arriba (Y+)
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) direction.set(0, -1); // Abajo (Y-)
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) direction.set(-1, 0); // Izquierda (X-)
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) direction.set(1, 0); // Derecha (X+)
    }

    private void move() {
        Vector2 head = new Vector2(body.first());
        head.add(direction);
        body.insert(0, head);
        if (!grow) {
            body.pop();
        } else {
            grow = false;
        }
    }

    public void grow() {
        grow = true;
    }

    public boolean collidesWithApple(Apple apple) {
        return body.first().epsilonEquals(apple.getPosition(), 0.1f);
    }

    public boolean checkSelfCollision() {
        for (int i = 1; i < body.size; i++) {
            if (body.first().epsilonEquals(body.get(i), 0.1f)) return true;
        }
        return false;
    }

    public void setDirection(Direction dir){
        if((dir == Direction.LEFT && currentDirection != Direction.RIGHT) ||
            (dir == Direction.RIGHT && currentDirection != Direction.LEFT) ||
            (dir == Direction.UP && currentDirection != Direction.DOWN) ||
            (dir == Direction.DOWN && currentDirection != Direction.UP)){
            currentDirection = dir;
        }
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.GREEN);
        for (Vector2 part : body) {
            shapeRenderer.rect(part.x * 16, part.y * 16, 32, 32);
        }
    }

}

