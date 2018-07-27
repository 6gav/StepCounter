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

//endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        IsShop = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);


        lst = findViewById(R.id.lvInvItems);
        btnInvApply = findViewById(R.id.btnInvApply);
        detailManager = new DetailManager(this);
        ArrayAdapter<String> A = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        CreateItems();
        LoadShopData("Purchased",A);
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




}
