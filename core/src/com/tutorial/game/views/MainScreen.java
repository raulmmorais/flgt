package com.tutorial.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.tutorial.game.B2DModel;
import com.tutorial.game.Box2DTutorial;
import com.tutorial.game.controller.KeyboardController;

public class MainScreen implements Screen {
    private Box2DTutorial parent;

    private B2DModel model;
    private OrthographicCamera cam;
    private Box2DDebugRenderer debugRenderer;
    private KeyboardController controller;

    public MainScreen (Box2DTutorial box2DTutorial){
        this.parent = box2DTutorial;
        cam = new OrthographicCamera(32, 24);
        controller = new KeyboardController();
        debugRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);

        model = new B2DModel(controller, cam);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(controller);
    }

    @Override
    public void render(float delta) {
        model.logicStep(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        debugRenderer.render(model.world, cam.combined);
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
