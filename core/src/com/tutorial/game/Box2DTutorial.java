package com.tutorial.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.tutorial.game.loader.B2dAssetManager;
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
    private Music playingSong;
    public B2dAssetManager assMan = new B2dAssetManager();
	
	@Override
	public void create () {
	    loadingScreen = new LoadingScreen(this);
        preferences = new AppPreferences();
	    setScreen(loadingScreen);

	    assMan.queueAddMusic();
	    assMan.manager.finishLoading();

        playingSong = assMan.manager.get(B2dAssetManager.TRACK_07_MUSIC, Music.class);

        playingSong.setVolume(0.5f);
        playingSong.play();
    }

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
	    playingSong.dispose();
	    assMan.manager.dispose();
	}

	public void changeScreen (int screen){
	    switch (screen){
            case MENU:
                if(menuScreen == null) menuScreen = new MenuScreen(this);
                this.setScreen(menuScreen);
                break;
            case PREFERENCES:
                if(preferencesScreen == null) preferencesScreen = new PreferencesScreen(this);
                this.setScreen(preferencesScreen);
                break;
            case APPLICATION:
                if(mainScreen == null) mainScreen = new MainScreen(this);
                this.setScreen(mainScreen);
                break;
            case ENDGAME:
                if(endScreen == null) endScreen = new EndScreen(this);
                this.setScreen(endScreen);
                break;
        }
	}

    public AppPreferences getPreferences() {
        return this.preferences;
    }
}
