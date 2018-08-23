package com.usfit.stepcounter;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class AvatarCustomizationActivity extends ShopActivity {

    //region Declarations

    //Components
    Button btnAvatarCustApply;
    Drawable tempImage;
    DetailManager detailManager;
    //arrays
    int[] images = {
            -1,
            -1,
            -1,

            -1,
            -1,
            -1
    };

    //variables
    int img = -1,ldrawn = -1;


    //endregion
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //TextView tv = (TextView)view;
        //Toast.makeText(this,"You clicked on " + tv.getText() + position, Toast.LENGTH_SHORT).show();
        int lmg = img;
        boolean dblClick = false;
        detailManager.PlaySound(R.raw.sfx_select);
        ImageView lv = currentImage;

        _selectedItemObject = shop[position];
        btnAvatarCustApply.setEnabled(_selectedItemObject != null);


        switch (_selectedItemObject.getTag()){
            case "hair_00":
                currentImage = ivAvatarHead;
                img = 0;
                break;
                case "A_HED":
                currentImage = ivAvatarHead;
                img = 0;
                break;
            case "outfit_t00":
                currentImage = ivAvatarTop;
                img = 1;
                break;
            case "outfit_b00":
                currentImage = ivAvatarBottom;
                img = 2;
                break;
            case "outfit_f00":
                currentImage = ivAvatarFeet;
                img = 3;
                break;
            case "expression_00":
                currentImage = ivAvatarFace;
                img = 5;
                break;
            case "body_s0":
                currentImage = ivAvatarBody;
                img = 4;
                break;
            default:
                currentImage = ivAvatarTop;
                break;

        }

        if(lmg != img && tempImage != null)
            lv.setImageDrawable(tempImage);

        tempImage = currentImage.getDrawable();

        detailManager.DrawCrisp(currentImage,_selectedItemObject.getImage_Id());
        if(img!= -1)
            images[img] = _selectedItemObject.getImage_Id();

        if(ldrawn != -1){
            if(ldrawn == images[img]){
                ldrawn = -1;
                ApplyChanges(view);
            }else {
                ldrawn = images[img];
            }
        }else
        {
            ldrawn = images[img];
        }
    }
ImageView ivAvatarFace,ivAvatarHead,ivAvatarBody,ivAvatarTop,ivAvatarBottom,ivAvatarFeet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        IsShop = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar_customization);
        detailManager = new DetailManager(this);

        ivAvatarFace = ((ImageView)findViewById(R.id.ivAvatarFace));
        ivAvatarHead = ((ImageView)findViewById(R.id.ivAvatarHead));
        ivAvatarBody = ((ImageView)findViewById(R.id.ivAvatarBody));
        ivAvatarTop = ((ImageView)findViewById(R.id.ivAvatarTop));
        ivAvatarBottom = ((ImageView)findViewById(R.id.ivAvatarBottom));
        ivAvatarFeet = ((ImageView)findViewById(R.id.ivAvatarFeet));
        btnAvatarCustApply = findViewById(R.id.btnAvatarCustApply);

        lst = findViewById(R.id.lvItems);
        ArrayAdapter<String> A = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        CreateItems();
        LoadShopData("body_s0 expression_00 outfit_h00",A);
        DrawPlayer();
    }
    void DrawPlayer(){
        DetailManager.DrawPlayer(this,preferences);
    }


    public void ApplyChanges(View v){

        detailManager.PlayAnimation(btnAvatarCustApply,getDrawable(R.drawable.blip_blue));
        SharedPreferences.Editor editor = preferences.edit();
        detailManager.PlaySound(R.raw.sfx_equip);
        //detailManager.PlayAnimation((ListView)findViewById(R.id.lvAvatarItems),getResources().getDrawable(R.drawable.blip_blue));
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
}
