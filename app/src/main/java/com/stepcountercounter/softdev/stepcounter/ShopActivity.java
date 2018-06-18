package com.stepcountercounter.softdev.stepcounter;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public  class ShopActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private int MonValue, itemoffset, MaxItems = 5;
    SharedPreferences preferences;
    Item[] items;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView tv = (TextView)view;
        Toast.makeText(this,"You clicked on " + tv.getText() + position, Toast.LENGTH_SHORT).show();
    }

    enum ClothingType{
        CT_Top,
        CT_Bottom,
        CT,FootWear,
        CT_Hat
    }

    int[] top = {
            R.drawable.outfit_t1,
            R.drawable.outfit_t2,
            R.drawable.outfit_t3
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

    ListView lst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = getApplicationContext().getSharedPreferences("com.stepcountercounter.marketplace", 0);
        MonValue = preferences.getInt("MonValue", 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        items = new Item[MaxItems];
        lst = findViewById(R.id.lvShopItems);
        LoadShopData();
    }

    //
    public void LoadShopData() {

        for (int i = 0; i < MaxItems; ++i) {
            Item item = new Item();
            item.setCost(5+10 * i);
            item.setName("Item #" + i);
            item.setTag("t");
            item.setDescription("Purchase " + item.getName() + " for " + item.getCost() + "$.");
            items[i] = item;
        }

        ArrayAdapter<Item> AAdapter = new ArrayAdapter<Item>(this,android.R.layout.simple_list_item_1);
        AAdapter.addAll(items);
        lst.setAdapter(AAdapter);
        lst.setOnItemClickListener(this);

    }


    public void MoneyUpdate(View v){
        TextView MoneyText = findViewById(R.id.tvMonValueInfo);
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

    }

    class Item {
        private int cost;
        private String name, description, tag;
        private Drawable image;

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

