package ru.losev.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

public class TrackingStar extends Star {

    private Vector2 trackingVelocity;
    private Vector2 sumVelocity;

    public TrackingStar(TextureAtlas atlas) {
        super(atlas);
        trackingVelocity = new Vector2();
        sumVelocity = new Vector2();
    }

    public void setVelocity(Vector2 velocity){
        trackingVelocity = velocity;
    }

    @Override
    public void update(float delta) {

        sumVelocity.setZero().mulAdd(trackingVelocity, 0.02f).rotate(180).add(velocity);

        position.mulAdd(sumVelocity, delta);
        checkAndHandleBounds();
    }
}
