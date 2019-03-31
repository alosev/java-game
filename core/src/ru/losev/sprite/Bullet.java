package ru.losev.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.losev.base.Sprite;
import ru.losev.math.Rect;

public class Bullet extends Sprite {

    private Rect worldBounds;
    private Vector2 velocity;
    private float speed;
    private int demage;
    private Object owner;

    public Bullet() {
        regions = new TextureRegion[1];
        velocity = new Vector2();
    }

    @Override
    public void update(float delta) {
        position.mulAdd(velocity, speed * delta);

        if(isOutside(worldBounds)){
            destroy();
        }
    }

    public int getDemage() {
        return demage;
    }

    public Object getOwner() {
        return owner;
    }


    //инициализация

    public Bullet setOwner(Object owner){
        this.owner = owner;
        return this;
    }

    public Bullet setDamage(int damage){
        this.demage = damage;
        return this;
    }

    public Bullet setSpeed(float speed){
        this.speed = speed;
        return this;
    }

    public Bullet setVelocity(Vector2 velocity){
        this.velocity = velocity;
        return this;
    }

    public Bullet setTexture(TextureRegion region){
        this.regions[0] = region;
        return this;
    }

    public Bullet setPosition(Vector2 position){
        this.position.set(position);
        return this;
    }

    public Bullet setSize(float height){
        setHeightProportion(height);
        return  this;
    }

    public Bullet setWorldBounds(Rect worldBounds){
        this.worldBounds = worldBounds;
        return this;
    }

}
