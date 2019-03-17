package ru.losev.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.losev.base.BaseScreen;
import ru.losev.base.Sprite;
import ru.losev.math.Rect;
import ru.losev.sprite.Player;

public class GameScreen extends BaseScreen {

    private TextureAtlas atlas;
    private Sprite player;

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    protected void init() {
        super.init();

        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        player = new Player(atlas);
    }

    @Override
    protected void resizeInternal(Rect worldBounds) {
        player.resize(worldBounds);
    }

    @Override
    protected void renderInternal(float delta) {
        player.draw(batch);
    }

    @Override
    protected void updateInternal(float delta) {
        player.update(delta);
    }

    @Override
    public boolean keyDown(int keycode) {
        return player.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return player.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        return player.touchDown(touch, pointer);
    }

    @Override
    public void dispose() {
        atlas.dispose();
        super.dispose();
    }
}
