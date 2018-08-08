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
    private AppPreferences preferences;
	
	@Override
	public void create () {
        Gdx.app.log("create", "call LoadScreen");
	    loadingScreen = new LoadingScreen(this);
	    setScreen(loadingScreen);
	    preferences = new AppPreferences();
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
                    menuScreen = new MenuScreen(this);
                Gdx.app.log("changeScreen", "call MenuScreen");
                this.setScreen(menuScreen);
                break;
            case PREFERENCES:
                if (preferencesScreen == null)
                    preferencesScreen = new PreferencesScreen(this);
                Gdx.app.log("changeScreen", "call PreferencesScreen");
                this.setScreen(preferencesScreen);
                break;
            case APPLICATION:
                if (mainScreen == null)
                    mainScreen = new MainScreen(this);
                Gdx.app.log("changeScreen", "call MainScreen");
                this.setScreen(mainScreen);
                break;
            case ENDGAME:
                if (endScreen == null)
                    endScreen = new EndScreen(this);
                Gdx.app.log("changeScreen", "call EndScreen");
                this.setScreen(endScreen);
                break;
        }
	}

    public AppPreferences getPreferences() {
        return preferences;
    }
}
