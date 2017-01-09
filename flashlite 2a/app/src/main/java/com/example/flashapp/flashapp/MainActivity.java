package com.example.flashapp.flashapp;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout rl = null;
    RelativeLayout buttonRl = null;
    Boolean isButtonsVisible = true;
    TextView tv = null;
    String[] options = {"Hidden","Visible","Cancel"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Startup Button Visibility").setItems(options, new DialogInterface.OnClickListener() {
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
            }
        });

        rl = (RelativeLayout)findViewById(R.id.mainRelLayout);
        buttonRl = (RelativeLayout)findViewById(R.id.buttonRelLayout);
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

        if(prefs.getString("userChoice", null).equals("Hidden")){
            if(buttonRl.getVisibility() != View.GONE){
                buttonRl.setVisibility(View.GONE);
            }
        } else if(prefs.getString("userChoice", null).equals("Visible")){
            buttonRl.setVisibility(View.VISIBLE);
        }
        if(prefs.getString("userChoice", null).equals("Cancel")){
            builder.show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.button: {
                rl.setBackgroundColor(getResources().getColor(R.color.white));
                buttonRl.setVisibility(View.GONE);
                isButtonsVisible = false;
                break;
            }
            case  R.id.button2: {
                rl.setBackgroundColor(getResources().getColor(R.color.black));
                buttonRl.setVisibility(View.GONE);
                isButtonsVisible = false;
                break;
            }
            case  R.id.button3: {
                rl.setBackgroundColor(getResources().getColor(R.color.red));
                buttonRl.setVisibility(View.GONE);
                isButtonsVisible = false;
                break;
            }
            case  R.id.button4: {
                rl.setBackgroundColor(getResources().getColor(R.color.yellow));
                buttonRl.setVisibility(View.GONE);
                isButtonsVisible = false;
                break;
            }
            case  R.id.button5: {
                rl.setBackgroundColor(getResources().getColor(R.color.green));
                buttonRl.setVisibility(View.GONE);
                isButtonsVisible = false;
                break;
            }
            case R.id.mainRelLayout:{
                if(isButtonsVisible){
                    buttonRl.setVisibility(View.GONE);
                    tv.setVisibility(View.GONE);
                } else {
                    buttonRl.setVisibility(View.VISIBLE);
                }
                isButtonsVisible = !isButtonsVisible;
                break;
            }

            //.... etc
        }
    }
}
