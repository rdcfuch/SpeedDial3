package com.support.android.SpeedDial3;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rdcfuch on 4/26/2015.
 */
public class PreferenceSetting {
    public SharedPreferences settings;
    public SharedPreferences.Editor editor;
    public String recordLength="RecordLength";
    public int list_length = 7;
    public String[] phone_name_list = new String[list_length];

    public PreferenceSetting(String _settingName,Context _mContext) {

//?????????sharedPreference???????MainActivity?????preference??????????????Settings",????????
//        1?. public SharedPreferences getPreferences (int mode)
//        ??Activity??????????Activity???Preference????????xml???????????Activity????????Activity??????????Activity?
//        2?. public SharedPreferences getSharedPreferences (String name, int mode)
//        ??Activity???ContextWrapper???????Activity????????????????????????????name???????????
//        3?. public static SharedPreferences getDefaultSharedPreferences (Context context)
//        PreferenceManager????????PreferenceActivity?????????????????????Android??????PreferenceActivity??????????????


        settings = _mContext.getSharedPreferences(_settingName, _mContext.MODE_PRIVATE);
        editor = settings.edit();
        if(getRecordNumber()==0){
        editor.putString("0","FC USA 2G|+18777485444,,37225459#,#|0");
        editor.putInt(this.recordLength,1);
        editor.commit();
      }
    }

    public void setRecordNumber(int phone_list_number){
        editor.putInt(this.recordLength,phone_list_number);
        editor.commit();
    }

    public int getRecordNumber(){
        return this.settings.getInt(this.recordLength,0);
    }

    public List<PhoneCallUnit> getRecordList(){

        List listFromSettingFile = new ArrayList<PhoneCallUnit>();
        String record_long_string="NULL";

        int record_length=this.getRecordNumber();

        for (int i=0;i<record_length;i++){
            record_long_string = this.settings.getString(String.valueOf(i),"NULL");
            if (!record_long_string.equalsIgnoreCase("NULL")){
                //Pattern myPattern = Pattern.compile("\\|");
                //String [] tempList = myPattern.split(record_long_string);

                String[] tempList = record_long_string.split("\\|");

                //!!!!???????????????????List??
                PhoneCallUnit tempPCU = new PhoneCallUnit();
                Log.d("BBBBBB   ", tempList[0]);
                Log.d("BBBBBB   ", tempList[1]);
                Log.d("BBBBBB   ", tempList[2]);
                tempPCU.setPhoneAlias(tempList[0]);
                tempPCU.setPhoneNumber(tempList[1]);
                tempPCU.setSimSlot(Integer.parseInt(tempList[2]));
                Log.d("ffffuck  ", tempPCU.getPhoneAlias());
                listFromSettingFile.add(tempPCU);
                listFromSettingFile.set(i,tempPCU);
            }
        }
        this.printList(listFromSettingFile);
        return listFromSettingFile;
    }

    public void putRecordList (List<PhoneCallUnit> inputList){

        this.printList(inputList);
        this.setRecordNumber(inputList.size());
        PhoneCallUnit tempPCU=new PhoneCallUnit();

        for(int i=0;i<inputList.size();i++){
            tempPCU=inputList.get(i);
            String putLongString="";
            putLongString+=tempPCU.getPhoneAlias();
            putLongString+="|";
            putLongString+=tempPCU.getPhoneNumber();
            putLongString+="|";
            putLongString+= String.valueOf(tempPCU.getSimSlot());
            this.editor.putString(String.valueOf(i),putLongString);
            this.editor.commit();
        }
    }

    public void initSetting() {

        //for (int i = 0; i < list_length; i++) {
            //String temp_phone_name = "phone_" + String.valueOf(1) + "_name";
            //System.out.println(temp_phone_name);
            String phone_1 = this.settings.getString("phone_1_name", "+++");
        //}
    }

    public String getItem(String _input){
        String item_value;
        item_value=settings.getString(_input,"NULL");
        return item_value;
    }

    //?????object
    public void writeItem(int index,PhoneCallUnit unit) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(unit);
        String unit_Base64_encoded = new String(Base64.encode(byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
        this.editor.putString(String.valueOf(index),unit_Base64_encoded);
    }
    //?????objiect
    public PhoneCallUnit readItem(int index,PhoneCallUnit unit) throws IOException, ClassNotFoundException {
        String itemString = this.settings.getString(String.valueOf(index), "NULL");
        byte[] itemBytes= Base64.decode(itemString.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(itemBytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        PhoneCallUnit _phoneItem=(PhoneCallUnit) objectInputStream.readObject();
        return _phoneItem;
    }


    public String base64encode(PhoneCallUnit unit) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(unit);
        String unit_Base64_encoded = new String(Base64.encode(byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
        return unit_Base64_encoded;
    }


    //????list????????listView????????
    public List<PhoneCallUnit> getList(){
        PhoneCallUnit u1 = new PhoneCallUnit("FC USA 2G","+18777485444,,37225459#,#",0);
        PhoneCallUnit u2 = new PhoneCallUnit("89921 T Free","+18772373651",0);
        PhoneCallUnit u3 = new PhoneCallUnit("SF PET VISUALCOM","+14087505800,,,2444349#,",0);
        PhoneCallUnit u4 = new PhoneCallUnit("FC 4","44444444444",0);
//        PhoneCallUnit u5 = new PhoneCallUnit("FC 5","55555555555",0);

        List _mylist = new ArrayList<PhoneCallUnit>();

        _mylist.add(u1);
        _mylist.add(u2);
        _mylist.add(u3);
        _mylist.add(u4);
//        _mylist.add(u5);

        return _mylist;

    }

        public void printList(List<PhoneCallUnit> tempList){
            for (int i=0;i<tempList.size();i++){
                Log.d("!!!!!!!!!!!", String.valueOf(i) + " : " + tempList.get(i).getPhoneAlias());
            }
        }



}

