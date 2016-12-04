package com.example.android.inventoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.android.inventoryapp.data.InventoryDbHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final InventoryDbHelper db = new InventoryDbHelper(this);
        ListView listview = (ListView) findViewById(R.id.list);
        listview.setEmptyView(findViewById(R.id.empty_view));
        final ArrayList<Product> list = db.getAllData();

        //Setup a onClickListener that will be passed into the adapter for clicks on the Add/Delete Button
        final AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, final View view, final int position, long l) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                Product extra = list.get(position);
                intent.putExtra("extra", extra);
                startActivity(intent);
            }
        };

        final InventoryListAdapter adapter = new InventoryListAdapter(MainActivity.this, list, listener);
        listview.setAdapter(adapter);

        //FAB Listener
        FloatingActionButton addInventory = (FloatingActionButton) findViewById(R.id.fab);
        addInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditAddActivity.class);
                startActivity(intent);
            }
        });

        //Button to delete all entries
        Button deleteAll = (Button) findViewById(R.id.delete_all_button);
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteAllProducts();
                finish();
                startActivity(getIntent());
            }
        });
    }
}
