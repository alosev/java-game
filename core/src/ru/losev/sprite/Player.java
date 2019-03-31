package ru.losev.sprite;


import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.losev.StarGame;
import ru.losev.math.Rect;
import ru.losev.pool.BulletPool;
import ru.losev.pool.ExplosionPool;

public class Player extends Ship {

    private static final int INVALID_POINTER = -1;

    private int leftPointer;
    private int rightPointer;


    public Player(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool, Sound shootSound) {
        super(atlas.findRegion("main_ship"), 2, 1, 2);

        setHeightProportion(0.1f);

        speed = 0.2f;
        movement = new Vector2(1f,0f);
        leftPointer = rightPointer = INVALID_POINTER;

        bulletRegion = atlas.findRegion("bulletMainShip");
        bulletVelocity = new Vector2(0, 1f);
        bulletSpeed = 0.6f;
        bulletHeight = 0.01f;
        bulletDamage = 5;
        coolDown = 0.15f;

        this.shootSound = shootSound;
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;

        health = 100;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(this.worldBounds.getBottom() + 0.05f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        checkAndHandleBounds();
    }

    private void checkAndHandleBounds(){

        if(getLeft() < worldBounds.getLeft()){
            setLeft(worldBounds.getLeft());
        }

        if(getRight() > worldBounds.getRight()){
            setRight(worldBounds.getRight());
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if(StarGame.isAndroid) {
            if(touch.x > worldBounds.position.x){
                if(rightPointer == INVALID_POINTER){
                    rightPointer = pointer;
                    moveRight();
                    return true;
                }
            }
            else{
                if(leftPointer == INVALID_POINTER){
                    leftPointer = pointer;
                    moveLeft();
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if(StarGame.isAndroid){
            if(pointer == leftPointer){
                leftPointer = INVALID_POINTER;

                if(rightPointer != INVALID_POINTER){
                    moveRight();
                }
                else{
                    stop();
                }

                return true;
            }

            if(pointer == rightPointer){
                rightPointer = INVALID_POINTER;

                if(leftPointer != INVALID_POINTER){
                    moveLeft();
                }
                else{
                    stop();
                }

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(!StarGame.isAndroid) {
            switch (keycode) {
                case Input.Keys.LEFT:
                case Input.Keys.A: {
                    leftPointer = 1;
                    moveLeft();
                    break;
                }
                case Input.Keys.RIGHT:
                case Input.Keys.D: {
                    rightPointer = 1;
                    moveRight();
                    break;
                }
                default:
                    return false;
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(!StarGame.isAndroid) {
            switch (keycode) {
                case Input.Keys.LEFT:
                case Input.Keys.A: {
                    leftPointer = INVALID_POINTER;
                    if (rightPointer != INVALID_POINTER) {
                        moveRight();
                    } else {
                        stop();
                    }
                    break;
                }
                case Input.Keys.RIGHT:
                case Input.Keys.D: {
                    rightPointer = INVALID_POINTER;
                    if (leftPointer != INVALID_POINTER) {
                        moveLeft();
                    } else {
                        stop();
                    }
                    break;
                }
                default:
                    return false;
            }

            return true;
        }

        return false;
    }

    private void moveRight(){
        v.set(movement);
    }

    private void moveLeft(){
        v.set(movement).rotate(180);
    }

    private void stop(){
        v.setZero();
    }

    public boolean isBulletCollision(Bullet bullet){
        return  bullet.getRight() > getLeft() && bullet.getLeft() < getRight() && bullet.getTop() > getBottom() && bullet.getBottom() < position.y;
    }
}
