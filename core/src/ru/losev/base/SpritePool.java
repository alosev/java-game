package ru.losev.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class SpritePool<T extends Sprite> {
    private final List<T> activeObjects;
    private final List<T> freeObjects;

    public SpritePool() {
        activeObjects = new ArrayList<T>();
        freeObjects = new ArrayList<T>();
    }

    protected abstract T getNewObject();

    public T obtain(){
        T object;

        if(freeObjects.isEmpty()){
            object = getNewObject();
        }
        else{
            object = freeObjects.remove(freeObjects.size() - 1);
        }

        activeObjects.add(object);

        return object;
    }

    public void updateActiveObjects(float delta){
        for(int i = 0; i < activeObjects.size(); i++){
            T object = activeObjects.get(i);
            if(!object.isDestroyed()){
                object.update(delta);
            }
        }
    }

    public void drawActiveObjects(SpriteBatch batch){
        for(int i = 0; i < activeObjects.size(); i++){
            T object = activeObjects.get(i);
            if(!object.isDestroyed()){
                object.draw(batch);
            }
        }
    }

    public void freeActiveObjects(){
        for(int i = 0; i < activeObjects.size(); i++){
            T object = activeObjects.get(i);
            if(object.isDestroyed()){
                free(object);
                i--;
            }
        }
    }

    private void free(T object){
        if(activeObjects.remove(object)){
            freeObjects.add(object);
            object.flushDestroy();
        }
    }

    public Iterator<T> getActiveObjects(){
        return activeObjects.iterator();
    }

    public void dispose(){
        activeObjects.clear();
        freeObjects.clear();
    }
}
