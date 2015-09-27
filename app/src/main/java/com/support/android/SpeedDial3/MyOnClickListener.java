package com.support.android.SpeedDial3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by rdcfuch on 4/24/2015.
 */

public class MyOnClickListener implements OnClickListener {
    private Context mContext;
    private Activity mActivity;
    private PreferenceSetting mSetting;
    private PhoneCallUnit _phoneUnit;
    private int listIndex = 0;

    public MyOnClickListener(Context _mCon) {
        super();
        this.mContext = _mCon;
        //mSetting=new PreferenceSetting(_mCon);
        //this.mActivity=_mAct;
        //mSetting=new PreferenceSetting(_mCon);
    }

    MyOnClickListener(Context _mCon, PhoneCallUnit inputObject, int _inex) {
        super();
        this.mContext = _mCon;
        this._phoneUnit = inputObject;
        this.listIndex = _inex;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.Phone_call_Name:
                //Toast.makeText(mContext, "phone call name clicked", Toast.LENGTH_SHORT).show();
                break;


//            case R.id.Phone_setup_button:
//                try {
//                    this.editPhoneSetting(listIndex, this._phoneUnit);
//                    Toast.makeText(mContext, "You can config "+_phoneUnit.getPhoneAlias(), Toast.LENGTH_SHORT).show();
//                } catch (IOException e) {
//                    Toast.makeText(mContext, "????:(", Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();
//                }
//                break;
            case R.id.Phone_Call_card:
                Toast.makeText(mContext, "Calling " + this._phoneUnit.getPhoneNumber() + " on sim " + String.valueOf(_phoneUnit.getSimSlot() + 1), Toast.LENGTH_SHORT).show();
                triggerPhoneCall(this._phoneUnit.getPhoneNumber());
                break;

            case R.id.addPhoneNumFab:
                Toast.makeText(mContext, "You can add A New Phone Call now", Toast.LENGTH_SHORT).show();
                addPhoneCall();
                break;

            case R.id.appbar:
                Intent intent = new Intent(mContext, CheeseDetailActivity.class);
                intent.putExtra(CheeseDetailActivity.EXTRA_NAME, "testAAAAAAA");

                mContext.startActivity(intent);
                break;

        }


    }


    public void triggerPhoneCall(String phoneNumber) {
        Intent phoneIntent;

        phoneIntent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNumber));
//        ??
        // ???1, set "0" ????
        String[] dualSimTypes = {"subscription", "Subscription",
                "com.android.phone.extra.slot",
                "phone", "com.android.phone.DialingMode",
                "simId", "simnum", "phone_type",
                "simSlot"};

        for (int i = 0; i < dualSimTypes.length; i++) {
            phoneIntent.putExtra(dualSimTypes[i], 0);
        }
        this.mContext.startActivity(phoneIntent);
    }

    public void addPhoneCall() {
        Intent addPhoneIntent;
        addPhoneIntent = new Intent(mContext, AddNewPhoneCall.class);
        ((Activity) mContext).startActivityForResult(addPhoneIntent, 2);
    }


    public void editPhoneSetting(int _index, PhoneCallUnit _inputPCU) throws IOException {
        Bundle my_bl = new Bundle();
        Base64en_decode endecode = new Base64en_decode();
        String sendToIntent_PCU = endecode.base64encode(_inputPCU);
        my_bl.putInt("INDEX", _index);
        my_bl.putString("OBJECT", sendToIntent_PCU);


        Intent configIntent = new Intent();
        configIntent.setClass(mContext, ConfigPhoneCall.class);
        configIntent.putExtras(my_bl);
        //????activity??startActivityForResult
        ((Activity) mContext).startActivityForResult(configIntent, 1);
    }


//    public String base64encode(PhoneCallUnit unit) throws IOException {
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
//        objectOutputStream.writeObject(unit);
//        String unit_Base64_encoded = new String(Base64.encode(byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
//        return unit_Base64_encoded;
//    }


}