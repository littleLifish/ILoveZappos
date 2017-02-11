package myapp.findyouritem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends Activity {

    private ListView lv = null;
    private EditText edtText = null;
    private DatabaseHandler dbHandler;
    private List<Items> listItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new DatabaseHandler(this);
        Button btnSreach = (Button)this.findViewById(R.id.search);
        btnSreach.setOnClickListener(listener);
        Button btnCart = (Button)this.findViewById(R.id.cart);
        edtText = (EditText) this.findViewById(R.id.text1);
        lv = (ListView)this.findViewById(R.id.listview);

        DatabaseHandler db = new DatabaseHandler(this);
        //insert items
        if(db.getAllItems() == null)
        {
            db.onUpdate();
            InsertItems(db);
        }

        lv = (ListView) findViewById(R.id.listview);
        //lv.setOnItemClickListener(itemListener);

        registerForContextMenu(lv);
        showData(-1);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Cart.class);
                startActivity(intent);
                //overridePendingTransition();
            }
        });
    }

    private void showData(int num){
        //List<Items> listItem = null;
        if(num < 0){
            listItem = dbHandler.getAllItems();
        }
        else {
            String brand = edtText.getText().toString();
            listItem = dbHandler.findByType(brand);
        }
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
        for(Items items : listItem){
            HashMap<String, Object> row = new HashMap<String, Object>();
            row.put("brand", items.getBrand());
            row.put("dis", items.getDisc());
            row.put("price", items.getPrice());
            row.put("type", items.getType());
            data.add(row);
        }
        SimpleAdapter adapter = buildListAdapter(this, data);
        lv.setAdapter(adapter);

        showLog();
    }

    public SimpleAdapter buildListAdapter(Context context,
                                          List<HashMap<String, Object>> data) {
        SimpleAdapter adapter = new SimpleAdapter(context, data, R.layout.items,
                new String[]{"brand", "discription", "price","type"}, new int[]{R.id.brand, R.id.discript, R.id.price,R.id.type});
        return adapter;
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.search:
                    String brand = edtText.getText().toString();
                    Toast.makeText(MainActivity.this, "search: "+brand +"!", Toast.LENGTH_LONG).show();
                   if(brand == "")
                        showData(-1);
                    else
                       showData(1);
                    break;
                default:
                    break;
            }
        }
    };


    private final class ItemClickListener implements AdapterView.OnItemClickListener {

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ListView listView = (ListView) parent;
            HashMap<String, Object> data = (HashMap<String, Object>) listView.getItemAtPosition(position);
            String item = data.get("brand").toString();
            Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();
        }
    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void InsertItems(DatabaseHandler db){

        // Inserting Contacts
        Log.d("Insert: ", "Inserting ..");
        db.addItems(new Items(1,"Columbia", "Sporty Tee", 35, "Clothing"));
        db.addItems(new Items("Nike", "Tank Top", 45, "Clothing"));
        db.addItems(new Items("Nike", "Tank Top", 69, "Clothing"));
        db.addItems(new Items("Columbia", "Sporty Tee", 25, "Clothing"));
        db.addItems(new Items("Lucy", "Tank Top", 49, "Clothing"));
        db.addItems(new Items("PUMA", "Blouse S/S", 35, "Clothing"));
        db.addItems(new Items("Volcom", "Blouse S/S", 35, "Clothing"));
        db.addItems(new Items("Volcom", "Blouse", 15, "Clothing"));
        db.addItems(new Items("Volcom", "Blouse", 55, "Clothing"));
        db.addItems(new Items("PUMA", "Jacket", 37, "Clothing"));
        db.addItems(new Items("PUMA", "Jacket", 28, "Clothing"));
        db.addItems(new Items("Columbia", "Jacket", 62, "Clothing"));
        db.addItems(new Items("Columbia", "Jacket", 42, "Clothing"));
        db.addItems(new Items("UGG", "Classics", 105, "Shoes"));
        db.addItems(new Items("UGG", "Classics", 120, "Shoes"));
        db.addItems(new Items("UGG", "Classics", 95, "Shoes"));
        db.addItems(new Items("TOMS", "SkateBoarding", 70, "Shoes"));
        db.addItems(new Items("TOMS", "SkateBoarding", 80, "Shoes"));
        db.addItems(new Items("ADIDAS", "SkateBoarding", 99, "Shoes"));
        db.addItems(new Items("ADIDAS", "SkateBoarding", 85, "Shoes"));
        db.addItems(new Items("Kipling", "Bombora", 55, "Bags"));
        db.addItems(new Items("Kipling", "Bombora", 52, "Bags"));
        db.addItems(new Items("Dakine", "Jive", 35, "Bags"));
        db.addItems(new Items("Dakine", "Jive", 25, "Bags"));
        db.addItems(new Items("Dakine", "Jo Jo", 15, "Bags"));
        db.addItems(new Items("Dakine", "Jo Jo", 35, "Bags"));
        db.addItems(new Items("Dakine", "EQ BAG", 35, "Bags"));
        db.addItems(new Items("Dakine", "EQ BAG", 35, "Bags"));

    }

    private void showLog(){
        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        //List<Items> itms = db.getAllItems();

        for (Items cn : listItem) {
            String log = "Id: " + cn.getID() + " ,Brand: " + cn.getBrand() + " ,Discription: " + cn.getDisc()
                    + " ,Price: " +cn.getPrice() + " ,Type: " + cn.getType();
            // Writing Contacts to log
            Log.d("Brand: ", log);
        }
    }
}
