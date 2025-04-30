package mred.m8.uf3.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class AssetManager {

    public static Texture apple;
    public static BitmapFont font;

    public static void load() {
        apple = new Texture(Gdx.files.internal("apple.png"));
        font = new BitmapFont(Gdx.files.internal("default.fnt"));
    }

    public static void dispose() {
        apple.dispose();
        font.dispose();
    }
}
