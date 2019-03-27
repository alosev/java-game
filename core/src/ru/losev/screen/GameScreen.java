package ru.losev.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import java.util.Iterator;

import ru.losev.base.BaseScreen;
import ru.losev.base.Sprite;
import ru.losev.math.Rect;
import ru.losev.pool.BulletPool;
import ru.losev.pool.EnemyPool;
import ru.losev.pool.ExplosionPool;
import ru.losev.sprite.Bullet;
import ru.losev.sprite.ButtonNewGame;
import ru.losev.sprite.Enemy;
import ru.losev.sprite.GameOver;
import ru.losev.sprite.Player;
import ru.losev.utils.EnemyFactory;

public class GameScreen extends BaseScreen {

    private TextureAtlas atlas;
    private Player player;
    private Sound laserSound;
    private Sound explosionSound;
    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private EnemyFactory enemyFactory;
    private ExplosionPool explosionPool;

    private GameOver gameOver;
    private ButtonNewGame btnNewGame;

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    protected void init() {
        super.init();

        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        laserSound = Gdx.audio.newSound(Gdx.files.internal("audio/laser.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("audio/explosion.wav"));

        explosionPool = new ExplosionPool(atlas, explosionSound);

        bulletPool = new BulletPool();
        enemyPool = new EnemyPool(bulletPool, explosionPool, laserSound, worldBounds);

        player = new Player(atlas, bulletPool, explosionPool, laserSound);

        enemyFactory = new EnemyFactory(atlas, worldBounds, enemyPool);

        gameOver = new GameOver(atlas.findRegion("message_game_over"));
        btnNewGame = new ButtonNewGame(atlas, getGame());
    }

    @Override
    protected void resizeInternal(Rect worldBounds) {
        if(!player.isDestroyed()){
            player.resize(worldBounds);
        }
        gameOver.resize(worldBounds);
        btnNewGame.resize(worldBounds);
    }

    @Override
    protected void renderInternal(float delta) {
        if(!player.isDestroyed()){
            player.draw(batch);
            bulletPool.drawActiveObjects(batch);
            enemyPool.drawActiveObjects(batch);
        }
        else{
            gameOver.draw(batch);
            btnNewGame.draw(batch);
        }

        explosionPool.drawActiveObjects(batch);
    }

    @Override
    protected void updateInternal(float delta) {
        explosionPool.updateActiveObjects(delta);

        if(!player.isDestroyed()) {
            player.update(delta);
            bulletPool.updateActiveObjects(delta);
            enemyPool.updateActiveObjects(delta);
            enemyFactory.generate(delta);
        }

        bulletPool.freeActiveObjects();
        enemyPool.freeActiveObjects();
        explosionPool.freeActiveObjects();

        checkCollisions();
    }

    private void checkCollisions() {
        if(!player.isDestroyed()){
            Iterator<Enemy> enemies = enemyPool.getActiveObjects();
            while (enemies.hasNext()){
                Enemy enemy = enemies.next();
                if(enemy.isDestroyed()){
                    continue;
                }

                Iterator<Bullet> bullets = bulletPool.getActiveObjects();
                while (bullets.hasNext()){
                    Bullet bullet = bullets.next();
                    if(bullet.isDestroyed() || bullet.getOwner() != player){
                        continue;
                    }

                    if(enemy.isBulletCollision(bullet)){
                        enemy.damage(bullet.getDemage());
                        bullet.destroy();
                    }
                }

                float minDist = enemy.getHalfWidth() + player.getHalfWidth();
                if(enemy.position.dst(player.position) < minDist){
                    explosionPool.obtain().setSize(enemy.getHeight()).setPosition(enemy.position);
                    enemy.destroy();
                    player.damage(player.getHealth());
                }
            }

            Iterator<Bullet> bullets = bulletPool.getActiveObjects();
            while (bullets.hasNext()){
                Bullet bullet = bullets.next();
                if(bullet.isDestroyed() || bullet.getOwner() == player){
                    continue;
                }

                if(player.isBulletCollision(bullet)){
                    player.damage(bullet.getDemage());
                    bullet.destroy();
                }
            }
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if(!player.isDestroyed()){
            return player.keyDown(keycode);
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(!player.isDestroyed()){
            return player.keyUp(keycode);
        }

        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if(!player.isDestroyed()){
            return player.touchDown(touch, pointer);
        }

        return btnNewGame.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if(!player.isDestroyed()){
            return player.touchUp(touch, pointer);
        }

        return btnNewGame.touchUp(touch, pointer);
    }

    @Override
    public void dispose() {
        atlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        laserSound.dispose();
        explosionSound.dispose();
        super.dispose();
    }
}
