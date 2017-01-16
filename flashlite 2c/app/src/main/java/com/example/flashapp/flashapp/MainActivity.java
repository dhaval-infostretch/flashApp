package com.example.flashapp.flashapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout rl = null;
    LinearLayout buttonRl = null;
    Boolean isButtonsVisible = true;
    TextView tv = null;
    String[] options = {"Hidden", "Visible", "Cancel"};
    View toDisplayInDialog = null;
    AlertDialog.Builder builder = null;
    AlertDialog.Builder builderUserChoice = null;
    Boolean isSetStartupDialogOpen = false;
    Boolean isBtnVisibleOpen = false;
    String selectedColor = "";
    ProgressBar progressBar;
    sosActivity signal;

    RadioGroup radioBtnGrp;
    int radioGroupId;
    RadioButton myCheckedButton;
    int index = -1;
    /**
     * @author MOHIT SARANG
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT > 16) {
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
                isBtnVisibleOpen = false;
                if(!prefs.contains("defaultBackground")){
                    builder.show();
                }
            }
        });

        toDisplayInDialog = getLayoutInflater().inflate(R.layout.radiogroup, null);
        builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(toDisplayInDialog);
        radioBtnGrp = (RadioGroup) toDisplayInDialog.findViewById(R.id.radioGroup);
        builder.setPositiveButton("Set Startup Color",
            new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    radioGroupId = radioBtnGrp.getCheckedRadioButtonId();
                    myCheckedButton = (RadioButton) toDisplayInDialog.findViewById(radioGroupId);
                    index = radioBtnGrp.indexOfChild(myCheckedButton);
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
                    isSetStartupDialogOpen = false;
                    dialog.dismiss();
                }
            });
        builder.setNegativeButton("Cancel",
            new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    isSetStartupDialogOpen = false;
                    dialog.dismiss();

                }
            });

        rl = (RelativeLayout) findViewById(R.id.mainRelLayout);
        buttonRl = (LinearLayout) findViewById(R.id.buttonRelLayout);
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
        final Button sos = (Button) findViewById(R.id.sos);
        sos.setOnClickListener(this);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
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
            isBtnVisibleOpen = true;
        }
        if (savedInstanceState != null){
            if(savedInstanceState.containsKey("selectedColor")){
                setBackgroundColor(savedInstanceState.getString("selectedColor"));
            }
            if(savedInstanceState.containsKey("getCheckedColor")  && savedInstanceState.getInt("getCheckedColor") != -1){
                ((RadioButton)radioBtnGrp.getChildAt(savedInstanceState.getInt("getCheckedColor"))).setChecked(true);
            }
            if(savedInstanceState.containsKey("isBtnVisibleOpen") && savedInstanceState.getBoolean("isBtnVisibleOpen")){
                builderUserChoice.show();
                isBtnVisibleOpen = true;
            } else if(savedInstanceState.containsKey("isSetStartupDialogOpen") && savedInstanceState.getBoolean("isSetStartupDialogOpen")){
                toDisplayInDialog = null;
                toDisplayInDialog = getLayoutInflater().inflate(R.layout.radiogroup, null);
                builder.setView(toDisplayInDialog);
                builder.show();
                isSetStartupDialogOpen = true;
            }
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
        if(isSetStartupDialogOpen){
            savedInstance.putBoolean("isSetStartupDialogOpen", true);
        } else {
            savedInstance.putBoolean("isSetStartupDialogOpen", false);
        }
        if(isBtnVisibleOpen){
            savedInstance.putBoolean("isBtnVisibleOpen", true);
        } else {
            savedInstance.putBoolean("isBtnVisibleOpen", false);
        }
        radioGroupId = radioBtnGrp.getCheckedRadioButtonId();
        myCheckedButton = (RadioButton) toDisplayInDialog.findViewById(radioGroupId);
        index = radioBtnGrp.indexOfChild(myCheckedButton);
        savedInstance.putInt("getCheckedColor",index);
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
                isBtnVisibleOpen = true;
                return true;

            case R.id.startupColor:
                toDisplayInDialog = null;
                toDisplayInDialog = getLayoutInflater().inflate(R.layout.radiogroup, null);
                builder.setView(toDisplayInDialog);
                builder.show();
                isSetStartupDialogOpen = true;
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void stopSOS(){
        if(signal!=null){
            signal.cancel(true);
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
                stopSOS();
                break;
            }
            case R.id.button2: {
                rl.setBackgroundColor(getResources().getColor(R.color.black));
                buttonRl.setVisibility(View.GONE);
                selectedColor = "black";
                isButtonsVisible = false;
                stopSOS();
                break;
            }
            case R.id.button3: {
                rl.setBackgroundColor(getResources().getColor(R.color.red));
                buttonRl.setVisibility(View.GONE);
                selectedColor = "red";
                isButtonsVisible = false;
                stopSOS();
                break;
            }
            case R.id.button4: {
                rl.setBackgroundColor(getResources().getColor(R.color.yellow));
                selectedColor = "yellow";
                buttonRl.setVisibility(View.GONE);
                isButtonsVisible = false;
                stopSOS();
                break;
            }
            case R.id.button5: {
                rl.setBackgroundColor(getResources().getColor(R.color.green));
                selectedColor = "green";
                buttonRl.setVisibility(View.GONE);
                isButtonsVisible = false;
                stopSOS();
                break;
            }

            case R.id.sos:
                signal=new sosActivity();
                signal.execute("...---...");
                break;

            case R.id.mainRelLayout: {
                if (isButtonsVisible) {
                    buttonRl.setVisibility(View.GONE);
                    tv.setVisibility(View.GONE);
                } else {
                    buttonRl.setVisibility(View.VISIBLE);
                    stopSOS();
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

    public class sosActivity extends AsyncTask<String, Integer, Void> {

        int time =0;
        int count = 0;
        int progress=0;
        @Override
        protected void onPreExecute(){
            setBackgroundColor(selectedColor);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
        }
        @Override
        protected Void doInBackground(String... params) {
            progress= params[0].length();
            while (!isCancelled()){
                for(int i=0; i< params[0].length(); i++){
                    if(isCancelled()){
                        break;
                    }
                    if(params[0].charAt(i)=='.'){
                        time = 500;
                    }
                    else if (params[0].charAt(i)=='-'){
                        time=1000;
                    }
                    try{
                        Thread.sleep(time);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }

                    publishProgress(Color.rgb(0,0,255));

                    try{
                        Thread.sleep(time);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }

                    switch (selectedColor) {
                        case "white": {
                            publishProgress(getResources().getColor(R.color.white));
                            break;
                        }
                        case "black": {
                            publishProgress(getResources().getColor(R.color.black));
                            break;
                        }
                        case "red": {
                            publishProgress(getResources().getColor(R.color.red));
                            break;
                        }
                        case "yellow": {
                            publishProgress(getResources().getColor(R.color.yellow));
                            break;
                        }
                        case "green": {
                            publishProgress(getResources().getColor(R.color.green));
                            break;
                        }

                        //.... etc
                    }

                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values){
            rl.setBackgroundColor(values[0]);
            count=++count%(2*progress);
            progressBar.setProgress((1+count)*50/progress);
        }

        @Override
        protected void onPostExecute(Void Result){
            progressBar.setVisibility(View.GONE);
            setBackgroundColor(selectedColor);
        }

        @Override
        protected void onCancelled(){
            super.onCancelled();
            setBackgroundColor(selectedColor);
        }


    }
    @Override
    protected void onStop(){
        super.onPause();
        if(signal!=null)
        {
            signal.cancel(true);
        }
    }

}
