package com.support.android.SpeedDial3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.AppCompatEditText;


import java.io.IOException;

public class ConfigPhoneCall extends Activity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;


//    private TextView title,aliasTV;
    private AppCompatTextView title,aliasTV;
//    private EditText aliasEdit;
    private EditText aliasEdit;
    private EditText phoneEdit;
    private Intent myIntent;
    private Bundle myBl;
    private int simSlot;
    private int myIndex;
    private PhoneCallUnit mPCU;
    private Base64en_decode myendecoder;
    private TextInputLayout aliasTIL,phoneTIL;
    private Typeface tf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_config_layout);

        myIntent = getIntent();
        myBl = myIntent.getExtras();

        myIndex=myBl.getInt("INDEX");

        myendecoder = new Base64en_decode();

        try {

            mPCU = myendecoder.readObject(myBl.getString("OBJECT"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



        //final View controlsView = findViewById(R.id.fullscreen_content_controls);
        //final View contentView = findViewById(R.id.fullscreen_content);

        tf = Typeface.createFromAsset(this.getAssets(), "fonts/RobotoThin.ttf");

        title = (AppCompatTextView) findViewById(R.id.config_title);
        title.setTypeface(tf);

        aliasTIL= (TextInputLayout) findViewById(R.id.SDAliasLayout);
        phoneTIL=(TextInputLayout) findViewById(R.id.phoneLayout);
        phoneTIL.setErrorEnabled(true);

        aliasEdit = (EditText) findViewById(R.id.alias_edit);
        aliasEdit.setText(this.mPCU.getPhoneAlias());
        aliasEdit.setSelection(aliasEdit.getText().length());

        phoneEdit = (EditText) findViewById(R.id.phone_edit);
        phoneEdit.setText(this.mPCU.getPhoneNumber());

        aliasEdit.setTypeface(tf);
        phoneEdit.setTypeface(tf);


        simSlot = this.mPCU.getSimSlot();


        RadioGroup simRadioGrp = (RadioGroup) findViewById(R.id.sim_Radio_Group);
        RadioButton sim1 = (RadioButton) findViewById(R.id.radio_sime_1);
        RadioButton sim2 = (RadioButton) findViewById(R.id.radio_sime_2);

        sim1.setTypeface(tf);
        sim2.setTypeface(tf);

        //??radiobutton???
        if (simSlot == 0) {
            sim1.setChecked(true);
            sim2.setChecked(false);
        } else if (simSlot == 1) {
            sim1.setChecked(false);
            sim2.setChecked(true);
        }


        simRadioGrp.setOnCheckedChangeListener(new setOnCheckedChangeListener());

        Button ok_B = (Button) findViewById(R.id.ok_btn);
        Button cancel_B = (Button) findViewById(R.id.cancel_btn);
        Button delete_B = (Button)findViewById(R.id.delete_btn);


        ok_B.setOnClickListener(new configOnClickListener());
        cancel_B.setOnClickListener(new configOnClickListener());
        delete_B.setOnClickListener(new configOnClickListener());

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.





    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
    }

    public class configOnClickListener implements View.OnClickListener {
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.ok_btn:

                    mPCU.setPhoneAlias(aliasEdit.getText().toString());
                    mPCU.setPhoneNumber(phoneEdit.getText().toString());
                    mPCU.setSimSlot(simSlot);

                    myBl.putInt("INDEX",myIndex);

                    String sendToIntent_PCU= "";
                    try {
                        sendToIntent_PCU = myendecoder.base64encode(mPCU);
                    } catch (IOException e) {


                        e.printStackTrace();
                    }

                    myBl.putString("OBJECT",sendToIntent_PCU);

                    myIntent.putExtras(myBl);
                    setResult(Activity.RESULT_OK,myIntent);
                    finish();

                    break;

                case R.id.cancel_btn:
                    setResult(Activity.RESULT_OK,myIntent);
                    finish();
                    break;

                case R.id.delete_btn:
                    myBl.putInt("INDEX",myIndex);
                    myIntent.putExtras(myBl);
                    setResult(5,myIntent);
                    finish();
                    break;

            }
        }


    }

    public class setOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener{

        public void onCheckedChanged(RadioGroup group, int checkedId) {
            int radio_bnt_id=group.getCheckedRadioButtonId();
            switch (radio_bnt_id){
                case R.id.radio_sime_1:
                    simSlot=0;
                    break;

                case R.id.radio_sime_2:
                    simSlot=1;
                    break;

            }
        }

    }

}


