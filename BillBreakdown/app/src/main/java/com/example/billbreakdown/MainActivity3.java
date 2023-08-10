package com.example.billbreakdown;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity {

    final Context context = this;
    int numFriend = 0, equalPax, amountPax;
    double totalAmount, remainBill, equalBill;
    ArrayList<structFriend> structList = new ArrayList<>();

    private void updateUI() {
        StringBuilder resultBuilder = new StringBuilder();
        TextView showList = findViewById(R.id.showResult);

        for (structFriend f : structList) {
            // write into one people one line
            // configure the format
            String writeLine = "Name: " + f.getName() + "     RM " + f.getBill() + "\n";
            resultBuilder.append(writeLine);
        }

        // Get lines from builder to textview to list out all data collected
        String getWrite = resultBuilder.toString();
        showList.setText(getWrite);
    }

    // functions
    public void switchActivity() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        setContentView(R.layout.activity_main);
    }
    public void switchActivity2() {
        Intent intent = new Intent(this,MainActivity2.class);
        startActivity(intent);
        setContentView(R.layout.activity_main2);
    }
    public void switchActivity3() {
        Intent intent = new Intent(this,MainActivity3.class);
        startActivity(intent);
        setContentView(R.layout.activity_main3);
    }

    // functions end

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        // radio button to switch pages
        RadioButton byEqual = (RadioButton)findViewById(R.id.equalBut);
        RadioButton byAmount = (RadioButton)findViewById(R.id.amountBut);
        RadioButton byCustom = (RadioButton)findViewById(R.id.customBut);

        Button calculate = (Button)findViewById(R.id.calculator);
        EditText getNumber = (EditText)findViewById(R.id.numOfPeople);
        EditText getTotal = (EditText)findViewById(R.id.totalOfBill);
        TextView showEqual = (TextView)findViewById(R.id.showEqual);

        // switch activity
        byEqual.setOnClickListener(view -> {
            if (byEqual.isChecked()) {
                switchActivity();
            }
        });
        byAmount.setOnClickListener(view -> {
            if (byAmount.isChecked()) {
                switchActivity2();
            }
        });
        byCustom.setOnClickListener(view -> {
            if (byCustom.isChecked()) {
                switchActivity3();
            }
        });

        calculate.setOnClickListener(view -> {
            // get data from user input
            numFriend = Integer.parseInt(getNumber.getText().toString());
            totalAmount = Double.parseDouble(getTotal.getText().toString());
            remainBill = totalAmount;

            // let every friend enter their name and their bill
            for (int i = 0; i < numFriend; i++) {

                // inflate dialog view
                LayoutInflater layoutInf = LayoutInflater.from(context);
                View dialogView = layoutInf.inflate(R.layout.amount_dialog, null);

                // get views from dialogView
                EditText inputName = dialogView.findViewById(R.id.inputName);
                EditText inputAmount = dialogView.findViewById(R.id.inputAmount);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(dialogView);

                // ok button in the dialog
                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // variable to put into structFriend
                        String name = inputName.getText().toString();
                        // get the value of entered amount first
                        double amount = Double.parseDouble(inputAmount.getText().toString());
                        remainBill -= amount;

                        // Create a structFriend object
                        structFriend friend = new structFriend(name, amount);

                        // Store the data into the arraylist
                        structList.add(friend);
                        amountPax++;

                        // Update UI to show the entered data
                        updateUI();
                    }
                });

                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Calculate equal split for the remaining people
                        equalPax = numFriend - amountPax;
                        equalBill = remainBill / equalPax;
                        String strBill = String.format("%.2f", equalBill);
                        showEqual.setText("Remaining " + equalPax + " people\nRM " + strBill + "  per pax");
                        dialog.cancel();
                    }
                });

                // Create and show the alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // Show the alert dialog
                alertDialog.show();
            }
        });
    }
}