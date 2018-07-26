package com.usfit.stepcounter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.widget.Button;

public class DetailManager {

    MediaPlayer sfx_player;
    Context context;
    public DetailManager(Context _context){
        context = _context;
       sfx_player = MediaPlayer.create(context,R.raw.sfx_confirm);
    }
    public void PlaySound(int track){
        //if sound is enabled
        boolean soundEnabled = true;


        if(!soundEnabled)return;
        sfx_player = MediaPlayer.create(context,track);
        sfx_player.start();
    }
    public   void PlayAnimation(Button b, Drawable d){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ((AnimationDrawable) d).start();
            b.setForeground(d);

        }
    }
}
