package ru.losev.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.losev.base.Sprite;
import ru.losev.math.Rect;

public class GameOver extends Sprite {

    public GameOver(TextureRegion regions) {
        super(regions);
        setHeightProportion(0.1f);
    }

    @Override
    public void resize(Rect worldBounds) {
        setTop(worldBounds.getTop() - 0.2f);
    }

}
