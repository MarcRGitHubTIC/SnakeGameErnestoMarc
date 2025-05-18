package mred.m8.uf3.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class AssetManager {
    private static Texture apple;
    private static BitmapFont font;
    private static boolean loaded = false;

    public static void load() {
        if (!loaded) {
            apple = new Texture(Gdx.files.internal("apple.png"));
            font = new BitmapFont(Gdx.files.internal("snake.fnt")); // Asegúrate que snake.fnt existe
            loaded = true;
        }
    }

    public static Texture getApple() {
        if (!loaded) load();
        // Configura el filtrado para mejor escalado
        apple.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        return apple;
    }

    public static BitmapFont getFont() {
        if (!loaded) load();
        return font;
    }

    public static void dispose() {
        if (apple != null) apple.dispose();
        if (font != null) font.dispose();
        loaded = false;
    }
}
