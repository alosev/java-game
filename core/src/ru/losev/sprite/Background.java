package ru.losev.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.losev.base.Sprite;
import ru.losev.math.Rect;

public class Background extends Sprite {

    public Background(TextureRegion regions) {
        super(regions);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight());
        position.set(worldBounds.position);
    }

}
