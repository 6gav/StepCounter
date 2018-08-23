package com.usfit.stepcounter;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public  class ShopActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    //Components
    ImageView currentImage;
    ListView lst;
    Button btnPurchase, btnEquip;
    TextView MoneyText;
    Timer timer;
    DetailManager detailManager;

    //Preferences
    SharedPreferences preferences;

    //Arrays/Enums
    Item[] items,shop;
    enum ClothingType{
        CT_Top,
        CT_Bottom,
        CT,FootWear,
        CT_Hat
    }

    //region arrays
    int[] cost;

    int[] body = {
            R.drawable.body_s0,
            R.drawable.body_s1,
            R.drawable.body_s2,
            R.drawable.body_s3,
            R.drawable.body_s4,
            R.drawable.body_s5
    };

    int[] headwear = {
            R.drawable.outfit_h00,

            R.drawable.outfit_h01,
            R.drawable.outfit_h02,
            R.drawable.outfit_h03,

            R.drawable.outfit_h04,
            R.drawable.outfit_h05,
            R.drawable.outfit_h06,

            R.drawable.outfit_h07,
            R.drawable.outfit_h08,
            R.drawable.outfit_h09,

            R.drawable.outfit_h10,
            R.drawable.outfit_h11,
            R.drawable.outfit_h12,

            R.drawable.outfit_h13,
            R.drawable.outfit_h14,//17
            R.drawable.outfit_h15,

            R.drawable.outfit_h16,
            R.drawable.outfit_h17,
            R.drawable.outfit_h18,

            R.drawable.outfit_h19,
            R.drawable.outfit_h20,
            R.drawable.outfit_h21,

            R.drawable.outfit_h22,
            R.drawable.outfit_h23,
            R.drawable.outfit_h24,

            R.drawable.outfit_h25,
            R.drawable.outfit_h26,




    };

    int[] expressions = {
            R.drawable.outfit_e00,
            R.drawable.outfit_e01,
            R.drawable.outfit_e02,

            R.drawable.outfit_e03,
            R.drawable.outfit_e04,
            R.drawable.outfit_e05,

            R.drawable.outfit_e06,
            R.drawable.outfit_e07,
            R.drawable.outfit_e08,

            R.drawable.outfit_e09,//7
    };

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
            R.drawable.outfit_t17,//18

            R.drawable.outfit_t18,
            R.drawable.outfit_t19,
            R.drawable.outfit_t20,

            R.drawable.outfit_t21,
            R.drawable.outfit_t22,
            R.drawable.outfit_t23,

            R.drawable.outfit_t24,
            R.drawable.outfit_t25,
            R.drawable.outfit_t26,

            R.drawable.outfit_t27,
            R.drawable.outfit_t28,
            R.drawable.outfit_t29,

            R.drawable.outfit_t30,
            R.drawable.outfit_t31,
            R.drawable.outfit_t32,

            R.drawable.outfit_t33,//34

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
            R.drawable.outfit_b10,//11

            R.drawable.outfit_b11,
            R.drawable.outfit_b12,
            R.drawable.outfit_b13,

            R.drawable.outfit_b14,
            R.drawable.outfit_b15,
            R.drawable.outfit_b16,

            R.drawable.outfit_b17,
            R.drawable.outfit_b18,
            R.drawable.outfit_b19,

            R.drawable.outfit_b20,
            R.drawable.outfit_b21,
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

            R.drawable.outfit_f09,//10
            R.drawable.outfit_f10,
            R.drawable.outfit_f11,
            R.drawable.outfit_f12,

            R.drawable.outfit_f13,
            R.drawable.outfit_f14,
            R.drawable.outfit_f15,

            R.drawable.outfit_f16,
            R.drawable.outfit_f17,
            R.drawable.outfit_f18
    };
