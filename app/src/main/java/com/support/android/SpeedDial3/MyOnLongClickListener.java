package com.support.android.SpeedDial3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by RDCFUCH on 9/25/2015.
 */
public class MyOnLongClickListener implements View.OnLongClickListener {

    private Context mContext;
    private Activity mActivity;
    private PreferenceSetting mSetting;
    private PhoneCallUnit _phoneUnit;
    private int listIndex = 0;

    public MyOnLongClickListener(Context mCtx) {
        super();
        this.mContext = mCtx;

    }

    MyOnLongClickListener(Context _mCon, PhoneCallUnit inputObject, int _inex) {
        super();
        this.mContext = _mCon;
        this._phoneUnit = inputObject;
        this.listIndex = _inex;
    }

    @Override
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.Phone_Call_card:

                try {
                    this.editPhoneSetting(listIndex, this._phoneUnit);
                    Toast.makeText(mContext, "You can config " + _phoneUnit.getPhoneAlias(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(mContext, "????:(", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                return true;

            default: return false;

        }


    }



    public void editPhoneSetting(int _index,PhoneCallUnit _inputPCU) throws IOException {
        Bundle my_bl=new Bundle();
        Base64en_decode endecode = new Base64en_decode();
        String sendToIntent_PCU=endecode.base64encode(_inputPCU);
        my_bl.putInt("INDEX",_index);
        my_bl.putString("OBJECT",sendToIntent_PCU);


        Intent configIntent = new Intent();
        configIntent.setClass(mContext, ConfigPhoneCall.class);
        configIntent.putExtras(my_bl);
        //????activity??startActivityForResult
        ((Activity)mContext).startActivityForResult(configIntent,1);
    }
}
