package ru.losev.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.losev.base.Sprite;
import ru.losev.math.Rect;
import ru.losev.math.Rnd;

public class Star extends Sprite {

    private Vector2 v;
    private Rect worldBounds;

    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star"));

        float vX = Rnd.nextFloat(-0.02f, 0.02f);
        float vY = Rnd.nextFloat(-0.5f, -0.1f);

        v = new Vector2(vX, vY);

        setHeightProportion(0.01f);
    }

    @Override
    public void update(float delta) {
        checkAndHandleBounds();
        position.mulAdd(v, delta);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;

        float posX = Rnd.nextFloat(this.worldBounds.getLeft(), this.worldBounds.getRight());
        float posY = Rnd.nextFloat(this.worldBounds.getBottom(), this.worldBounds.getTop());

        position.set(posX, posY);
    }

    private void checkAndHandleBounds(){
        if(getRight() < worldBounds.getLeft()){
            setLeft(worldBounds.getRight());
        }

        if(getLeft() > worldBounds.getRight()){
            setRight(worldBounds.getLeft());
        }

        if(getTop() < worldBounds.getBottom()){
            setBottom(worldBounds.getTop());
        }
    }

}
