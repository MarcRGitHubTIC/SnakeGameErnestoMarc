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
    private ImageButton buttonUp;
    private ImageButton buttonDown;
    private ImageButton buttonLeft;
    private ImageButton buttonRight;
    private TextureRegionDrawable drawable;


    public GameScreen(SnakeGame game, Batch batch, OrthographicCamera camera) {
        this.game = game;
        this.batch = (SpriteBatch) batch;
        this.camera = camera;


        stage = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(stage);

        Texture upArrow = new Texture("up.png");
        Texture downArrow = new Texture("down.png");
        Texture rightArrow = new Texture("right.png");
        Texture leftArrow = new Texture("left.png");

        TextureRegionDrawable upDrawable = new TextureRegionDrawable(new TextureRegion(upArrow));
        ImageButton.ImageButtonStyle upStyle = new ImageButton.ImageButtonStyle();
        upStyle.imageUp = upDrawable;
        buttonUp = new ImageButton(upStyle);
        buttonUp.getImage().setScale(3f);

        TextureRegionDrawable downDrawable = new TextureRegionDrawable(new TextureRegion(downArrow));
        ImageButton.ImageButtonStyle downStyle = new ImageButton.ImageButtonStyle();
        downStyle.imageUp = downDrawable;
        buttonDown = new ImageButton(downStyle);
        buttonDown.getImage().setScale(3f);


        TextureRegionDrawable leftDrawable = new TextureRegionDrawable(new TextureRegion(leftArrow));
        ImageButton.ImageButtonStyle leftStyle = new ImageButton.ImageButtonStyle();
        leftStyle.imageUp = leftDrawable;
        buttonLeft = new ImageButton(leftStyle);
        buttonLeft.getImage().setScale(3f);


        TextureRegionDrawable rightDrawable = new TextureRegionDrawable(new TextureRegion(rightArrow));
        ImageButton.ImageButtonStyle rightStyle = new ImageButton.ImageButtonStyle();
        rightStyle.imageUp = rightDrawable;
        buttonRight = new ImageButton(rightStyle);
        buttonRight.getImage().setScale(3f);


        buttonUp.setPosition(300, 200 + 164);
        buttonDown.setPosition(300, 200 - 164);
        buttonLeft.setPosition(300 - 164, 200);
        buttonRight.setPosition(300 + 164, 200);


        buttonUp.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                snake.setDirection(Direction.UP);
            }
        });

        buttonDown.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                snake.setDirection(Direction.DOWN);
            }
        });

        buttonRight.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                snake.setDirection(Direction.RIGHT);
            }
        });

        buttonLeft.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                snake.setDirection(Direction.LEFT);
            }
        });


        stage.addActor(buttonUp);
        stage.addActor(buttonDown);
        stage.addActor(buttonRight);
        stage.addActor(buttonLeft);

        shapeRenderer = new ShapeRenderer();
        score = 0;

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.6745f, 0.7137f, 0.0275f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        snake.update(delta);


        if (snake.collidesWithApple(apple)) {
            snake.grow();
            apple.respawn(snake);
            score++;
        }
        if (snake.checkSelfCollision()) {
            game.setScreen(new GameOverScreen(game, score));
            return;
        }


        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        batch.begin();
        snake.draw( batch);
        batch.end();
        shapeRenderer.end();


        batch.begin();
        apple.draw(batch);
        if (font != null) {
            font.draw(batch, "Score: " + score, 10, 470);
        }
        batch.end();


        stage.act(delta);
        stage.draw();
    }
    @Override
    public void resize(int w, int h) {
    }

    @Override
    public void show() {
        AssetManager.load();
        snake = new Snake();
        apple = new Apple(snake);
        font = AssetManager.getFont();
        score = 0;
        Gdx.input.setInputProcessor(stage);


        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        stage.dispose();
    }

    public void reset() {
        snake = new Snake();
        apple = new Apple(snake);
        score = 0;
        stage.clear();


        stage.addActor(buttonUp);
        stage.addActor(buttonDown);
        stage.addActor(buttonLeft);
        stage.addActor(buttonRight);


        setupButtonListeners();
    }

    private void setupButtonListeners() {

        buttonUp.clearListeners();
        buttonUp.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                snake.setDirection(Direction.UP);
            }
        });

        buttonDown.clearListeners();
        buttonDown.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                snake.setDirection(Direction.DOWN);
            }
        });

        buttonLeft.clearListeners();
        buttonLeft.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                snake.setDirection(Direction.LEFT);
            }
        });

        buttonRight.clearListeners();
        buttonRight.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                snake.setDirection(Direction.RIGHT);
            }
        });


        Gdx.input.setInputProcessor(stage);
    }

}

