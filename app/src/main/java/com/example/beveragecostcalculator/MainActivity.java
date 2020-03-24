package com.example.beveragecostcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //creating objects
    TextView appName, bvgName, bvgSize;
    EditText custName;
    AutoCompleteTextView Province;
    RadioButton Coffee, Tea, None, Vanilla, Chocolate, Lemon, Mint;
    CheckBox Milk, Sugar;
    Spinner Size;
    RadioGroup Flavor, Beverage;
    Button Order;
    //creating string arrays for size spinner and province autocomplete textview
    final String[] arySize = {"Small", "Medium", "Large"};
    final String[] aryProvince = {"Alberta", "British Columbia", "Manitoba", "New Brunswick", "Newfoundland and Labrador", "Nova Scotia", "Ontario", "Prince Edward Island", "Quebec", "Saskatchewan"};

    //creating static variables.
    public static double smallCost = 1.5;
    public static double mediumCost = 2.5;
    public static double largeCost = 3.25;
    public static double milkCost = 1.25;
    public static double sugarCost = 1.0;
    public static double sugarAndMilk = 2.25;
    public static double vanillaCost = 0.25;
    public static double chocolateCost = 0.75;
    public static double mintCost = 0.5;
    public static double lemonCost = 0.25;
    public static double tax = 0.13;


    //on create method that will run at start
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //linking the objects
        appName = findViewById(R.id.tvAppName);
        bvgName = findViewById(R.id.tvBeverage);
        bvgSize = findViewById(R.id.tvSize);
        custName = findViewById(R.id.tvCustName);
        Coffee = findViewById(R.id.rdCoffee);
        Tea = findViewById(R.id.rdTea);
        None = findViewById(R.id.rdNone);
        Vanilla = findViewById(R.id.rdVani);
        Chocolate = findViewById(R.id.rdChoco);
        Lemon = findViewById(R.id.rdLemon);
        Mint = findViewById(R.id.rdMint);
        Milk = findViewById(R.id.chkMilk);
        Sugar = findViewById(R.id.chkSugar);
        Flavor = findViewById(R.id.rdgFlavor);
        Beverage = findViewById(R.id.rdgBeverage);
        Order = findViewById(R.id.btnOrder);
        //creating and setting an array adapter for the spinner
        Size = findViewById(R.id.spnSize);
        ArrayAdapter<String> aptSize = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arySize);
        aptSize.setDropDownViewResource(android.R.layout.simple_spinner_item);
        Size.setAdapter(aptSize);
        //creating and setting an array adapter for the autocomplete textview
        Province = findViewById(R.id.actvProvince);
        ArrayAdapter<String> aptProvince = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, aryProvince);
        //setting threshold to show data in autocomplete textview after user enters two characters
        Province.setThreshold(2);
        Province.setAdapter(aptProvince);

        //listener for when the user selects the tea radio button
        Tea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if statements to enable and disable flavor radio buttons when tea is selected
                if (Tea.isChecked()) {
                    Chocolate.setEnabled(false);
                    Vanilla.setEnabled(false);
                    Lemon.setEnabled(true);
                    Mint.setEnabled(true);
                } else {
                    Lemon.setEnabled(false);
                    Mint.setEnabled(false);
                    Chocolate.setEnabled(true);
                    Vanilla.setEnabled(true);
                }
            }
        });

        //listener for when the user selects the coffee radio button
        Coffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if statements to enable and disable flavor radio buttons when coffee is selected
                if (Coffee.isChecked()) {
                    Chocolate.setEnabled(true);
                    Vanilla.setEnabled(true);
                    Lemon.setEnabled(false);
                    Mint.setEnabled(false);
                } else {
                    Lemon.setEnabled(true);
                    Mint.setEnabled(true);
                    Chocolate.setEnabled(false);
                    Vanilla.setEnabled(false);
                }
            }
        });

        //listener for when the user clicks the order button
        Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calling the validate function when the button is clicked
                validate();
            }
        });
    }

    //custom function named validate
    public void validate() {
        //creating variables
        double amount = 0, grandTotal;
        String flavorMsg = "", nameMsg, provinceMsg, message = "";

        //if statements for when the user selects the size of the beverage
        if (Size.getSelectedItem().toString().equals("Small")) {
            amount = smallCost;
            message += " a small ";
        } else if (Size.getSelectedItem().toString().equals("Medium")) {
            amount = mediumCost;
            message += " a medium ";

        } else if (Size.getSelectedItem().toString().equals("Large")) {
            amount = largeCost;
            message += " a large ";

        }

        //nested if statements for flavoring cost and display message when the user selects tea
        if (Tea.isChecked()) {
            if (Lemon.isChecked()) {
                amount += lemonCost;
                flavorMsg += "lemon flavoring, cost: $";
            } else if (Mint.isChecked()) {
                amount += mintCost;
                flavorMsg += "mint flavoring, cost: $";
            }
            message += "tea, ";
        }
        //nested if statements for flavoring cost and display message when the user selects coffee
        else if (Coffee.isChecked()) {
            if (Vanilla.isChecked()) {
                amount += vanillaCost;
                flavorMsg += " vanilla flavoring, cost: $";
            } else if (Chocolate.isChecked()) {
                amount += chocolateCost;
                flavorMsg += " chocolate flavoring, cost: $";
            }
            message += "coffee, ";
        }

        //if statement for when the user selects no flavoring
        if (None.isChecked()) {
            flavorMsg += " no flavoring, cost: $";
        }

        //if statements for when the user selects sugar or milk or both
        if (Sugar.isSelected() && Milk.isSelected()) {
            amount += sugarAndMilk;
            message += " with sugar and milk, ";
        } else if (Sugar.isSelected()) {
            amount += sugarCost;
            message += " with sugar, ";
        } else if (Milk.isSelected()) {
            amount += milkCost;
            message += " with milk, ";
        }

        //storing name entered by the user in nameMsg
        nameMsg = custName.getText().toString();
        //storing province selected by the user in provinceMsg
        provinceMsg = Province.getText().toString();
        //mathematical calculations
        amount += amount * tax;
        grandTotal = amount;

        //displaying a toast with the final message once user clicks order button
        Toast.makeText(getApplicationContext(), "For " + nameMsg + " " + "from " + provinceMsg + ", " + message + flavorMsg + String.format("%.2f", grandTotal), Toast.LENGTH_LONG).show();


    }
}




