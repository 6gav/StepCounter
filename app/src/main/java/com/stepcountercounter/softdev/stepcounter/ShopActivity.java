package com.stepcountercounter.softdev.stepcounter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;

import org.w3c.dom.Text;

public  class ShopActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    //Components
    ImageView currentImage;
    ListView lst;
    Button btnPurchase, btnEquip;
    TextView MoneyText;
    Timer timer;

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
    int[] cost = {
            10,
            20,
            25,

            25,
            25,
            30,

            50,
            100,
            125,

            135,
            150,
            160,

            160,
            175,
            180,

            200,
            200,
            200,//shirts

            10,
            10,
            15,

            20,
            20,
            25,

            25,
            50,
            30,

            60,
            90,//pants

            10,
            10,
            10,

            15,
            15,
            25,

            20,
            20,
            20,

            30,
            35,//footwear

            //hats
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

    //Variables
    boolean Validated = false;
    private int MonValue, itemoffset, MaxItems = 50,selectedItem = 0, purchases = 0,cue = 0;
    Item _selectedItemObject = null;
    Boolean IsShop = true;




    /////////////////////////////// functions /////////////////////////////////////////////////////
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        _selectedItemObject = shop[position];
        btnPurchase.setEnabled(_selectedItemObject != null);
        if( btnPurchase.isEnabled()){
            btnPurchase.setEnabled(!_selectedItemObject.isPurchased());
        }
        btnEquip.setEnabled(_selectedItemObject.isPurchased());
        currentImage.setImageDrawable(_selectedItemObject.getImage());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shop);

        timer = new Timer();
        preferences = getApplicationContext().getSharedPreferences("com.stepcountercounter.marketplace", Context.MODE_PRIVATE);
        MonValue = preferences.getInt("MonValue", 0);
        purchases = preferences.getInt("Purchased Items", 0);

        items = new Item[MaxItems];
        shop = new Item[MaxItems];
        if(IsShop == true) {

            lst = findViewById(R.id.lvShopItems);
            currentImage = findViewById(R.id.ivCurrentImage);
            btnEquip = findViewById(R.id.btnEquip);
            btnPurchase = findViewById(R.id.btnPurchase);
            MoneyText = findViewById(R.id.tvMonValueInfo);


            CreateItems();
            LoadShopData(preferences.getString("ShopTag", "All"));
            MoneyUpdate();
            TimerTask t = new TimerTask() {
                @Override
                public void run() {

                    MonValue++;
                    //MoneyUpdate(MonValue);
                }
            };

            timer.scheduleAtFixedRate(t, 500, 500);
        }
        //currentImage.setImageDrawable(items[selectedItem].getImage());
    }



    public void OnCheckBoxClick(View v){
        CheckBox[] boxes = new CheckBox[5];
        boxes[0] = findViewById(R.id.cbxHead);
        boxes[1] = findViewById(R.id.cbxTop);
        boxes[2] = findViewById(R.id.cbxBottom);
        boxes[3] = findViewById(R.id.cbxFootwear);
        boxes[4] = findViewById(R.id.cbxPurchased);

        String alltags = " ",tag = "";
        for(int i = 0; i < 4; i++){
            if(boxes[i].isChecked()) {
                switch (boxes[i].getText().toString()) {
                    case "Top":
                        tag = "A_TOP ";
                        break;
                    case "Head":
                        tag = "A_HED ";
                        break;
                    case "Footwear":
                        tag = "A_FOT ";
                        break;
                    case "Bottoms":
                        tag = "A_BOT ";
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
        String[] topNames,botNames,fotNames;
        topNames = allNames.split(",");
        allNames = getResources().getString(R.string.shop_items_bottom);
        botNames = allNames.split(",");
        allNames = getResources().getString(R.string.shop_items_footwear);
        fotNames = allNames.split(",");



        for(i = 0; i < (top.length);i++){
            Item item = new Item();
            item.setCost(cost[j]);
            item.setName("Shirt "+i);
            item.setName(topNames[j]);
            item.setImage(top[i]);
            item.setTag("A_TOP");
            item.setPurchased(preferences.getBoolean(item.getImage_Id()+"purchased?",false));
            item.setDescription("Purchase " + item.getName() + " for " + item.getCost() + "$.");
            items[j] = item;
            ++j;
        }
        j = top.length;
        for(i = 0; i < bottom.length;i++){
            Item item = new Item();
            item.setCost(cost[j]);
            item.setName("Bottoms "+i);
            item.setName(botNames[i]);
            item.setImage(bottom[i]);
            item.setTag("A_BOT");
            item.setPurchased(preferences.getBoolean(item.getImage_Id()+"purchased?",false));
            item.setDescription("Purchase " + item.getName() + " for " + item.getCost() + "$.");
            items[j] = item;
            ++j;
        }
        j = top.length+ bottom.length;
        for(i = 0; i < footwear.length;i++){
            Item item = new Item();
            item.setCost(cost[j]);
            item.setName("Footwear "+i);
            item.setName(fotNames[i]);
            item.setImage(footwear[i]);
            item.setTag("A_FOT");
            item.setPurchased(preferences.getBoolean(item.getImage_Id()+"purchased?",false));
            item.setDescription("Purchase " + item.getName() + " for " + item.getCost() + "$.");
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
            boolean purchased = Tag.contains("Purchased");
            if(items[i] != null) {
                if(Tag.contains(items[i].getTag()) || "All" == Tag || purchased) {
                        if(!purchased) {
                            AAdapter.add(items[i].getDescription());
                            shop[j] = items[i];
                            items[i].setPosition(j);
                            j++;
                        }
                        else if(items[i].isPurchased()){
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
    public void Purchase(View v){
        boolean n = Purchase();
    }
    public void Equip(View v){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(_selectedItemObject.getTag(),_selectedItemObject.getImage_Id());
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

                SharedPreferences.Editor editor = preferences.edit();

                editor.putInt("MonValue", MonValue);
                editor.putBoolean(_selectedItemObject.getImage_Id()+"purchased?",purchased);
                editor.apply();

                btnEquip.setEnabled(true);
                btnPurchase.setEnabled(false);
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
                return getResources().getDrawable(image_id);
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

