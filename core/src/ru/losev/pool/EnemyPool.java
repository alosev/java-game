package ru.losev.pool;

import com.badlogic.gdx.audio.Sound;

import ru.losev.base.SpritePool;
import ru.losev.math.Rect;
import ru.losev.sprite.Enemy;

public class EnemyPool extends SpritePool<Enemy> {

    private BulletPool bulletPool;
    private Sound shootSound;
    private Rect worldBounds;
    private ExplosionPool explosionPool;

    public EnemyPool(BulletPool bulletPool, ExplosionPool explosionPool, Sound shootSound, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.shootSound = shootSound;
        this.worldBounds = worldBounds;
        this.explosionPool = explosionPool;
    }

    @Override
    protected Enemy getNewObject() {
        return new Enemy(bulletPool, explosionPool, shootSound, worldBounds);
    }
}
