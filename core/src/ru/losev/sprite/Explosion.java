package ru.losev.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.losev.base.Sprite;

public class Explosion extends Sprite {

    private float animateDuration;
    private float timer;
    private Sound explosionSound;

    public Explosion(TextureAtlas atlas, Sound explosionSound) {
        super(atlas.findRegion("explosion"), 9, 9, 74);

        this.explosionSound = explosionSound;
        animateDuration = 0.017f;
    }

    @Override
    public void update(float delta) {
        timer += delta;
        if(timer >= animateDuration){
            timer = 0;
            if(++frame == regions.length){
                destroy();
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        frame = 0;
    }

    public Explosion setSize(float height){
        setHeightProportion(height);
        return this;
    }

    public Explosion setPosition(Vector2 position){
        this.position.set(position);
        explosionSound.play();
        return this;
    }
}
