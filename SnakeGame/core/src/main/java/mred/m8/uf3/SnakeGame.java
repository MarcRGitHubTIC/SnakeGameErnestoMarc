package mred.m8.uf3;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import mred.m8.uf3.helpers.AssetManager;
import mred.m8.uf3.screens.StartScreen;


public class SnakeGame extends Game {
    public SpriteBatch batch;
    public OrthographicCamera camera;

    @Override
    public void create() {
        AssetManager.load();
        setScreen(new StartScreen(this));


        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1024, 768);


        this.setScreen(new StartScreen(this));
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
