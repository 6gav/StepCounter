package com.stepcountercounter.softdev.stepcounter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public  class ShopActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private int MonValue, itemoffset, MaxItems = 30,selectedItem = 0, purchases = 0;
    SharedPreferences preferences;
    Item[] items;
    Item _selectedItemObject = null;
    ImageView currentImage;
    ListView lst;
    Button btnPurchase, btnEquip;
    TextView MoneyText;
    /////////////////////// private variable initialization ///////////////////////////////////////

    enum ClothingType{
        CT_Top,
        CT_Bottom,
        CT,FootWear,
        CT_Hat
    }

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
            25,
            50,//pants

            30,
            60,
            90,//footwear
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
            R.drawable.outfit_t17,
    };

    int[] bottom = {
            R.drawable.outfit_b1,
            R.drawable.outfit_b1,
            R.drawable.outfit_b1
    };

    int[] footwear = {
            R.drawable.outfit_f1,
            R.drawable.outfit_f1,
            R.drawable.outfit_f1
    };

    /////////////////////////////// functions /////////////////////////////////////////////////////
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //TextView tv = (TextView)view;
        //Toast.makeText(this,"You clicked on " + tv.getText() + position, Toast.LENGTH_SHORT).show();
        _selectedItemObject = items[position];
        btnPurchase.setEnabled(_selectedItemObject != null);
        btnEquip.setEnabled(_selectedItemObject.isPurchased());
        currentImage.setImageDrawable(_selectedItemObject.getImage());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);


        preferences = getApplicationContext().getSharedPreferences("com.stepcountercounter.marketplace", Context.MODE_PRIVATE);
        MonValue = preferences.getInt("MonValue", 0);
        purchases = preferences.getInt("Purchased Items", 0);
        items = new Item[MaxItems];

        lst = findViewById(R.id.lvShopItems);
        currentImage = findViewById(R.id.ivCurrentImage);
        btnEquip = findViewById(R.id.btnEquip);
        btnPurchase = findViewById(R.id.btnPurchase);
        MoneyText = findViewById(R.id.tvMonValueInfo);

        CreateItems();
        LoadShopData(preferences.getString("ShopTag","All"));
        MoneyUpdate();

        //currentImage.setImageDrawable(items[selectedItem].getImage());
    }

    private void CreateItems() {
        int i,j = 0;
        String allNames = getResources().getString(R.string.shop_item_names);
        String[] names = allNames.split(",");

        for(i = 0; i < top.length/4;i++){
            Item item = new Item();
            item.setCost(cost[j]);
            item.setName("Shirt "+i);
            item.setName(names[j]);
            item.setImage(top[i]);
            item.setTag("A_TOP");
            item.setPurchased(preferences.getBoolean(item.getImage_Id()+"purchased?",false));
            item.setDescription("Purchase " + item.getName() + " for " + item.getCost() + "$.");
            items[j] = item;
            ++j;
        }

        for(i = 0; i < bottom.length;i++){
            Item item = new Item();
            item.setCost(cost[j]);
            item.setName("Bottoms "+i);
            item.setImage(bottom[i]);
            item.setTag("A_BOT");
            item.setPurchased(preferences.getBoolean(item.getImage_Id()+"purchased?",false));
            item.setDescription("Purchase " + item.getName() + " for " + item.getCost() + "$.");
            items[j] = item;
            ++j;

        }
        for(i = 0; i < footwear.length;i++){
            Item item = new Item();
            item.setCost(cost[j]);
            item.setName("Footwear "+i);
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
        ArrayAdapter<String> AAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);

        for (int i = 0; i < MaxItems; ++i) {
            /*Item item = new Item();
            item.setCost(5+10 * i);
            item.setName("Item #" + i);
            item.setTag("t");
            item.setDescription("Purchase " + item.getName() + " for " + item.getCost() + "$.");
            items[i] = item;*/
            //
            if(items[i] != null) {
                if(items[i].getTag() == Tag || "All" == Tag) {
                    AAdapter.add(items[i].getDescription());
                    items[i].setPosition(i);
                }
            }
        }



        lst.setAdapter(AAdapter);
        lst.setOnItemClickListener(this);

    }


    public void MoneyUpdate(View v){
        MoneyUpdate();
    }
    public void MoneyUpdate(){
        MoneyText.setText("X " + preferences.getInt("MonValue", 0));
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
                _selectedItemObject.setPurchased(true);
                purchased = true;
                SharedPreferences.Editor editor = preferences.edit();
                //editor.putInt("MonValue",get(MoneyText.getText()));
                editor.putInt("MonValue", MonValue);
                editor.putBoolean(_selectedItemObject.getImage_Id()+"purchased?",purchased);
                btnEquip.setEnabled(true);
                editor.apply();
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
        private Drawable image;
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

        public void setImage(Drawable i) {
            image = i;
        }

        public void setImage(int i) {
            image_id = i;
            try {
                image = getResources().getDrawable(i);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        public void setPurchased(boolean purchased) {
            purchased = purchased;
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
            return image;
        }

        public int getCost() {
            return cost;
        }

        public String getName() {
            return name;
        }

    }

}

