package com.tutorial.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tutorial.game.views.EndScreen;
import com.tutorial.game.views.LoadingScreen;
import com.tutorial.game.views.MainScreen;
import com.tutorial.game.views.MenuScreen;
import com.tutorial.game.views.PreferencesScreen;

public class Box2DTutorial extends Game {
	SpriteBatch batch;
	Texture img;

	public final static int MENU = 0;
    public final static int PREFERENCES = 1;
    public final static int APPLICATION = 2;
    public final static int ENDGAME = 3;
    private LoadingScreen loadingScreen;
    private PreferencesScreen preferencesScreen;
    private MenuScreen menuScreen;
    private MainScreen mainScreen;
    private EndScreen endScreen;
	
	@Override
	public void create () {
	    loadingScreen = new LoadingScreen(this);
	    setScreen(loadingScreen);
		//batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	public void changeScreen (int screen){
	    switch (screen){
            case MENU:
                if (menuScreen == null)
                    menuScreen = new MenuScreen(this);
                this.setScreen(menuScreen);
                break;
            case PREFERENCES:
                if (preferencesScreen == null)
                    preferencesScreen = new PreferencesScreen(this);
                this.setScreen(preferencesScreen);
                break;
            case APPLICATION:
                if (mainScreen == null)
                    mainScreen = new MainScreen(this);
                this.setScreen(mainScreen);
                break;
            case ENDGAME:
                if (endScreen == null)
                    endScreen = new EndScreen(this);
                this.setScreen(endScreen);
                break;
        }
	}
}
