package com.example.android.coffeeorder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URL;
import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;
    boolean hasWhippedCream , hasChocolate ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
       /* int numberOfCoffees = quantity;
        display(numberOfCoffees);
        displayPrice(numberOfCoffees * 5) ;
        */
        CheckBox cream = (CheckBox)findViewById(R.id.cb_wipped_cream);
       hasWhippedCream = cream.isChecked();

       CheckBox  chocolate = (CheckBox)findViewById(R.id.cb_chocolate);
       hasChocolate = chocolate.isChecked();

        EditText enteredName =(EditText)findViewById(R.id.ed_name);
        String name = enteredName.getText().toString();

       int price = calculatePrice();
      String priceMessage = orderSummary(price ,hasWhippedCream,hasChocolate,name);
     // displayMessage(priceMessage);


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order summary to: "+name);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    private void displayMessage (String message){

        TextView priceTV = (TextView) findViewById(R.id.order_summary_text_view);
        priceTV.setText(message);

    }

    public void increment(View view) {

        quantity = quantity + 1;
        display(quantity);

    }

    public void decrement(View view) {
        if (quantity > 0) {
            quantity = quantity - 1;
            display(quantity);

        } else {
            display(0);
        }
    }

    private int calculatePrice (){

        int price =quantity*5;

        if (hasChocolate){
            price = price +(quantity * 1) ;

        }if (hasWhippedCream){
            price = price +(quantity * 2);
        }


        return price;

    }
    private String orderSummary (int price,boolean addWhippedCream, boolean addChocolate,String addName){

        String priceMessage = " Name : "+ addName;
        priceMessage += "\n total is:  " + quantity + " cups of coffee";
        priceMessage += "\n has whipped cream :  "+ addWhippedCream;
        priceMessage +="\n has chocolate : " +addChocolate;
        priceMessage += "\n the price is : " + price ;
        priceMessage += "$\n thank you come again" ;

        Log.v("title","price is: " + price);


        return priceMessage;
    }


    public void composeEmail(String email, String subject, Uri attachment) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_STREAM, attachment);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}