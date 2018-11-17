package e.kevin_st_john.jersey;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    private Item mCurrentItem;
    private TextView mNameTextView, mQuantityTextView;
    //jerseyColour = true means red
    private boolean jerseyColour=true;
    private ImageView mImageView;

    // Constants:
    private final static String PREFS = "PREFS";
    private static final String KEY_JERSEY_NAME = "KEY_JERSEY_NAME";
    private static final String KEY_JERSEY_NUMBER = "KEY_JERSEY_NUMBER";
    private static final String KEY_JERSEY_COLOUR = "KEY_JERSEY_COLOUR";

    // Add this method:
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_JERSEY_NAME, mCurrentItem.getName());
        // Put the other fields into the editor
        editor.putInt(KEY_JERSEY_NUMBER, mCurrentItem.getQuantity());
        editor.putBoolean(KEY_JERSEY_COLOUR, jerseyColour);

        editor.commit();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mCurrentItem= new Item();

        //Restore values if needed
        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);

        String name = prefs.getString(KEY_JERSEY_NAME, "Android");
        // Get the other fields. Then use them all
        int number = prefs.getInt(KEY_JERSEY_NUMBER, 18);
        boolean colour = prefs.getBoolean(KEY_JERSEY_COLOUR, true);

        mCurrentItem.setName(name);
        mCurrentItem.setQuantity(number);
        jerseyColour=colour;

        //id name text is in content main textbox
        mNameTextView = findViewById(R.id.name_text);
        //id name_text is in content main textbox
        // this is on the main screen
        mQuantityTextView = findViewById(R.id.quantity_text);




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                insertItem(false);
            }
        });

        showCurrentItem();
    }

    private void insertItem(final boolean isEdit) {
        // this launches the dialog box
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //customise dialog
        //builder.setMessage("hello");

        // need to inflate it
        View view = getLayoutInflater().inflate(R.layout.dialog_add, null, false);
        builder.setView(view);
        // this is the Edit Text for name in the dialogue box
        final EditText namedEditText = view.findViewById(R.id.edit_name);

        // this is the EditText object for the quantity in the dialog box
        final EditText quantityEditText = view.findViewById(R.id.edit_quantity);
       // final CalendarView deliveryDateView = view.findViewById(R.id.calendar_view);
        //final GregorianCalendar calendar = new GregorianCalendar();

        //final means it will only refer to this object
        // use a boolean value to determine the jersey colour
        final Button colourButton = view.findViewById(R.id.button_id);
        if (jerseyColour){
            colourButton.setText("BLUE");
        }
        else{
            colourButton.setText("RED");
        }
        colourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(jerseyColour){
                    jerseyColour=false;
                    colourButton.setText("RED");
                    //colourButton.setEnabled(false);
                   // Snackbar snackbar = Snackbar.make(findViewById(R.id.coordinator_layout), "Item cleared", Snackbar.LENGTH_LONG);
                }
                else{
                    jerseyColour=true;
                    colourButton.setText("BLUE");
                   // colourButton.setEnabled(false);
                  //  Snackbar snackbar = Snackbar.make(findViewById(R.id.coordinator_layout), "Item cleared", Snackbar.LENGTH_LONG);
                }
            }
        });

        namedEditText.setText(mCurrentItem.getName());
        quantityEditText.setText(""+mCurrentItem.getQuantity());

        //TODO: populate the item with the values if it is an edit

        //if(isEdit){
         //   namedEditText.setText(mCurrentItem.getName());
        //    quantityEditText.setText(""+mCurrentItem.getQuantity());
            //deliveryDateView.setDate(mCurrentItem.getDeliveryDateTime());
      //  }

        //deliveryDateView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
         //   @Override
           // public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
            //    calendar.set(year, month, dayOfMonth);
            //}
       // });

        builder.setNegativeButton(android.R.string.cancel, null);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //runs when u click ok

                //TODO: Edit the mCurrentItem instead of making a new one if this is an edit
                String name = namedEditText.getText().toString();
                int quantity = Integer.parseInt(quantityEditText.getText().toString());

                //Set the values
                mCurrentItem.setName(name);
                mCurrentItem.setQuantity(quantity);

               // if(isEdit){
                //    mCurrentItem.setName(name);
                 //   mCurrentItem.setQuantity(quantity);
                   // mCurrentItem.setDeliveryDate(calendar);

                //}else{
                //    mCurrentItem = new Item(name, quantity);
                    //mItems.add(mCurrentItem);
                //}
                showCurrentItem();
            }
        });


        builder.create().show();
    }

    private void showCurrentItem() {
        mNameTextView.setText(mCurrentItem.getName());
        mQuantityTextView.setText(getString(R.string.quantity_format, mCurrentItem.getQuantity()));

        mImageView = findViewById(R.id.myImageView);

        if(jerseyColour){
            //TODO: Set the background image to red
            mImageView.setImageResource(R.drawable.red_jersey);
        }
        else{
            //TODO set the background image to blue
            mImageView.setImageResource(R.drawable.blue_jersey);
        }
       // mDateTextView.setText(getString(R.string.date_format, mCurrentItem.getDeliveryDateString()));
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

        switch(item.getItemId()){
            case R.id.action_reset:
               // mClearedItem = mCurrentItem;
                shoClearAllDialog();

                showCurrentItem();
                //TODO: Use a snackbar to allow a user to undo their action.
                Snackbar snackbar = Snackbar.make(findViewById(R.id.coordinator_layout), "Jersey Reset", Snackbar.LENGTH_LONG);
                //snackbar.setAction("Undo", new View.OnClickListener() {
                  //  @Override
                 //   public void onClick(View v) {
                        //TODO: do the undo
                       // mCurrentItem = mClearedItem;
                        showCurrentItem();
                   //     Snackbar.make(findViewById(R.id.coordinator_layout), "Item restored", Snackbar.LENGTH_SHORT).show();

                   // }
                //});


                snackbar.show();

                return true;
           // case R.id.action_search:
             //   showSearchDialog();
               // return true;

            case R.id.action_settings:
                startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                return true;
          //  case R.id.action_clear_all:
            //    shoClearAllDialog();
              //  return true;


        }

        return super.onOptionsItemSelected(item);
    }


    private void shoClearAllDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Reset");
        builder.setMessage("Are you sure you want to reset the jersey?");
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //mItems.clear();
                mCurrentItem = new Item();
                jerseyColour=true;
                showCurrentItem();
            }
        });
        builder.create().show();
    }
}
