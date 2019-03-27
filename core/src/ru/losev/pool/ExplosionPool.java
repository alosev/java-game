package ru.losev.pool;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.losev.base.SpritePool;
import ru.losev.sprite.Explosion;

public class ExplosionPool extends SpritePool<Explosion> {

    private TextureAtlas atlas;
    private Sound explosionSound;

    public ExplosionPool(TextureAtlas atlas, Sound explosionSound) {
        this.atlas = atlas;
        this.explosionSound = explosionSound;
    }

    @Override
    protected Explosion getNewObject() {
        return new Explosion(atlas, explosionSound);
    }
}
