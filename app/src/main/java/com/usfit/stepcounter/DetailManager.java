package com.usfit.stepcounter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;

public class DetailManager {

    MediaPlayer sfx_player;
    Context context;
    int selectedTrack;
    public DetailManager(Context _context){
        context = _context;
        selectedTrack = R.raw.sfx_confirm;
        sfx_player = MediaPlayer.create(context,selectedTrack);
    }

    public void PlaySound(int track){
        //if sound is enabled
        boolean soundEnabled = true;


        if(!soundEnabled)return;

            selectedTrack = track;
            Release();
            sfx_player = MediaPlayer.create(context, track);

        sfx_player.start();
    }

    public   void PlayAnimation(Button b, Drawable d){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            AnimationDrawable drawable = (AnimationDrawable)d;
            b.setForeground(drawable);
            drawable.start();

        }
    }
    public   void PlayAnimation(ImageView iv, Drawable d){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            AnimationDrawable drawable = (AnimationDrawable)d;
            iv.setForeground(drawable);
            drawable.start();
        }
    }
    public   void PlayAnimation(ImageView iv){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            AnimationDrawable drawable = (AnimationDrawable)iv.getDrawable();
            iv.setForeground(drawable);
            drawable.start();
        }
    }

    public void Release(){
        sfx_player.release();
    }
}
