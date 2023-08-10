package com.example.billbreakdown;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import java.util.ArrayList;

class structFriend implements Parcelable{
    private final String friendName;
    private final double billAmount;
    public structFriend(String name, double amount) {
        friendName = name;
        billAmount = amount;
    }
    public String getName() {
        return friendName;
    }
    public double getBill() {
        return billAmount;
    }

    @Override
    public String toString() {
        return("Name: " + friendName + "\nBill: " + billAmount);
    }
    // Parcelable methods
    protected structFriend(Parcel in) {
        friendName = in.readString();
        billAmount = in.readDouble();
    }

    public static final Creator<structFriend> CREATOR = new Creator<structFriend>() {
        @Override
        public structFriend createFromParcel(Parcel in) {
            return new structFriend(in);
        }

        @Override
        public structFriend[] newArray(int size) {
            return new structFriend[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(friendName);
        dest.writeDouble(billAmount);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
public class MainActivity2 extends AppCompatActivity {

    final Context context = this;
    int numFriend = 0; // prompt dialog base on number of people
    double totalAmount; // the total of each own amount must be the same as totalAmount of the bill
    double checkTotal = 0;
    ArrayList<structFriend> structList = new ArrayList<>();

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

    private void updateUI() {
        StringBuilder resultBuilder = new StringBuilder();
        TextView showList = findViewById(R.id.showResult);

        for (structFriend f : structList) {
            // write into one people one line
            // configure the format
            String writeLine = "Name: " + f.getName() + "     RM " + f.getBill() + "\n";
            resultBuilder.append(writeLine);

        }
        // get lines from builder to textview to list out all data collected
        String getWrite = resultBuilder.toString();
        showList.setText(getWrite);
    }
    // functions end

    // app starting
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // button to get data then prompt dialog
        Button calculate = findViewById(R.id.calculator);

        // radio button to switch pages
        RadioButton byEqual = findViewById(R.id.equalBut);
        RadioButton byAmount = findViewById(R.id.amountBut);
        RadioButton byCustom = findViewById(R.id.customBut);
        EditText getNumber = findViewById(R.id.numOfPeople);
        EditText getTotal = findViewById(R.id.totalOfBill);
        TextView showError = findViewById(R.id.showResult);

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
                        double amount = Double.parseDouble(inputAmount.getText().toString());
                        // update the checkTotal
                        checkTotal += amount;
                        // create a structFriend object
                        structFriend friend = new structFriend(name, amount);
                        // store the data into arraylist
                        structList.add(friend);
                        // Update UI to show the entered data
                        updateUI();

                    }
                });
                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                // Create and show the alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

    }
}