//endregion

    //Variables
    boolean Validated = false;
    private int MonValue, itemoffset, MaxItems = 150,selectedItem = 0, purchases = 0,cue = 0,pp = -1,itemCostMultipler = 1450;
    Item _selectedItemObject = null,previousItem = null;
    Boolean IsShop = true;




    /////////////////////////////// functions /////////////////////////////////////////////////////

    private  void PlayAnimation(Button b, Drawable d){
        detailManager.PlayAnimation(b,d);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        detailManager.PlaySound(R.raw.sfx_select);
        _selectedItemObject = shop[position];
        DetailManager.DrawPlayer(this,preferences);
        if(pp != -1){
            previousItem = shop[pp];
            if(previousItem.getTag() != _selectedItemObject.getTag()){

            }
        }
        pp = position;

        currentImage = findViewById(DetailManager.GetPartFromTag(_selectedItemObject.getTag()));

        btnPurchase.setEnabled(_selectedItemObject != null);

        if( btnPurchase.isEnabled()){
            btnPurchase.setEnabled(!_selectedItemObject.isPurchased());
        }
        btnEquip.setEnabled(_selectedItemObject.isPurchased());
        detailManager.DrawCrisp(currentImage,_selectedItemObject.getImage_Id());
    }
    private int GetCostMultiplier(int i){
        return itemCostMultipler*(int)(i+4)+1000;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shop);
        lst = findViewById(R.id.lvItems);
        timer = new Timer();
        preferences = getApplicationContext().getSharedPreferences("com.usfit.stepcounter.marketplace", Context.MODE_PRIVATE);
        MonValue = preferences.getInt("MonValue", 0);
        purchases = preferences.getInt("Purchased Items", 0);


        DetailManager.DrawPlayer(this,preferences);
        items = new Item[MaxItems];
        shop = new Item[MaxItems];
        cost = getResources().getIntArray(R.array.costs);

        if(IsShop == true) {
            detailManager = new DetailManager(this);

            btnEquip = findViewById(R.id.btnEquip);
            btnPurchase = findViewById(R.id.btnPurchase);
            MoneyText = findViewById(R.id.tvMonValueInfo);


            CreateItems();
            ///region initialize base items
            selectedItem = 0;
            if(false == items[selectedItem].isPurchased()){
                _selectedItemObject = items[selectedItem];
                Purchase();
                Equip();
            }
            selectedItem = top.length;
            if(false == items[selectedItem].isPurchased()){
                _selectedItemObject = items[selectedItem];
                Purchase();
                Equip();
            }
            selectedItem = top.length+ bottom.length+1;
            if(false == items[selectedItem].isPurchased()){
                _selectedItemObject = items[selectedItem];
                Purchase();
                Equip();
            }
            LoadShopData(preferences.getString("ShopTag", "outfit_t00outfit_b00outfit_f00hair_00"));
            MoneyUpdate();


        }
        //currentImage.setImageDrawable(items[selectedItem].getImage());
    }

    @Override
    protected void onDestroy() {
        if(IsShop) {
            detailManager.PlaySound(R.raw.sfx_deny);
            detailManager.Release();
        }
        super.onDestroy();
    }

    public void OnCheckBoxClick(View v){
        detailManager.PlaySound(R.raw.sfx_cbx_toggle);
        OnCheckBoxClick();
    }
    public void OnCheckBoxClick(){
        CheckBox[] boxes = new CheckBox[5];
        boxes[0] = findViewById(R.id.cbxHead);
        boxes[1] = findViewById(R.id.cbxTop);
        boxes[2] = findViewById(R.id.cbxBottom);
        boxes[3] = findViewById(R.id.cbxFootwear);
        boxes[4] = findViewById(R.id.cbxPurchased);

        String alltags = " ",tag = "";
        for(int i = 0; i < boxes.length; i++){
            if(boxes[i].isChecked()) {
                switch (boxes[i].getText().toString()) {
                    case "Top":
                        tag = "outfit_t00 ";
                        break;
                    case "Head":
                        tag = "A_HED ";
                        break;
                    case "Footwear":
                        tag = "outfit_f00 ";
                        break;
                    case "Bottoms":
                        tag = "outfit_b00 ";
                        break;
                    case "Purchased":
                        tag = "Purchased";
                        break;
                }
            }
            alltags += tag;
        }
        LoadShopData(alltags);
    }

    protected void CreateItems() {
        int i,j = 0;
        String allNames = getResources().getString(R.string.shop_items_top);
        String[] topNames,botNames,fotNames,facNames,hedNames,bodNames;
        topNames = allNames.split(",");
        allNames = getResources().getString(R.string.shop_items_bottom);
        botNames = allNames.split(",");
        allNames = getResources().getString(R.string.shop_items_footwear);
        fotNames = allNames.split(",");


        allNames = getResources().getString(R.string.shop_items_face);
        facNames = allNames.split(",");
        allNames = getResources().getString(R.string.shop_items_body);
        bodNames = allNames.split(",");
        allNames = getResources().getString(R.string.shop_items_hair);
        hedNames = allNames.split(",");

        String description;
        boolean isPurchased;
        for(i = 0; i < (top.length);i++){
            Item item = new Item();
            item.setCost(GetCostMultiplier(i));
            item.setName("Shirt "+i);
            item.setImage(top[i]);
            item.setTag("outfit_t00");
            isPurchased = preferences.getBoolean(item.getImage_Id()+"purchased?",false);
            item.setPurchased(isPurchased);
            description = "";
            if(!item.isPurchased()) {
                description += "Purchase " + item.getName() + " for " + item.getCost() + "$.";
            }
            else {
                description += "Equip " + item.getName()+ ".";
            }
            item.setDescription(description);
            items[j] = item;
            ++j;
        }
        j = top.length;
        for(i = 0; i < bottom.length;i++){
            Item item = new Item();
            item.setCost(GetCostMultiplier(i));
            item.setName("Bottoms "+i);
            item.setImage(bottom[i]);
            item.setTag("outfit_b00");
            isPurchased = preferences.getBoolean(item.getImage_Id()+"purchased?",false);description = "";
            item.setPurchased(isPurchased);
            description = "";
            if(!item.isPurchased()) {
                description += "Purchase " + item.getName() + " for " + item.getCost() + "$.";
            }
            else {
                description += "Equip " + item.getName()+ ".";
            }
            item.setDescription(description);
            items[j] = item;
            ++j;
        }
        j = top.length+ bottom.length-1;
        for(i = 0; i < footwear.length;i++){
            Item item = new Item();
            item.setCost(GetCostMultiplier(i));
            item.setName("Footwear "+i);
            item.setImage(footwear[i]);
            item.setTag("outfit_f00");
            isPurchased = preferences.getBoolean(item.getImage_Id()+"purchased?",false);description = "";
            item.setPurchased(isPurchased);
            description = "";
            if(!item.isPurchased()) {
                description += "Purchase " + item.getName() + " for " + item.getCost() + "$.";
            }
            else {
                description += item.getName()+ ".";
            }
            item.setDescription(description);
            items[j] = item;
            ++j;
        }
        for(i = 0; i < (headwear.length-12);i++){
            Item item = new Item();
            item.setCost(0);
            item.setName("Hair " + i);
            item.setImage(headwear[i]);
            item.setTag("outfit_h00");
            isPurchased = true;//preferences.getBoolean(item.getImage_Id()+"purchased?",false);
            item.setPurchased(isPurchased);
            description = "";
            description += "Wear " + item.getName()+ ".";
            item.setDescription(description);
            items[j] = item;
            ++j;
        }
        for(i = headwear.length-12; i < (headwear.length);i++){
            Item item = new Item();
            item.setCost(GetCostMultiplier(i));
            item.setName("Headwear " + (i-15));
            item.setImage(headwear[i]);
            item.setTag("A_HED");
            isPurchased = preferences.getBoolean(item.getImage_Id()+"purchased?",false);
            item.setPurchased(isPurchased);
            description = "";
            description += "Purchase " + item.getName()+ " for " + item.getCost() + "$.";
            item.setDescription(description);
            items[j] = item;
            ++j;
        }
        for(i = 0; i < (body.length);i++){
            Item item = new Item();
            item.setCost(0);
            item.setName("Body " + i);
            item.setImage(body[i]);
            item.setTag("body_s0");
            isPurchased = true;//preferences.getBoolean(item.getImage_Id()+"purchased?",false);
            item.setPurchased(isPurchased);
            description = "";
            description += "Wear " + item.getName()+ ".";
            item.setDescription(description);
            items[j] = item;
            ++j;
        }
        for(i = 0; i < (facNames.length);i++){
            Item item = new Item();
            item.setCost(0);
            item.setName("Face " + i);
            item.setImage(expressions[i]);
            item.setTag("expression_00");
            isPurchased = true;//preferences.getBoolean(item.getImage_Id()+"purchased?",false);
            item.setPurchased(isPurchased);
            description = "";
            description += "Wear " + item.getName()+ ".";
            item.setDescription(description);
            items[j] = item;
            ++j;
        }
    }

    //

    public void LoadShopData(){
        LoadShopData("All");
    }
    public void LoadShopData(String Tag) {
        LoadShopData(Tag, new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1));
    }

    public void LoadShopData(String Tag,ArrayAdapter<String> AAdapter) {
        int i,j = 0;
        for (i = 0; i < MaxItems; ++i) {
            /*Item item = new Item();
            item.setCost(5+10 * i);
            item.setName("Item #" + i);
            item.setTag("t");
            item.setDescription("Purchase " + item.getName() + " for " + item.getCost() + "$.");
            items[i] = item;*/
            //
            boolean displayPurchasedItems = Tag.contains("Purchased");//search for purchased items

            if(items[i] != null) {
                if(Tag.contains(items[i].getTag()) || Tag.contains("All")) {
                        if(displayPurchasedItems && items[i].isPurchased()) {
                            AAdapter.add(items[i].getDescription());
                            shop[j] = items[i];
                            items[i].setPosition(j);
                            j++;
                        }
                        else if(!displayPurchasedItems){
                            AAdapter.add(items[i].getDescription());
                            shop[j] = items[i];
                            items[i].setPosition(j);
                            j++;
                        }
                    }
                }
            }
        if(lst != null) {
            lst.setAdapter(AAdapter);
            lst.setOnItemClickListener(this);
        }
    }


    public void MoneyUpdate(View v){
        MoneyUpdate();
    }
    public void MoneyUpdate(){
        MoneyText.setText("X " + preferences.getInt("MonValue", 0));
    }
    public void MoneyUpdate(int i){
        SharedPreferences.Editor p = preferences.edit();
        p.putInt("MonValue",i);
        p.apply();
    }

    public void SelectionUp(View v) {
        itemoffset = (itemoffset > 0 ? itemoffset - 1 : itemoffset);
        LoadShopData();
    }

    public void SelectionDown(View v) {
        itemoffset = (itemoffset < MaxItems-3 ? itemoffset + 1 : itemoffset);
        LoadShopData();
    }
    public void Purchase(View v) {
        boolean purchaseSuccessful = Purchase();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (purchaseSuccessful) {
                detailManager.PlaySound(R.raw.sfx_buy);
                PlayAnimation(btnPurchase, getResources().getDrawable(R.drawable.blip_green));
            } else {
                detailManager.PlaySound(R.raw.sfx_reject);
            }
        }
    }

    public void Equip(View v){
        Equip();
    }
    public void Equip(){
        detailManager.PlaySound(R.raw.sfx_equip);
        detailManager.PlayAnimation(btnEquip,getResources().getDrawable(R.drawable.blip_green));

        SharedPreferences.Editor editor = preferences.edit();
        String tag = _selectedItemObject.getTag();
        if(tag == "A_HED")tag = "hair_00";
        editor.putInt(tag,_selectedItemObject.getImage_Id());
        editor.apply();

    }
    public boolean Purchase(){
        boolean purchased = false;

        //returns true if the purchase was successful
        if(_selectedItemObject != null){
            if(_selectedItemObject.isPurchased()){//if the item is already owned
                Toast.makeText(this,"You already have " + _selectedItemObject.getName(), Toast.LENGTH_SHORT).show();
        }else if(_selectedItemObject.getCost() <= MonValue) {//cost is affordable

                purchases+= (1>>_selectedItemObject.getPosition());
                MonValue -= _selectedItemObject.getCost();
                MoneyText.setText("X " + MonValue);
                purchased = true;
                _selectedItemObject.setPurchased(purchased);
                _selectedItemObject.setDescription("Equip " + _selectedItemObject.getName()+ ".");
                SharedPreferences.Editor editor = preferences.edit();

                editor.putInt("MonValue", MonValue);
                editor.putBoolean(_selectedItemObject.getImage_Id()+"purchased?",purchased);
                editor.apply();

                btnEquip.setEnabled(true);
                btnPurchase.setEnabled(false);

            OnCheckBoxClick();

            }

        }
        else {
            btnPurchase.setEnabled(false);
        }
        return purchased;
    }

    class Item {
        private int cost,position,image_id;
        private String name, description, tag;
        private boolean purchased;

        public void setCost(int c) {
            cost = c;
        }

        public void setDescription(String s) {
            description = s;
        }

        public void setName(String s) {
            name = s;
        }

        public void setTag(String s) {
            tag = s;
        }

        public void setImage(int i) {
            image_id = i;
        }

        public void setPurchased(boolean p) {
            purchased = p;
        }

        public void setPosition(int position) {
            position = position;
        }

        public int getPosition() {
            return position;
        }
        public int getImage_Id(){
            return image_id;
        }

        public boolean isPurchased() {
            return purchased;
        }

        public String getDescription() {
            return description;
        }

        public String getTag() {
            return tag;
        }

        public Drawable getImage() {

            try {
                return getResources().getDrawable(image_id);//detailManager.DrawCrisp(image_id);
            }catch (Exception e){
                e.printStackTrace();
            }
            return getResources().getDrawable(R.drawable.googleg_disabled_color_18);
        }

        public int getCost() {
            return cost;
        }

        public String getName() {
            return name;
        }

    }

}

