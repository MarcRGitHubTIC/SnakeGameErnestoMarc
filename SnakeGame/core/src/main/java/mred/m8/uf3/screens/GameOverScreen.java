package mred.m8.uf3.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import mred.m8.uf3.SnakeGame;
import mred.m8.uf3.helpers.AssetManager;

public class GameOverScreen extends ScreenAdapter {
    private SnakeGame game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private BitmapFont font;
    private int finalScore;

    public GameOverScreen(SnakeGame game, int score) {
        this.game = game;
        this.batch = game.batch;
        this.camera = game.camera;
        this.font = AssetManager.getFont();
        this.finalScore = score;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        font.draw(batch, "Game Over\nScore: " + finalScore + "\nTap to Retry", 250, 300);
        batch.end();

        if (Gdx.input.justTouched()) {
            game.setScreen(game.getGameScreen());
        }
    }
}
