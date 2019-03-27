package ru.losev.pool;

import ru.losev.base.SpritePool;
import ru.losev.sprite.Bullet;

public class BulletPool extends SpritePool<Bullet> {

    @Override
    protected Bullet getNewObject() {
        return new Bullet();
    }
}
