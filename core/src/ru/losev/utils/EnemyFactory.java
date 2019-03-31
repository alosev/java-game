package ru.losev.utils;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.losev.math.Rect;
import ru.losev.math.Rnd;
import ru.losev.pool.EnemyPool;
import ru.losev.sprite.Enemy;

public class EnemyFactory {

    private static final float SMALL_HEIGHT = 0.1f;
    private static final int SMALL_HEALTH = 10;
    private static final float SMALL_SPEED = 0.2f;
    private static final float SMALL_BULLET_SPEED = 0.6f;
    private static final float SMALL_BULLET_HEIGHT = 0.01f;
    private static final int SMALL_BULLET_DAMAGE = 2;
    private static final float SMALL_BULLET_COOLDOWN = 2f;
    private TextureRegion[] smallRegions;

    private static final float MEDIUM_HEIGHT = 0.1f;
    private static final int MEDIUM_HEALTH = 20;
    private static final float MEDIUM_SPEED = 0.15f;
    private static final float MEDIUM_BULLET_SPEED = 0.4f;
    private static final float MEDIUM_BULLET_HEIGHT = 0.02f;
    private static final int MEDIUM_BULLET_DAMAGE = 5;
    private static final float MEDIUM_BULLET_COOLDOWN = 2f;
    private TextureRegion[] mediumRegions;

    private static final float BIG_HEIGHT = 0.2f;
    private static final int BIG_HEALTH = 30;
    private static final float BIG_SPEED = 0.1f;
    private static final float BIG_BULLET_SPEED = 0.4f;
    private static final float BIG_BULLET_HEIGHT = 0.04f;
    private static final int BIG_BULLET_DAMAGE = 10;
    private static final float BIG_BULLET_COOLDOWN = 4f;
    private TextureRegion[] bigRegions;

    private Vector2 bulletVelocity;

    private Rect worldBounds;
    private EnemyPool enemyPool;

    private float factoryCoolDown;
    private float timer;

    private int level;

    private TextureRegion bulletRegion;

    public EnemyFactory(TextureAtlas atlas, Rect worldBounds, EnemyPool enemyPool) {
        this.worldBounds = worldBounds;
        this.enemyPool = enemyPool;

        bulletRegion = atlas.findRegion("bulletEnemy");

        smallRegions = Regions.split(atlas.findRegion("enemy0"), 2, 1, 2);
        mediumRegions = Regions.split(atlas.findRegion("enemy1"), 2, 1, 2);
        bigRegions = Regions.split(atlas.findRegion("enemy2"), 2, 1, 2);

        bulletVelocity = new Vector2(0, -1f);

        factoryCoolDown = 2f;
    }

    public void generate(float delta, int score){
        level = score / 10 + 1;
        timer += delta;

        if(timer >= factoryCoolDown){
            timer = 0f;
            float type = (float)Math.random();
            if(type < 0.5f){
                generateSmallEnemyShip();
            }
            else if(type < 0.9){
                generateMediumEnemyShip();
            }
            else{
                generateBigEnemyShip();
            }
        }
    }


    private void generateSmallEnemyShip(){
        generateEmenyShip(smallRegions, SMALL_HEIGHT, SMALL_SPEED, SMALL_HEALTH, SMALL_BULLET_SPEED, SMALL_BULLET_HEIGHT, SMALL_BULLET_DAMAGE, SMALL_BULLET_COOLDOWN);
    }

    private void generateMediumEnemyShip(){
        generateEmenyShip(mediumRegions, MEDIUM_HEIGHT, MEDIUM_SPEED, MEDIUM_HEALTH, MEDIUM_BULLET_SPEED, MEDIUM_BULLET_HEIGHT, MEDIUM_BULLET_DAMAGE, MEDIUM_BULLET_COOLDOWN);
    }

    private void generateBigEnemyShip(){
        generateEmenyShip(bigRegions, BIG_HEIGHT, BIG_SPEED, BIG_HEALTH, BIG_BULLET_SPEED, BIG_BULLET_HEIGHT, BIG_BULLET_DAMAGE, BIG_BULLET_COOLDOWN);
    }

    private void generateEmenyShip(TextureRegion[] texture, float height, float speed, int health, float bulletSpeed, float bulletHeight, int bulletDamage, float bulletCoolDown){
        Enemy enemy =
                enemyPool.obtain()
                        .setTexture(texture)
                        .setSize(height)
                        .setSpeed(speed)
                        .setHealth(health)
                        .setBulletParams(bulletRegion, bulletVelocity, bulletSpeed, bulletHeight, bulletDamage * level, bulletCoolDown);

        enemy.position.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(), worldBounds.getRight() - enemy.getHalfWidth());
        enemy.setTop(worldBounds.getTop());
    }

    public int getLevel() {
        return level;
    }
}
