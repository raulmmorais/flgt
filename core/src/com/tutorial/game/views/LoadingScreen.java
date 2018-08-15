package com.tutorial.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tutorial.game.Box2DTutorial;
import com.tutorial.game.loader.B2dAssetManager;

public class LoadingScreen implements Screen {
    private Box2DTutorial parent;
    private SpriteBatch sb;
    private TextureAtlas atlas;
    private AtlasRegion title;
    private Animation flameAnimation;
    private AtlasRegion dash;

    public final int IMAGE = 0;		// loading images
    public final int FONT = 1;		// loading fonts
    public final int PARTY = 2;		// loading particle effects
    public final int SOUND = 3;		// loading sounds
    public final int MUSIC = 4;		// loading music

    private int currentLoadStage = 0;
    private float stateTime;
    private float countDown = 5f;

    public LoadingScreen (Box2DTutorial box2DTutorial){
        this.parent = box2DTutorial;
        sb = new SpriteBatch();
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
    }

    @Override
    public void show() {

        parent.assMan.queueAddLoadingImages();
        parent.assMan.manager.finishLoading();

        atlas = parent.assMan.manager.get(B2dAssetManager.LOAD_IMAGES, TextureAtlas.class);
        title = atlas.findRegion("staying-alight-logo");
        dash = atlas.findRegion("loading-dash");

        flameAnimation = new Animation(0.07f, atlas.findRegions("flames/flames"), PlayMode.LOOP);

        parent.assMan.queueAddImages();
        Gdx.app.log("Loading screen", "Now Loading...");

        stateTime = 0f;
    }

    private void drawLoadingBar(int stage, TextureRegion currentFrame){
        for (int i = 0; i < stage; i++){
            sb.draw(currentFrame, 50 + i*50, 150, 50, 50);
            sb.draw(dash, 35 + i*50, 140, 80, 80);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 0f, 0f, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stateTime += delta;

        TextureRegion currentFrame = (TextureRegion) flameAnimation.getKeyFrame(stateTime, true);

        sb.begin();
        drawLoadingBar(currentLoadStage * 2, currentFrame);
        sb.draw(title, 135, 250);
        sb.end();

        if (parent.assMan.manager.update()){
            currentLoadStage += 1;
            switch (currentLoadStage){
                case FONT:
                    Gdx.app.log("Loading screen", "Loading Fonts...");
                    parent.assMan.queueAddFonts();
                    break;
                case PARTY:
                    Gdx.app.log("Loading screen", "Loading Particle effects...");
                    parent.assMan.queueAddParticleEffects();
                    break;
                case SOUND:
                    Gdx.app.log("Loading screen", "Loading Sounds...");
                    parent.assMan.queueAddSounds();
                    break;
                case MUSIC:
                    Gdx.app.log("Loading screen", "Loading musics...");
                    parent.assMan.queueAddMusic();
                    break;
                case 5:
                    Gdx.app.log("Loading screen", "Finished");
                    break;
            }
            if (currentLoadStage > 5){
                countDown -= delta;
                currentLoadStage = 5;
                if (countDown < 0){
                    parent.changeScreen(Box2DTutorial.MENU);
                }
            }
        }
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
