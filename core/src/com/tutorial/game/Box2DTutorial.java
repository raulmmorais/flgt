package com.tutorial.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.tutorial.game.views.EndScreen;
import com.tutorial.game.views.LoadingScreen;
import com.tutorial.game.views.MainScreen;
import com.tutorial.game.views.MenuScreen;
import com.tutorial.game.views.PreferencesScreen;

public class Box2DTutorial extends Game {

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
        Gdx.app.log("create", "call LoadScreen");
	    loadingScreen = new LoadingScreen(this);
	    setScreen(loadingScreen);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		//batch.dispose();
		//img.dispose();
	}

	public void changeScreen (int screen){
	    switch (screen){
            case MENU:
                if (menuScreen == null)
                    Gdx.app.log("create", "call MenuScreen");
                    menuScreen = new MenuScreen(this);
                this.setScreen(menuScreen);
                break;
            case PREFERENCES:
                if (preferencesScreen == null)
                    Gdx.app.log("create", "call PreferencesScreen");
                    preferencesScreen = new PreferencesScreen(this);
                this.setScreen(preferencesScreen);
                break;
            case APPLICATION:
                if (mainScreen == null)
                    Gdx.app.log("create", "call MainScreen");
                    mainScreen = new MainScreen(this);
                this.setScreen(mainScreen);
                break;
            case ENDGAME:
                if (endScreen == null)
                    Gdx.app.log("create", "call EndScreen");
                    endScreen = new EndScreen(this);
                this.setScreen(endScreen);
                break;
        }
	}
}
