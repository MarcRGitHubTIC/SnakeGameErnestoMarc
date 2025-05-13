package mred.m8.uf3.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import mred.m8.uf3.SnakeGame;
import mred.m8.uf3.actors.Apple;
import mred.m8.uf3.actors.Direction;
import mred.m8.uf3.actors.Snake;
import mred.m8.uf3.helpers.AssetManager;

public class GameScreen implements Screen {
    private final SnakeGame game;
    private final OrthographicCamera camera;
    private Snake snake;
    private Apple apple;
    private BitmapFont font;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private int score;
    private Stage stage;
    private Texture upArrow;
    private Texture downArrow;
    private Texture rightArrow;
    private ImageButton buttonUp;
    private ImageButton buttonDown;
    private ImageButton buttonLeft;
    private ImageButton buttonRight;
    private TextureRegionDrawable drawable;


    public GameScreen(SnakeGame game, Batch batch, OrthographicCamera camera) {
        this.game = game;
        this.batch = (SpriteBatch) batch;
        this.camera = camera;

        stage = new Stage(new ScreenViewport());//capturar eventos táctiles

        Gdx.input.setInputProcessor(stage);
        //Cargar las texturas de las flechas direccionales
        Texture upArrow= new Texture("up.png");
        Texture downArrow= new Texture("down.png");
        Texture rightArrow= new Texture("right.png");
        Texture leftArrow= new Texture("left.png");

        //crear los botones
        drawable = new TextureRegionDrawable(new TextureRegion(upArrow));
        buttonUp= new ImageButton(drawable);
        drawable = new TextureRegionDrawable(new TextureRegion(downArrow));
        buttonDown= new ImageButton(drawable);
        drawable = new TextureRegionDrawable(new TextureRegion(leftArrow));
        buttonLeft= new ImageButton(drawable);
        drawable = new TextureRegionDrawable(new TextureRegion(rightArrow));
        buttonRight= new ImageButton(drawable);

        //posicionar botones
        buttonUp.setPosition(100, 100 + 64);
        buttonDown.setPosition(100, 100 - 64);
        buttonLeft.setPosition(100 - 64, 100);
        buttonRight.setPosition(100 + 64, 100);

        //agregamos los listeners
        buttonUp.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                snake.setDirection(Direction.UP);
            }
        });
        //agregamos los listeners
        buttonDown.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                snake.setDirection(Direction.DOWN);
            }
        });
        //agregamos los listeners
        buttonRight.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                snake.setDirection(Direction.RIGHT);
            }
        });
        //agregamos los listeners
        buttonLeft.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                snake.setDirection(Direction.LEFT);
            }
        });

        //añadimos botones al stage
        stage.addActor(buttonUp);
        stage.addActor(buttonDown);
        stage.addActor(buttonRight);
        stage.addActor(buttonLeft);

        shapeRenderer = new ShapeRenderer();
        score = 0;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // fondo negro
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        snake.update(delta);

        stage.act(delta);
        stage.draw();

        if (snake.collidesWithApple(apple)) {
            snake.grow();
            apple.respawn();
            score++;
        }

        if (snake.checkSelfCollision()) {
            game.setScreen(new StartScreen(game));
        }

        // Dibujo ShapeRenderer
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        snake.draw(shapeRenderer);
        shapeRenderer.end();

        // Dibujo apperu
        batch.begin();
        apple.draw(batch);
        font.draw(batch, "Score: " + score, 10, 470);
        batch.end();
    }


    @Override public void resize(int w, int h) {}
    @Override
    public void show() {
        AssetManager.load();
        snake = new Snake();
        apple = new Apple();
        font = AssetManager.font;
    }
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override
    public void dispose() {
        AssetManager.dispose();
        shapeRenderer.dispose();
    }
}

