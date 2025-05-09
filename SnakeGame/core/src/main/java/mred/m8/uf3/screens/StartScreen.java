package mred.m8.uf3.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import mred.m8.uf3.SnakeGame;
import mred.m8.uf3.helpers.AssetManager;

public class StartScreen extends ScreenAdapter {
    private SnakeGame game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private BitmapFont font;

    public StartScreen(SnakeGame game) {
        this.game = game;
        this.batch = game.batch;
        this.camera = game.camera;
        this.font = AssetManager.font;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "Snake Game.\n \nTap screen to play", 300, 400);
        batch.end();

        if (Gdx.input.justTouched()) {
            game.setScreen(new GameScreen(game, batch, camera));
        }
    }

    @Override
    public void show() {}
    @Override
    public void resize(int width, int height) {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
    @Override
    public void dispose() {}
}
