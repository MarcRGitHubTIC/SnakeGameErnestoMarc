package mred.m8.uf3.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import mred.m8.uf3.SnakeGame;
import mred.m8.uf3.actors.Apple;
import mred.m8.uf3.actors.Snake;

public class GameScreen implements Screen {
    private final SnakeGame game;
    private final OrthographicCamera camera;
    private Snake snake;
    private Apple apple;
    private BitmapFont font;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private int score;

    public GameScreen(SnakeGame game, Batch batch, OrthographicCamera camera) {
        this.game = game;
        this.batch = (SpriteBatch) batch;
        this.camera = camera;
        shapeRenderer = new ShapeRenderer();
        score = 0;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // fondo negro
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        snake.update(delta);

        if (snake.collidesWithApple(apple)) {
            snake.grow();
            apple.respawn();
            score++;
        }

        if (snake.checkSelfCollision()) {
            game.setScreen(new StartScreen(game));
        }

        // Dibujo con ShapeRenderer
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        snake.draw(shapeRenderer);
        shapeRenderer.end();

        // Dibujo del texto
        batch.begin();
        font.draw(batch, "Score: " + score, 10, 470);
        batch.end();
    }


    @Override public void resize(int w, int h) {}
    @Override public void show() {}
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void dispose() {}
}

