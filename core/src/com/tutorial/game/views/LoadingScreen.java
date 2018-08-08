package com.tutorial.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.tutorial.game.Box2DTutorial;

public class LoadingScreen implements Screen {
    private Box2DTutorial parent;

    public LoadingScreen (Box2DTutorial box2DTutorial){
        this.parent = box2DTutorial;
        Gdx.app.log("create", "New Load screen");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.app.log("create", "call Menu");
        parent.changeScreen(Box2DTutorial.MENU);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
