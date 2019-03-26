package ru.losev.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.losev.base.BaseScreen;
import ru.losev.sprite.BadLogic;

public class MenuScreen extends BaseScreen {

    private Texture badLogicTexture;
    private BadLogic badLogic;


    @Override
    protected void init() {
        super.init();

        badLogicTexture = new Texture("logo.jpg");
        badLogic = new BadLogic(new TextureRegion(badLogicTexture));
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        badLogic.update();

        batch.begin();
        badLogic.draw(batch);
        batch.end();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        badLogic.touchDown(touch, pointer);
        return true;
    }

    @Override
    public void dispose() {
        badLogicTexture.dispose();
        super.dispose();
    }
}
