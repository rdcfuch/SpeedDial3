package com.support.android.SpeedDial3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.IOException;

public class AddNewPhoneCall extends AppCompatActivity {

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

    private TextView title,aliasTV;
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

        setContentView(R.layout.activity_add_new_phone_call);

        //final View controlsView = findViewById(R.id.fullscreen_content_controls);
        //final View contentView = findViewById(R.id.fullscreen_content);

        myIntent = getIntent();
        myBl = new Bundle();
        myendecoder = new Base64en_decode();


        mPCU = new PhoneCallUnit();
        mPCU.setPhoneAlias("Phone Name");
        mPCU.setPhoneNumber("[0-9],p#");
        mPCU.setSimSlot(0);

        tf = Typeface.createFromAsset(this.getAssets(), "fonts/RobotoThin.ttf");

        title = (TextView)findViewById(R.id.adding_title);
        title.setTypeface(tf);

//        aliasTV = (TextView)findViewById(R.id.SD_Alias);
//        aliasTV.setTypeface(tf);
//
//        aliasTV = (TextView)findViewById(R.id.SD_number);
//        aliasTV.setTypeface(tf);
//
//
//        aliasTV = (TextView)findViewById(R.id.SD_sim);
//        aliasTV.setTypeface(tf);


        aliasTIL= (TextInputLayout) findViewById(R.id.SDAliasLayout);
        phoneTIL=(TextInputLayout) findViewById(R.id.phoneLayout);
        phoneTIL.setErrorEnabled(true);
        aliasEdit = (EditText) findViewById(R.id.alias_edit);
        phoneEdit = (EditText) findViewById(R.id.phone_edit);
        phoneEdit.setError("Please put phone numbers [0-9] p #");
        aliasEdit.setTypeface(tf);
        phoneEdit.setTypeface(tf);




        aliasEdit.setTypeface(tf);
        phoneEdit.setTypeface(tf);
        simSlot = 0;

        RadioGroup simRadioGrp = (RadioGroup) findViewById(R.id.sim_Radio_Group);
        RadioButton sim1 = (RadioButton) findViewById(R.id.radio_sime_1);
        RadioButton sim2 = (RadioButton) findViewById(R.id.radio_sime_2);
        sim1.setChecked(true);
        simRadioGrp.setOnCheckedChangeListener(new setOnCheckedChangeListener());

        sim1.setTypeface(tf);
        sim2.setTypeface(tf);

        Button ok_B = (Button) findViewById(R.id.ok_btn);
        Button cancel_B = (Button) findViewById(R.id.cancel_btn);

        ok_B.setOnClickListener(new configOnClickListener());
        cancel_B.setOnClickListener(new configOnClickListener());

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.

    }


    public class configOnClickListener implements View.OnClickListener {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ok_btn:

                    mPCU.setPhoneAlias(aliasEdit.getText().toString());
                    mPCU.setPhoneNumber(phoneEdit.getText().toString());
                    mPCU.setSimSlot(simSlot);

//                    //?????100 Index,
//                    myBl.putInt("INDEX",100);
                    if (mPCU.getPhoneAlias().equalsIgnoreCase("Phone Name")&&mPCU.getPhoneNumber().equalsIgnoreCase("[0-9],p#") ) {
                        setResult(Activity.RESULT_CANCELED, myIntent);
                        finish();
                    } else {
                        String sendToIntent_PCU = "";
                        try {
                            sendToIntent_PCU = myendecoder.base64encode(mPCU);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        myBl.putString("OBJECT", sendToIntent_PCU);

                        myIntent.putExtras(myBl);
                        setResult(Activity.RESULT_OK, myIntent);
                        finish();
                    }
                    break;

                case R.id.cancel_btn:
                    setResult(Activity.RESULT_CANCELED, myIntent);
                    finish();
                    break;
            }
        }


    }

    public class setOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        public void onCheckedChanged(RadioGroup group, int checkedId) {
            int radio_bnt_id = group.getCheckedRadioButtonId();
            switch (radio_bnt_id) {
                case R.id.radio_sime_1:
                    simSlot = 0;
                    break;

                case R.id.radio_sime_2:
                    simSlot = 1;
                    break;

            }
        }

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
    }
}

