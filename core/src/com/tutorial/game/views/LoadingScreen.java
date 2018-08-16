package com.tutorial.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tutorial.game.Box2DTutorial;
import com.tutorial.game.LoadingBarPart;
import com.tutorial.game.loader.B2dAssetManager;

public class LoadingScreen implements Screen {
    private Box2DTutorial parent;
    private Stage stage;

    private TextureAtlas atlas;
    private AtlasRegion title;
    private Animation flameAnimation;
    private AtlasRegion dash;
    private AtlasRegion background;
    private AtlasRegion copyright;

    public final int IMAGE = 0;		// loading images
    public final int FONT = 1;		// loading fonts
    public final int PARTY = 2;		// loading particle effects
    public final int SOUND = 3;		// loading sounds
    public final int MUSIC = 4;		// loading music

    private int currentLoadStage = 0;
    private float countDown = 5f;
    private Image tileImage;
    private Image copyrightImage;
    private Table table;
    private Table loadingTable;

    public LoadingScreen (Box2DTutorial box2DTutorial){
        this.parent = box2DTutorial;
        stage = new Stage(new FitViewport(600, 336));
        loadAssets();
        parent.assMan.queueAddImages();
        Gdx.app.log("Loading screen", "Now Loading...");
    }

    @Override
    public void show() {
        tileImage = new Image(title);
        copyrightImage = new Image(copyright);

        table = new Table();
        table.setFillParent(true);
        table.setDebug(false);
        table.setBackground(new TiledDrawable(background));

        loadingTable = new Table();
        for (int i = 0; i < 10; i++){
            loadingTable.add(new LoadingBarPart(dash, flameAnimation));
        }

        table.add(tileImage).align(Align.center).pad(10,0,0,0).colspan(10);
        table.row();
        table.add(loadingTable).width(400);
        table.row();
        table.add(copyrightImage).align(Align.center).pad(200,0,0,0).colspan(10);

        stage.addActor(table);

    }

    private void loadAssets() {
        parent.assMan.queueAddLoadingImages();
        parent.assMan.manager.finishLoading();

        atlas = parent.assMan.manager.get(B2dAssetManager.LOAD_IMAGES, TextureAtlas.class);
        title = atlas.findRegion("staying-alight-logo");
        dash = atlas.findRegion("loading-dash");
        background = atlas.findRegion("flamebackground");
        copyright = atlas.findRegion("copyright");


        flameAnimation = new Animation(0.07f, atlas.findRegions("flames/flames"), PlayMode.LOOP);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 0f, 0f, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (parent.assMan.manager.update()){
            currentLoadStage += 1;
            if (currentLoadStage <= 5){
                loadingTable.getCells().get((currentLoadStage -1)*2).getActor().setVisible(true);
                loadingTable.getCells().get((currentLoadStage -1)*2+1).getActor().setVisible(true);
            }
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
        stage.act();
        stage.draw();
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
        stage.dispose();
    }
}
