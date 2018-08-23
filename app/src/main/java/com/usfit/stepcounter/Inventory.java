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
            -1,

            -1,
            -1,
            -1,
    };

    //variables
    int img = -1;


    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        IsShop = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        detailManager = new DetailManager(this);
        lst = findViewById(R.id.lvItems);
        btnInvApply = findViewById(R.id.btnInvApply);
        ArrayAdapter<String> A = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        CreateItems();
        LoadShopData("outfit_t00outfit_b00outfit_f00PurchasedA_HED",A);
        DrawPlayer();
    }

    void DrawPlayer(){
        DetailManager.DrawPlayer(this,preferences);
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
            case "hair_00":
                currentImage = findViewById(R.id.ivAvatarHead);
                img = 0;
                break;
                case "A_HED":
                currentImage = findViewById(R.id.ivAvatarHead);
                img = 0;
                break;
            case "outfit_t00":
                currentImage = findViewById(R.id.ivAvatarTop);
                img = 1;
                break;
            case "outfit_b00":
                currentImage = findViewById(R.id.ivAvatarBottom);
                img = 2;
                break;
            case "outfit_f00":
                currentImage = findViewById(R.id.ivAvatarFeet);
                img = 3;
                break;
            case "body_s0":
                currentImage = findViewById(R.id.ivAvatarBody);
                img = 4;
                break;
            case "expression_00":
                currentImage = findViewById(R.id.ivAvatarFace);
                img = 5;
                break;
        }

        if(lmg != img && tempImage != null)
            lv.setImageDrawable(tempImage);

        tempImage = currentImage.getDrawable();

        detailManager.DrawCrisp(currentImage,_selectedItemObject.getImage_Id());
        if(img!= -1)
        images[img] = _selectedItemObject.getImage_Id();

    }

    public void ApplyChanges(View v){
        SharedPreferences.Editor editor = preferences.edit();
        detailManager.PlaySound(R.raw.sfx_equip);
        detailManager.PlayAnimation((Button)findViewById(R.id.btnInvApply),getResources().getDrawable(R.drawable.blip_blue));

        String[] tags = {
                "hair_00",
                "outfit_t00",
                "outfit_b00",

                "outfit_f00",
                "body_s0",
                "expression_00"
        };

        for(int i = 0; i < 6; i++)
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
