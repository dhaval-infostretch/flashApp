package com.example.flashapp.flashapp;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout rl = null;
    RelativeLayout buttonRl = null;
    Boolean isButtonsVisible = true;
    TextView tv = null;
    String[] options = {"Hidden", "Visible", "Cancel"};
    View toDisplayInDialog = null;
    AlertDialog.Builder builder = null;
    AlertDialog.Builder builderUserChoice = null;
    String selectedColor = "";

    /**
     * @author MOHIT SARANG
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
            mActionBar.hide();
        }
        builderUserChoice = new AlertDialog.Builder(MainActivity.this);
        builderUserChoice.setTitle("Startup Button Visibility").setItems(options, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = prefs.edit();
                switch (which) {
                    case 0:
                        editor.putString("userChoice", "Hidden");
                        buttonRl.setVisibility(View.GONE);
                        break;
                    case 1:
                        editor.putString("userChoice", "Visible");
                        buttonRl.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        editor.putString("userChoice", "Cancel");
                        break;
                }
                editor.commit();
                if(!prefs.contains("defaultBackground")){
                    builder.show();
                }
            }
        });
        toDisplayInDialog = getLayoutInflater().inflate(R.layout.radiogroup, null);
        builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(toDisplayInDialog);
        builder.setPositiveButton("Set Startup Color",
            new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    RadioGroup radioBtnGrp = (RadioGroup) toDisplayInDialog.findViewById(R.id.radioGroup);
                    int radioGroupId = radioBtnGrp.getCheckedRadioButtonId();
                    RadioButton myCheckedButton = (RadioButton) toDisplayInDialog.findViewById(radioGroupId);
                    int index = radioBtnGrp.indexOfChild(myCheckedButton);
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = prefs.edit();
                    switch (index) {
                        case 0:
                            rl.setBackgroundColor(getResources().getColor(R.color.black));
                            editor.putString("defaultBackground", "black");
                            break;
                        case 1:
                            rl.setBackgroundColor(getResources().getColor(R.color.white));
                            editor.putString("defaultBackground", "white");
                            break;
                        case 2:
                            rl.setBackgroundColor(getResources().getColor(R.color.red));
                            editor.putString("defaultBackground", "red");
                            break;
                        case 3:
                            rl.setBackgroundColor(getResources().getColor(R.color.yellow));
                            editor.putString("defaultBackground", "yellow");
                            break;
                        case 4:
                            rl.setBackgroundColor(getResources().getColor(R.color.green));
                            editor.putString("defaultBackground", "green");
                            break;

                    }
                    editor.commit();
                    dialog.dismiss();
                }
            });
        builder.setNegativeButton("Cancel",
            new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }
            });

        rl = (RelativeLayout) findViewById(R.id.mainRelLayout);
        buttonRl = (RelativeLayout) findViewById(R.id.buttonRelLayout);
        tv = (TextView) findViewById(R.id.textView);
        rl.setOnClickListener(this);
        final Button white = (Button) findViewById(R.id.button);
        white.setOnClickListener(this);
        final Button black = (Button) findViewById(R.id.button2);
        black.setOnClickListener(this);
        final Button red = (Button) findViewById(R.id.button3);
        red.setOnClickListener(this);
        final Button yellow = (Button) findViewById(R.id.button4);
        yellow.setOnClickListener(this);
        final Button green = (Button) findViewById(R.id.button5);
        green.setOnClickListener(this);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(prefs.contains("defaultBackground")){
            setBackgroundColor(prefs.getString("defaultBackground", null));
        }
        if(prefs.getString("userChoice", "").equals("Hidden")){
            if(buttonRl.getVisibility() != View.GONE){
                buttonRl.setVisibility(View.GONE);
            }
            if(!prefs.contains("defaultBackground")){
                builder.show();
            }
        } else if(prefs.getString("userChoice", "").equals("Visible")){
            buttonRl.setVisibility(View.VISIBLE);
            if(!prefs.contains("defaultBackground")){
                builder.show();
            }
        }
        if((prefs.getString("userChoice", "").equals("Cancel")) || !prefs.contains("userChoice")){
            builderUserChoice.show();
        }
        if (savedInstanceState != null && savedInstanceState.containsKey("selectedColor")){
            setBackgroundColor(savedInstanceState.getString("selectedColor"));
        }
    }

    /**
     * @author MOHIT SARANG
     * @param savedInstance
     */
    @Override
    protected void onSaveInstanceState(Bundle savedInstance) {
        super.onSaveInstanceState(savedInstance);
        savedInstance.putString("selectedColor", selectedColor);
    }

    /**
     * @author MOHIT SARANG
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.layout.menu, menu);
        return true;
    }

    /**
     * @author MOHIT SARANG
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.btnVisibility:
                builderUserChoice.show();
                return true;

            case R.id.startupColor:
                builder.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * @author MOHIT SARANG
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button: {
                rl.setBackgroundColor(getResources().getColor(R.color.white));
                buttonRl.setVisibility(View.GONE);
                selectedColor = "white";
                isButtonsVisible = false;
                break;
            }
            case R.id.button2: {
                rl.setBackgroundColor(getResources().getColor(R.color.black));
                buttonRl.setVisibility(View.GONE);
                selectedColor = "black";
                isButtonsVisible = false;
                break;
            }
            case R.id.button3: {
                rl.setBackgroundColor(getResources().getColor(R.color.red));
                buttonRl.setVisibility(View.GONE);
                selectedColor = "red";
                isButtonsVisible = false;
                break;
            }
            case R.id.button4: {
                rl.setBackgroundColor(getResources().getColor(R.color.yellow));
                selectedColor = "yellow";
                buttonRl.setVisibility(View.GONE);
                isButtonsVisible = false;
                break;
            }
            case R.id.button5: {
                rl.setBackgroundColor(getResources().getColor(R.color.green));
                selectedColor = "green";
                buttonRl.setVisibility(View.GONE);
                isButtonsVisible = false;
                break;
            }
            case R.id.mainRelLayout: {
                if (isButtonsVisible) {
                    buttonRl.setVisibility(View.GONE);
                    tv.setVisibility(View.GONE);
                } else {
                    buttonRl.setVisibility(View.VISIBLE);
                }
                isButtonsVisible = !isButtonsVisible;
                android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
                if(isButtonsVisible){
                    mActionBar.show();
                } else {
                    mActionBar.hide();
                }

                break;
            }

            //.... etc
        }
    }

    /**
     * @author MOHIT SARANG
     * @param colorName
     */
    public void setBackgroundColor(String colorName){
        switch (colorName) {
            case "white": {
                rl.setBackgroundColor(getResources().getColor(R.color.white));
                selectedColor = "white";
                break;
            }
            case "black": {
                rl.setBackgroundColor(getResources().getColor(R.color.black));
                selectedColor = "black";
                break;
            }
            case "red": {
                rl.setBackgroundColor(getResources().getColor(R.color.red));
                selectedColor = "red";
                break;
            }
            case "yellow": {
                rl.setBackgroundColor(getResources().getColor(R.color.yellow));
                selectedColor = "yellow";
                break;
            }
            case "green": {
                rl.setBackgroundColor(getResources().getColor(R.color.green));
                selectedColor = "green";
                break;
            }

            //.... etc
        }
    }
}
