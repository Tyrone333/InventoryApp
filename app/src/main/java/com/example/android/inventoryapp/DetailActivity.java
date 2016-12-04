package com.example.android.inventoryapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventoryapp.data.InventoryDbHelper;

/**
 * Created by tyrone3 on 04.12.16.
 */

public class DetailActivity extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_view);

        final InventoryDbHelper db = new InventoryDbHelper(this);

        final TextView nameView = (TextView) findViewById(R.id.name_text_view);

        final TextView quantityView = (TextView) findViewById(R.id.quantity_text_view);

        final TextView priceView = (TextView) findViewById(R.id.price_text_view);

        ImageView imageView = (ImageView) findViewById(R.id.photo_display_detail_view);

        Button minusButton = (Button) findViewById(R.id.minus);

        Button plusButton = (Button) findViewById(R.id.plus);

        Button sendEmail = (Button) findViewById(R.id.order_product);

        Button deleteProduct = (Button) findViewById(R.id.delete_button);

        Intent intent = getIntent();
        Product currentProduct = intent.getExtras().getParcelable("extra");

        //Poupulate all the information in the view
        final String name = currentProduct.getName();
        nameView.setText(name);
        quantityView.setText(String.valueOf(currentProduct.getQuantity()));
        priceView.setText(getString(R.string.dollar_sign) + String.valueOf(currentProduct.getPrice()));
        byte[] image = currentProduct.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        imageView.setImageBitmap(bitmap);


        //Button for minus one quantity
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = Integer.parseInt(quantityView.getText().toString());
                number--;
                quantityView.setText(String.valueOf(number));
                db.updateItem(name, number);
            }
        });

        //Button for plus one quantity
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = Integer.parseInt(quantityView.getText().toString());
                number++;
                quantityView.setText(String.valueOf(number));
                db.updateItem(name, number);
            }
        });

        //Button to delete the product
        deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                builder.setTitle(R.string.delete_);
                builder.setMessage(getString(R.string.really_delete) + name);
                builder.setPositiveButton(R.string.delete_,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                db.deleteOneProduct(name);
                                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                builder.setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(DetailActivity.this, R.string.canceled, Toast.LENGTH_SHORT).show();
                            }
                        });

                builder.create().show();

            }
        });

        //Button to send Email to supplier
        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String body = getString(R.string.order_text) + name;
                composeEmail(getString(R.string.test_email_address), getString(R.string.order_subject), body);
            }
        });

    }

    //Email function
    public void composeEmail(String address, String subject, String body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, address);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
