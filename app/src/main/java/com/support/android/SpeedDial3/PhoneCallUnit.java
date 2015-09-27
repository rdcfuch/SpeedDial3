package com.support.android.SpeedDial3;

/**
 * Created by rdcfuch on 4/26/2015.
 */
public class PhoneCallUnit implements java.io.Serializable{
    private String phoneAlias;  // alias for the phone call, e.g FC USA 2G
    private String phoneNumber; // the actual number , e.g. +18777485444,37225459,#,,#
    private int simSlot; // 0 use SIM 1, 1 use SIM 2

    public PhoneCallUnit(){
        this.phoneAlias = "NULL";
        this.phoneNumber = "NULL";
        this.simSlot = 0;
    }

    public PhoneCallUnit(String _phoneAlias, String _phoneNumber, int _simSlot) {
        this.phoneAlias = _phoneAlias;
        this.phoneNumber = _phoneNumber;
        this.simSlot = _simSlot;

    }

    public String getPhoneAlias(){
        return this.phoneAlias.toString();
    }

    public String getPhoneNumber(){
        return this.phoneNumber.toString();
    }

    public int getSimSlot(){
        return this.simSlot;
    }

    public void setPhoneAlias(String pA){
        this.phoneAlias=pA;
    }

    public void setPhoneNumber(String pN){
        this.phoneNumber=pN;
    }

    public void setSimSlot (int sL){
        this.simSlot=sL;
    }
}
