package com.usfit.stepcounter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class DetailManager {

    MediaPlayer sfx_player;
    Context context;
    int selectedTrack;
    static Timer timer;

    public DetailManager(Context _context){
        context = _context;
        selectedTrack = R.raw.sfx_confirm;
        sfx_player = MediaPlayer.create(context,selectedTrack);
        timer = new Timer();
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

    public void DrawCrisp(ImageView i, int did){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), did);

            RoundedBitmapDrawable mDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), bitmap);
            //mDrawable.setCircular(true);
            mDrawable.setFilterBitmap(false);
            i.setImageDrawable(mDrawable);

/*
            AliasingDrawableWrapper wrapper = new AliasingDrawableWrapper(i.getDrawable());

            wrapper.draw(canvas);*/
        }
    }
    public RoundedBitmapDrawable DrawCrisp(int did){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), did);

            RoundedBitmapDrawable mDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), bitmap);
            //mDrawable.setCircular(true);
            mDrawable.setFilterBitmap(false);

            return mDrawable;
        }
        else
            return null;
    }

    public static void AddObject(final View v, int x, int y, final ConstraintLayout layout, long delay){
        v.setX(x);
        v.setY(y);


        int index = layout.getChildCount();
        layout.addView(v,index);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                DetailManager.RemoveObject(layout,v);
            }
        },delay);
    }
    public static void RemoveObject(ConstraintLayout layout, View v){
        layout.removeView(v);
    }

    public void makeText(String text){
        Toast.makeText(context,text, Toast.LENGTH_SHORT);
    }

    public static void DrawPlayer(Activity activity, User user){
        int ufac = R.drawable.expression_0,
                uhar = R.drawable.hair_00,
                ubod = R.drawable.body_s0,
                utop = R.drawable.outfit_t00,
                ubot = R.drawable.outfit_b00,
                ufot = R.drawable.outfit_f00;



        ((ImageView)activity.findViewById(R.id.ivAvatarFace)).setImageDrawable(getDrawable(activity,user.mface + ufac));
        ((ImageView)activity.findViewById(R.id.ivAvatarHead)).setImageDrawable(getDrawable(activity,user.mhair + uhar));
        ((ImageView)activity.findViewById(R.id.ivAvatarBody)).setImageDrawable(getDrawable(activity,user.mbody + ubod));

        ((ImageView)activity.findViewById(R.id.ivAvatarTop)).setImageDrawable(getDrawable(activity,user.mTop +   utop));
        ((ImageView)activity.findViewById(R.id.ivAvatarBottom)).setImageDrawable(getDrawable(activity,user.mBot+ ubot));
        ((ImageView)activity.findViewById(R.id.ivAvatarFeet)).setImageDrawable(getDrawable(activity,user.mFoot + ufot));
    }

    public static void DrawPlayer(Activity activity, SharedPreferences preferences){
        if(preferences == null)
        {
            preferences = activity.getSharedPreferences("com.usfit.stepcounter.marketplace", Context.MODE_PRIVATE);
        }
        ((ImageView)activity.findViewById(R.id.ivAvatarFace)).setImageDrawable(getDrawable(activity,preferences.getInt("expression_0",R.drawable.expression_0),preferences.getInt("expression_0T",0)));
        ((ImageView)activity.findViewById(R.id.ivAvatarHead)).setImageDrawable(getDrawable(activity,preferences.getInt("hair_00",R.drawable.hair_00),preferences.getInt("hair_00T",0)));
        ((ImageView)activity.findViewById(R.id.ivAvatarBody)).setImageDrawable(getDrawable(activity,preferences.getInt("body_s0",R.drawable.body_s0),preferences.getInt("body_s0T",0)));

        ((ImageView)activity.findViewById(R.id.ivAvatarTop)).setImageDrawable(getDrawable(activity,preferences.getInt("outfit_t00",R.drawable.outfit_t00),preferences.getInt("outfit_t00T",9)));
        ((ImageView)activity.findViewById(R.id.ivAvatarBottom)).setImageDrawable(getDrawable(activity,preferences.getInt("outfit_b00",R.drawable.outfit_b00),preferences.getInt("outfit_b00T",0)));
        ((ImageView)activity.findViewById(R.id.ivAvatarFeet)).setImageDrawable(getDrawable(activity,preferences.getInt("outfit_f00",R.drawable.outfit_f00),preferences.getInt("outfit_f00T",0)));
    }
    private static Drawable getDrawable(Activity activity, int drawable_id,int tint){
        Drawable drawable = activity.getDrawable(drawable_id);
        Color color = new Color();
        color.alpha(255);
        color.red(0);
        color.blue(255);
        color.green(0);
        //tint = Color.argb(100,255,0,0);
        if(tint != 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            drawable.setColorFilter(tint, PorterDuff.Mode.ADD);

        }else {
            drawable.setTintList(null);
        }

        return drawable;
    }
    private static Drawable getDrawable(Activity activity, int drawable_id){
        Drawable drawable = activity.getDrawable(drawable_id);
        Color color = new Color();
        color.alpha(255);
        color.red(0);
        color.blue(255);
        color.green(0);


        return drawable;
    }
}
