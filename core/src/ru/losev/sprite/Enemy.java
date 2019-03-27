package ru.losev.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.losev.math.Rect;
import ru.losev.pool.BulletPool;
import ru.losev.pool.ExplosionPool;

public class Enemy extends Ship {

    public Enemy(BulletPool bulletPool, ExplosionPool explosionPool, Sound shootSound, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.shootSound = shootSound;
        this.worldBounds = worldBounds;
        this.explosionPool = explosionPool;
        v = new Vector2(0, -1f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if(getBottom() <= worldBounds.getBottom()){
            destroy();
        }
    }

    public boolean isBulletCollision(Bullet bullet){
        return  bullet.getRight() > getLeft() && bullet.getLeft() < getRight() && bullet.getBottom() < getTop() && bullet.getTop() > position.y;
    }

    public Enemy setTexture(TextureRegion[] regions){
        this.regions = regions;
        return this;
    }

    public Enemy setSize(float height){
        setHeightProportion(height);
        return this;
    }

    public Enemy setSpeed(float speed){
        this.speed = speed;
        return this;
    }

    public Enemy setHealth(int health){
        this.health = health;
        return this;
    }

    public Enemy setBulletParams(TextureRegion bulletRegion, Vector2 bulletVelocity, float bulletSpeed, float bulletHeight, int bulletDamage, float coolDown){
        this.bulletRegion = bulletRegion;
        this.bulletVelocity = bulletVelocity;
        this.bulletSpeed = bulletSpeed;
        this.bulletHeight = bulletHeight;
        this.bulletDamage = bulletDamage;
        this.coolDown = coolDown;
        return this;
    }

}
