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

        AssetManager.load();  // Carga los assets primero

        setScreen(new StartScreen(this));  // Solo muestra la pantalla de inicio
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        AssetManager.dispose(); // Solo aquí se liberan recursos
    }

    public GameScreen getGameScreen() {
        if (gameScreen == null) {
            gameScreen = new GameScreen(this, batch, camera);
        } else {
            // Reinicia el estado si ya existe
            gameScreen.reset();
        }
        return gameScreen;
    }
}
