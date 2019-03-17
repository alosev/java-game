package ru.losev.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.losev.base.BaseScreen;
import ru.losev.math.Rect;
import ru.losev.sprite.ButtonExit;
import ru.losev.sprite.ButtonPlay;

public class MenuScreen extends BaseScreen {

    private TextureAtlas atlas;
    private ButtonExit btnExit;
    private ButtonPlay btnPlay;

    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    protected void init() {
        super.init();

        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        btnExit = new ButtonExit(atlas);
        btnPlay = new ButtonPlay(atlas, getGame());
    }

    @Override
    protected void resizeInternal(Rect worldBounds) {
        btnExit.resize(worldBounds);
        btnPlay.resize(worldBounds);
    }

    @Override
    protected void renderInternal(float delta) {
        btnExit.draw(batch);
        btnPlay.draw(batch);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        return btnExit.touchDown(touch, pointer) | btnPlay.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        return btnExit.touchUp(touch, pointer) | btnPlay.touchUp(touch, pointer);
    }

    @Override
    public void dispose() {
        atlas.dispose();
        super.dispose();
    }
}
