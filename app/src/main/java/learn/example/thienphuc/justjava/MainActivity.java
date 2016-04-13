package learn.example.thienphuc.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private int quantity = 1; //number of coffee cups
    private int price = 0; //cost of the order
    private boolean hasWhippedCream = false; //has whipped cream on top ?
    private boolean hasChocolate = false; //has chocolate on top ?
    private String name = ""; //person's name who ordered

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText nameEditText = (EditText) findViewById(R.id.name_edit_text);
        name = nameEditText.getText().toString();
    }

    /**
     * This method displays the given text on the screen.
     *
     * return None
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView)findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView)findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method is called to calculate the price of order based on the current quantity
     */
    private void calculatePrice() {
        price = quantity * 5;
        if (hasWhippedCream)
            price += 1;
        if (hasChocolate)
            price += 2;
    }

    /**
     * This method is called to summarize the order
     *
     * @return a message about the coffee order
     */
    private String createOrderSummary() {
        String message = getString(R.string.order_summary_name, name);
        message += "\n" + getString(R.string.order_summary_whipped_cream, hasWhippedCream);
        message += "\n" + getString(R.string.order_summary_chocolate, hasChocolate);
        message += "\n" + getString(R.string.order_summary_quantity, quantity);
        message += "\n" + getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(price));
        message += "\n" + getString(R.string.thank_you);
        return message;
    }

    /**
     * This method is called when - button was called.
     */
    public void decrement(View view) {
        if (this.quantity > 1)
            this.quantity -= 1;
        this.displayQuantity(quantity);
    }

    /**
     * This method is called when + button was called.
     *
     */
    public void increment(View view) {
        if (quantity < 100)
            this.quantity += 1;
        this.displayQuantity(quantity);
    }

    /**
     * This method is called when order button was submit.
     */
    public void submitOrder(View view) {
        calculatePrice();
        String summaryMessage = createOrderSummary();
        displayMessage(summaryMessage);

        //send information to email app
        Intent intent = new Intent((Intent.ACTION_SENDTO));
        intent.setData(Uri.parse("mailto:")); //only email should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for" + name);
        intent.putExtra(Intent.EXTRA_TEXT, summaryMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method is called when whipped cream was checked
     */
    public void isChecked(View view) {
        CheckBox whippedCreamCheckedBox = (CheckBox)findViewById(R.id.whipped_cream_check_box);
        CheckBox chocolateCheckBox = (CheckBox)findViewById(R.id.chocolate_check_box);
        if (whippedCreamCheckedBox.isChecked()) {
            hasWhippedCream = true;
        }
        else {
            hasWhippedCream = false;
        }

        if (chocolateCheckBox.isChecked()) {
            hasChocolate = true;
        }
        else {
            hasChocolate = false;
        }
    }
}
