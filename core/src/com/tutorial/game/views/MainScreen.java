package com.tutorial.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.tutorial.game.B2DModel;
import com.tutorial.game.Box2DTutorial;
import com.tutorial.game.controller.KeyboardController;
import com.tutorial.game.loader.B2dAssetManager;

public class MainScreen implements Screen {
    private Box2DTutorial parent;
    private B2DModel model;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera cam;
    private KeyboardController controller;
    private TextureRegion playerTex;
    private SpriteBatch sb;
    private TextureAtlas atlas;

    public MainScreen (Box2DTutorial box2DTutorial){
        this.parent = box2DTutorial;
        cam = new OrthographicCamera(32, 24);
        controller = new KeyboardController();
        model = new B2DModel(controller, cam, parent.assMan);
        debugRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);

        sb = new SpriteBatch();
        sb.setProjectionMatrix(cam.combined);

        atlas = parent.assMan.manager.get(B2dAssetManager.GAME_IMAGES, TextureAtlas.class);
        playerTex = atlas.findRegion("player");
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
        sb.begin();
        sb.draw(playerTex, model.player.getPosition().x-1, model.player.getPosition().y-1, 2, 2);
        sb.end();

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
