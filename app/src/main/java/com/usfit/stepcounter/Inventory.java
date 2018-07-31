package com.usfit.stepcounter;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

public class Inventory extends ShopActivity {

    //region Declarations

    //Components
    Button btnInvApply;
    Drawable tempImage;
    DetailManager detailManager;
    //arrays
    int[] images = {
            -1,
            -1,
            -1
    };

    //variables
    int img = -1;


    //region arrays
    int[] cost;
    int[] top = {
            R.drawable.outfit_t00,
            R.drawable.outfit_t01,
            R.drawable.outfit_t02,

            R.drawable.outfit_t03,
            R.drawable.outfit_t04,
            R.drawable.outfit_t05,

            R.drawable.outfit_t06,
            R.drawable.outfit_t07,
            R.drawable.outfit_t08,

            R.drawable.outfit_t09,
            R.drawable.outfit_t10,
            R.drawable.outfit_t11,

            R.drawable.outfit_t12,
            R.drawable.outfit_t13,
            R.drawable.outfit_t14,

            R.drawable.outfit_t15,
            R.drawable.outfit_t16,
            R.drawable.outfit_t17//18
    };

    int[] bottom = {
            R.drawable.outfit_b00,
            R.drawable.outfit_b01,
            R.drawable.outfit_b02,

            R.drawable.outfit_b03,
            R.drawable.outfit_b04,
            R.drawable.outfit_b05,

            R.drawable.outfit_b06,
            R.drawable.outfit_b07,
            R.drawable.outfit_b08,

            R.drawable.outfit_b09,
            R.drawable.outfit_b10//11
    };

    int[] footwear = {
            R.drawable.outfit_f00,
            R.drawable.outfit_f01,
            R.drawable.outfit_f02,

            R.drawable.outfit_f03,
            R.drawable.outfit_f04,
            R.drawable.outfit_f05,

            R.drawable.outfit_f06,
            R.drawable.outfit_f07,
            R.drawable.outfit_f08,

            R.drawable.outfit_f09//10
    };
//endregion

    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        IsShop = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        detailManager = new DetailManager(this);

        lst = findViewById(R.id.lvInvItems);
        btnInvApply = findViewById(R.id.btnInvApply);
        ArrayAdapter<String> A = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        CreateItems();
        LoadShopData("AllPurchased",A);
        DrawPlayer();
    }

    void DrawPlayer(){
        ((ImageView)findViewById(R.id.ivInvFace)).setImageDrawable(getDrawable(preferences.getInt("A_FAC",R.drawable.expression_0)));
        ((ImageView)findViewById(R.id.ivInvHead)).setImageDrawable(getDrawable(preferences.getInt("A_HED",R.drawable.hair_00)));
        ((ImageView)findViewById(R.id.ivInvBody)).setImageDrawable(getDrawable(preferences.getInt("A_BOD",R.drawable.body_s0)));

        ((ImageView)findViewById(R.id.ivInvTop)).setImageDrawable(getDrawable(preferences.getInt("A_TOP",R.drawable.outfit_t00)));
        ((ImageView)findViewById(R.id.ivInvBottom)).setImageDrawable(getDrawable(preferences.getInt("A_BOT",R.drawable.outfit_b00)));
        ((ImageView)findViewById(R.id.ivInvFeet)).setImageDrawable(getDrawable(preferences.getInt("A_FOT",R.drawable.outfit_f1)));
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //TextView tv = (TextView)view;
        //Toast.makeText(this,"You clicked on " + tv.getText() + position, Toast.LENGTH_SHORT).show();
        int lmg = img;
        detailManager.PlaySound(R.raw.sfx_select);
        ImageView lv = currentImage;
        _selectedItemObject = shop[position];
        btnInvApply.setEnabled(_selectedItemObject != null);
        switch (_selectedItemObject.getTag()){
            case "A_HED":
                currentImage = findViewById(R.id.ivInvTop);
                img = 0;
                break;
            case "A_TOP":
                currentImage = findViewById(R.id.ivInvTop);
                img = 0;
                break;
            case "A_BOT":
                currentImage = findViewById(R.id.ivInvBottom);
                img = 1;
                break;
            case "A_FOT":
                currentImage = findViewById(R.id.ivInvFeet);
                img = 2;
                break;
        }
        if(lmg != img && tempImage != null)
            lv.setImageDrawable(tempImage);

        tempImage = currentImage.getDrawable();

        currentImage.setImageDrawable(_selectedItemObject.getImage());
        if(img!= -1)
        images[img] = _selectedItemObject.getImage_Id();

    }

    public void ApplyChanges(View v){
        SharedPreferences.Editor editor = preferences.edit();
        detailManager.PlaySound(R.raw.sfx_equip);
        detailManager.PlayAnimation((Button)findViewById(R.id.btnInvApply),getResources().getDrawable(R.drawable.blip_blue));
        String[] tags = {
                "A_TOP",
                "A_BOT",
                "A_FOT"
        };

        for(int i = 0; i < 3; i++)
            if(images[i] != -1)
                editor.putInt(tags[i],images[i]);

        tempImage = currentImage.getDrawable();
        editor.apply();

    }

    @Override
    protected void onDestroy() {
        detailManager.PlaySound(R.raw.sfx_deny);
        detailManager.Release();

        super.onDestroy();
    }
}
