package ru.losev.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.losev.base.Sprite;
import ru.losev.math.Rect;
import ru.losev.pool.BulletPool;
import ru.losev.pool.ExplosionPool;
import ru.losev.utils.Regions;

public class Ship extends Sprite {

    protected Rect worldBounds;

    protected float speed;
    protected Vector2 v;
    protected Vector2 movement;

    protected TextureRegion bulletRegion;
    protected Vector2 bulletVelocity;
    protected float bulletSpeed;
    protected float bulletHeight;
    protected int bulletDamage;
    protected BulletPool bulletPool;
    protected ExplosionPool explosionPool;
    protected Sound shootSound;

    protected float coolDown;
    protected float timer;

    private float damageAnimateDuration = 0.1f;
    private float damageAnimationTimer;

    protected int health;

    public Ship() {
    }

    public Ship(TextureRegion region, int cols, int rows, int frames) {
        super(region, cols, rows, frames);
        v = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        timer += delta;
        if(timer >= coolDown){
            timer = 0f;
            shoot();
        }

        position.mulAdd(v, speed * delta);

        damageAnimationTimer += delta;
        if(damageAnimationTimer >= damageAnimateDuration){
            frame = 0;
        }
    }

    public void damage(int damage){
        frame = 1;
        damageAnimationTimer = 0f;
        health -= damage;
        if(health <= 0){
            health = 0;
            destroy();
        }
    }

    protected void shoot(){
        Bullet bullet =
                    bulletPool.obtain()
                        .setOwner(this)
                        .setTexture(bulletRegion)
                        .setPosition(position)
                        .setSpeed(bulletSpeed)
                        .setVelocity(bulletVelocity)
                        .setSize(bulletHeight)
                        .setWorldBounds(worldBounds)
                        .setDamage(bulletDamage);

        shootSound.play();
    }

    public int getHealth() {
        return health;
    }

    @Override
    public void destroy() {
        super.destroy();
        boom();
    }

    public void boom(){
        explosionPool.obtain().setSize(getHeight()).setPosition(position);
    }

    public Vector2 getV() {
        return v;
    }
}
