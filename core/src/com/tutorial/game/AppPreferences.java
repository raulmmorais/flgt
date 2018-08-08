package com.tutorial.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class AppPreferences {
    private static final String PREF_MUSIC_VOLUME = "volume";
    private static final String PREF_MUSIC_ENABLE = "music.enable";
    private static final String PREF_SOUND_ENABLE = "sound.enable";
    private static final String PREF_SOUND_VOL = "sound";
    private static final String PREFS_NAME = "b2dtut";

    private final Preferences prefsObj = Gdx.app.getPreferences(PREFS_NAME);

    protected Preferences getPrefs(){
        //return Gdx.app.getPreferences(PREFS_NAME);
        return prefsObj;
    }

    public float getMusicVolume(){
        return getPrefs().getFloat(PREF_MUSIC_VOLUME, 0.5f);
    }
    public void setMusicVolume(float volume){
        Gdx.app.log("Volume", "Change" + volume);
        getPrefs().putFloat(PREF_MUSIC_VOLUME, volume);
        getPrefs().flush();
    }

    public float getSoundVolume(){
        return getPrefs().getFloat(PREF_SOUND_VOL, 0.5f);
    }
    public void setSoundVolume(float volume){
        Gdx.app.log("Volume", "Change" + volume);
        getPrefs().putFloat(PREF_SOUND_VOL, volume);
        getPrefs().flush();
    }

    public boolean isMusicEnabled(){
        return getPrefs().getBoolean(PREF_MUSIC_ENABLE, true);
    }
    public void setMusicEnabled(boolean musicEnabled){
        Gdx.app.log("Volume", "Change" + musicEnabled);
        getPrefs().putBoolean(PREF_MUSIC_ENABLE, musicEnabled);
        getPrefs().flush();
    }

    public boolean isSoundEffectsEnabled(){
        return getPrefs().getBoolean(PREF_SOUND_ENABLE, true);
    }
    public void setSoundEffectsEnabled(boolean soundEffectsEnabled){
        Gdx.app.log("Volume", "Change" + soundEffectsEnabled);
        getPrefs().putBoolean(PREF_SOUND_ENABLE, soundEffectsEnabled);
        getPrefs().flush();
    }
}
