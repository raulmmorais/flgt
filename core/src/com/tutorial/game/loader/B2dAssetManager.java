package com.tutorial.game.loader;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class B2dAssetManager {
    public final AssetManager manager = new AssetManager();

    public final static String PLAYER_IMAGE = "images/player.png";
    public final static String ENEMY_IMAGE = "images/enemy.png";
    public final static String BOING_SOUND = "sounds/boing.wav";
    public final static String PING_SOUND = "sounds/ping.wav";
    public final static String ESCAPE_MUSIC = "music/escape.mp3";
    public final static String TRACK_07_MUSIC = "music/track07.mp3";

    public final static String SKIN = "skin/glassy-ui.json";

    public void queueAddImages(){
        manager.load(PLAYER_IMAGE, Texture.class);
        manager.load(ENEMY_IMAGE, Texture.class);
    }
    public void queueAddSounds(){
        manager.load(BOING_SOUND, Sound.class);
        manager.load(PING_SOUND, Sound.class);
    }
    public void queueAddMusic(){
        manager.load(ESCAPE_MUSIC, Music.class);
        manager.load(TRACK_07_MUSIC, Music.class);
    }

    public void queueAddSkin(){
        SkinParameter params = new SkinParameter("skin/glassy-ui.atlas");
        manager.load(SKIN, Skin.class, params);
    }
}
