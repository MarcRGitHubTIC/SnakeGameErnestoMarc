package mred.m8.uf3;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import mred.m8.uf3.helpers.AssetManager;
import mred.m8.uf3.screens.GameScreen;
import mred.m8.uf3.screens.StartScreen;


public class SnakeGame extends Game {
    public SpriteBatch batch;
    public OrthographicCamera camera;
    private GameScreen gameScreen;
    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 480);

        AssetManager.load();

        setScreen(new StartScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        AssetManager.dispose();
    }

    public GameScreen getGameScreen() {
        if (gameScreen == null) {
            gameScreen = new GameScreen(this, batch, camera);
        } else {

            gameScreen.reset();
        }
        return gameScreen;
    }
}
