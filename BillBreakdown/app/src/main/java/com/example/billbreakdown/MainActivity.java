package com.example.billbreakdown;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    int numFriend = 0;
    double totalAmount, equalBill;

    // functions
    public static double equalBreak(int people, double price) {
        // get number of friend and break down the bill equally
        // return the calculated result
        return price / people;
    }
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

    // app starting
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button calculate = (Button)findViewById(R.id.calculator);
        EditText getNumber = (EditText)findViewById(R.id.numOfPeople);
        EditText getTotal = (EditText)findViewById(R.id.totalOfBill);

        // radio button to switch pages
        RadioButton byEqual = (RadioButton)findViewById(R.id.equalBut);
        RadioButton byAmount = (RadioButton)findViewById(R.id.amountBut);
        RadioButton byCustom = (RadioButton)findViewById(R.id.customBut);

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
            // calculate the amount equally
            equalBill = equalBreak(numFriend,totalAmount);

            TextView resultView = (TextView)findViewById(R.id.resultDesc);
            resultView.setText("per pax");

            TextView outPut = (TextView)findViewById(R.id.showResult);
            // store the result in a string
            // because setText() only accept string
            String strBill = String.format("%.2f",equalBill);
            outPut.setText("RM " + strBill);
        });
    }
}