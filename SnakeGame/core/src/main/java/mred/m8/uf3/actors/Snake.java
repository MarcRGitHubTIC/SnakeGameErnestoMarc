package mred.m8.uf3.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import mred.m8.uf3.helpers.AssetManager;
import mred.m8.uf3.screens.StartScreen;

public class Snake {

    private Array<Vector2> body = new Array<>();
    private Vector2 direction = new Vector2(1, 0);
    private float timer = 0;
    private float moveTime = 0.2f;
    private boolean grow = false;
    private Direction currentDirection = Direction.RIGHT;
    private Texture headTexture;
    private Texture bodyTexture;
    private float rotation = 0;

    // ... (otras variables)
    private static final int GRID_WIDTH = 20;  // Ancho en celdas (ajusta según tu juego)
    private static final int GRID_HEIGHT = 15; // Alto en celdas (ajusta según tu juego)

    public Snake() {
        headTexture = new Texture("head.png");
        bodyTexture = new Texture("body.png");

        headTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        bodyTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        body.add(new Vector2(5, 5));
    }

    public void update(float delta) {
        timer += delta;
        if (timer >= moveTime) {
            timer = 0;
            move();
        }
    }

    private void move() {
        // Guardar posiciones anteriores
        Array<Vector2> previousPositions = new Array<>();
        for (Vector2 segment : body) {
            previousPositions.add(new Vector2(segment));
        }

        // Mover la cabeza
        Vector2 head = body.first();
        switch (currentDirection) {
            case UP: head.y += 1; break;
            case DOWN: head.y -= 1; break;
            case LEFT: head.x -= 1; break;
            case RIGHT: head.x += 1; break;
        }

        // Aplicar teletransporte si sale de los límites
        if (head.x >= GRID_WIDTH) head.x = 0;
        else if (head.x < 0) head.x = GRID_WIDTH - 1;

        if (head.y >= GRID_HEIGHT) head.y = 0;
        else if (head.y < 0) head.y = GRID_HEIGHT - 1;

        // Mover el cuerpo
        for (int i = 1; i < body.size; i++) {
            body.get(i).set(previousPositions.get(i-1));
        }

        // Manejar crecimiento
        if (grow) {
            body.add(new Vector2(previousPositions.peek()));
            grow = false;
        }
    }
    public void grow() {
        grow = true;
    }

    public boolean collidesWithApple(Apple apple) {
        if (apple == null || body.size == 0) return false;

        Vector2 head = body.first();
        Vector2 applePos = apple.getPosition();

        // Verifica colisión con margen de error
        return Math.abs(head.x - applePos.x) < 0.5f &&
            Math.abs(head.y - applePos.y) < 0.5f;
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

    public void draw(SpriteBatch batch) {
        for(int i = 1; i< body.size; i++){
            Vector2 segment = body.get(i);
            batch.draw(bodyTexture,
                segment.x * 32,
                segment.y * 32,
                16,16,
                32,32,
                1,1,
                0,
                0,0,
                32,32,
                false,false);
        }
        updateHeadRotation();
        Vector2 head = body.first();
        batch.draw(headTexture,
            head.x * 32,
            head.y * 32,
            16,16,
            32,32,
            1,1,
            rotation,
            0,0,
            32,32,
            false,false);

    }
    private void updateHeadRotation() {
        switch(currentDirection) {
            case UP:
                rotation = 0;
                break;
            case RIGHT:
                rotation = 270;
                break;
            case DOWN:
                rotation = 180;
                break;
            case LEFT:
                rotation = 90;
                break;
        }
    }

    // Añade este método para liberar recursos
    public void dispose() {
        headTexture.dispose();
        bodyTexture.dispose();
    }

    public Array<Vector2> getBody() {
        return body; // Método que devuelve el cuerpo de la serpiente
    }


}

