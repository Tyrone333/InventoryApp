package com.example.android.inventoryapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.inventoryapp.data.InventoryDbHelper;

import java.io.ByteArrayOutputStream;

/**
 * Created by tyrone3 on 03.12.16.
 */

public class EditAddActivity extends AppCompatActivity {

    String name;
    int price;
    int quantity;

    boolean newProduct = true;

    ImageView mImageView;

    byte[] image;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_add_activity);


        final InventoryDbHelper db = new InventoryDbHelper(this);

        final EditText nameEdit = (EditText) findViewById(R.id.name_edit_text);

        final EditText quantityEdit = (EditText) findViewById(R.id.quantity_edit_text);

        final EditText priceEdit = (EditText) findViewById(R.id.price_edit_text);

        mImageView = (ImageView) findViewById(R.id.photo_display);


        //Button to add an item to the database and validate the input first
        Button addInventory = (Button) findViewById(R.id.add_product);
        addInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameEdit.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(EditAddActivity.this, R.string.error_name_missing, Toast.LENGTH_SHORT).show();
                    return;
                }
                String mQuantity = (quantityEdit.getText().toString());
                if (mQuantity.matches("")) {
                    Toast.makeText(EditAddActivity.this, R.string.error_quantity, Toast.LENGTH_SHORT).show();
                    return;
                }
                quantity = Integer.parseInt(mQuantity);
                String mPrice = (priceEdit.getText().toString());
                if (mPrice.matches("")) {
                    Toast.makeText(EditAddActivity.this, R.string.error_price, Toast.LENGTH_SHORT).show();
                    return;
                }
                price = Integer.parseInt(mPrice);
                if (image == null) {
                    Toast.makeText(EditAddActivity.this, R.string.error_picture, Toast.LENGTH_SHORT).show();
                    return;
                }
                db.insertData(name, quantity, price, image, newProduct);
                Intent intent = new Intent(EditAddActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //Button to take a picture
        Button b = (Button) findViewById(R.id.photo);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
    }

    //Result of the picture taken
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            mImageView.setImageBitmap(imageBitmap);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            image = stream.toByteArray();
        }
    }
}

