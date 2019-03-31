package ru.losev.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.Iterator;

import ru.losev.base.BaseScreen;
import ru.losev.base.Font;
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
import ru.losev.sprite.Star;
import ru.losev.sprite.TrackingStar;
import ru.losev.utils.EnemyFactory;

public class GameScreen extends BaseScreen {

    private enum State { PLAYING, GAMEOVER, PAUSE}

    private static final float FONT_SIZE = 0.02f;

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

    private Font font;
    private Font fontHp;

    private int score;
    private int level;
    private StringBuilder sbScore;
    private StringBuilder sbHp;
    private StringBuilder sbLevel;
    private StringBuilder sbHpLine;

    private State state;
    private State oldState;

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

        font = new Font("font/font.fnt", "font/font.png");
        font.setSize(FONT_SIZE);

        fontHp = new Font("font/font.fnt", "font/font.png");
        fontHp.setSize(FONT_SIZE);
        fontHp.setColor(255f, 0f, 0f, 1f);

        score = 0;
        level = 1;
        sbScore = new StringBuilder();
        sbHp = new StringBuilder();
        sbLevel = new StringBuilder();
        sbHpLine = new StringBuilder();

        state = State.PLAYING;

        for (TrackingStar star: stars) {
            star.setVelocity(player.getV());
        }
    }

    @Override
    protected void resizeInternal(Rect worldBounds) {
        player.resize(worldBounds);
        gameOver.resize(worldBounds);
        btnNewGame.resize(worldBounds);
    }

    @Override
    protected void renderInternal(float delta) {
        if(state == State.PLAYING){
            player.draw(batch);
            bulletPool.drawActiveObjects(batch);
            enemyPool.drawActiveObjects(batch);
        }

        if(state == State.GAMEOVER){
            gameOver.draw(batch);
            btnNewGame.draw(batch);
        }

        printInfo();

        explosionPool.drawActiveObjects(batch);
    }

    private void printInfo(){
        sbScore.setLength(0);
        sbHp.setLength(0);
        sbLevel.setLength(0);
        sbHpLine.setLength(0);

        font.draw(batch, sbScore.append("Score: ").append(score), worldBounds.getLeft(), worldBounds.getTop() - 0.01f);
        font.draw(batch, sbHp.append("HP: ").append(player.getHealth()), worldBounds.position.x, worldBounds.getTop() - 0.01f, Align.center);
        font.draw(batch, sbLevel.append("LEVEL: ").append(enemyFactory.getLevel()), worldBounds.getRight(), worldBounds.getTop() - 0.01f, Align.right);
        for(int i = 0; i < Math.ceil(player.getHealth() / 10f); i++){
            sbHpLine.append("|");
        }
        fontHp.draw(batch, sbHpLine, worldBounds.position.x, worldBounds.getTop() - 0.05f, Align.center);
    }

    @Override
    protected void updateInternal(float delta) {
        explosionPool.updateActiveObjects(delta);

        if(state == State.PLAYING) {
            player.update(delta);
            bulletPool.updateActiveObjects(delta);
            enemyPool.updateActiveObjects(delta);
            enemyFactory.generate(delta, score);
        }

        bulletPool.freeActiveObjects();
        enemyPool.freeActiveObjects();
        explosionPool.freeActiveObjects();

        if(state == State.PLAYING) {
            checkCollisions();
        }
    }

    private void checkCollisions() {
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
                    if(enemy.isDestroyed()){
                        score++;
                    }
                }
            }

            float minDist = enemy.getHalfWidth() + player.getHalfWidth();
            if(enemy.position.dst(player.position) < minDist){
                explosionPool.obtain().setSize(enemy.getHeight()).setPosition(enemy.position);
                enemy.destroy();
                player.damage(player.getHealth());
                state = State.GAMEOVER;
                return;
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
                if(player.isDestroyed()){
                    state = State.GAMEOVER;
                    return;
                }
            }
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if(state == State.PLAYING){
            return player.keyDown(keycode);
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(state == State.PLAYING){
            return player.keyUp(keycode);
        }

        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if(state == State.PLAYING){
            return player.touchDown(touch, pointer);
        }

        if(state == State.GAMEOVER){
            return btnNewGame.touchDown(touch, pointer);
        }

        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if(state == State.PLAYING){
            return player.touchUp(touch, pointer);
        }

        if(state == State.GAMEOVER){
            return btnNewGame.touchUp(touch, pointer);
        }

        return false;
    }

    @Override
    public void pause() {
        super.pause();
        oldState = state;
        state = State.PAUSE;
    }

    @Override
    public void resume() {
        super.resume();
        state = oldState;
        oldState = null;
    }

    @Override
    public void dispose() {
        atlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        laserSound.dispose();
        explosionSound.dispose();
        font.dispose();
        super.dispose();
    }
}
