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

public class AvatarCustomizationActivity extends ShopActivity {

    //region Declarations

    //Components
    Button btnAvatarApply;
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
        btnAvatarApply.setEnabled(_selectedItemObject != null);


        switch (_selectedItemObject.getTag()){
            case "A_HAR":
                currentImage = ivAvatarHead;
                img = 0;
                break;
                case "A_HED":
                currentImage = ivAvatarHead;
                img = 0;
                break;
            case "A_TOP":
                currentImage = ivAvatarTop;
                img = 1;
                break;
            case "A_BOT":
                currentImage = ivAvatarBottom;
                img = 2;
                break;
            case "A_FOT":
                currentImage = ivAvatarFeet;
                img = 3;
                break;
            case "A_FAC":
                currentImage = ivAvatarFace;
                img = 5;
                break;
            case "A_BOD":
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

        currentImage.setImageDrawable(_selectedItemObject.getImage());
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
        btnAvatarApply = findViewById(R.id.btnAvatarApply);

        lst = findViewById(R.id.lvAvatarItems);
        ArrayAdapter<String> A = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        CreateItems();
        LoadShopData("A_BOD A_FAC A_HAR",A);
        DrawPlayer();
    }
    void DrawPlayer(){
        DetailManager.DrawPlayer(this,preferences);
    }


    public void ApplyChanges(View v){
        SharedPreferences.Editor editor = preferences.edit();
        detailManager.PlaySound(R.raw.sfx_equip);
        detailManager.PlayAnimation((Button)findViewById(R.id.btnAvatarApply),getResources().getDrawable(R.drawable.blip_blue));
        String[] tags = {
                "A_HED",
                "A_TOP",
                "A_BOT",

                "A_FOT",
                "A_BOD",
                "A_FAC"
        };

        for(int i = 0; i < 6; i++)
            if(images[i] != -1)
                editor.putInt(tags[i],images[i]);

        tempImage = currentImage.getDrawable();
        editor.apply();

    }
}
