package com.support.android.SpeedDial3;

import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by rdcfuch on 4/30/2015.
 */
public class Base64en_decode {

    private SharedPreferences settings ;
    private SharedPreferences.Editor editor;

    public Base64en_decode(SharedPreferences _set){
        this.settings=_set;
        editor=this.settings.edit();

    }

    public Base64en_decode(){

    }

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

    public PhoneCallUnit readObject(String _inputString) throws IOException, ClassNotFoundException {
        byte[] itemBytes= Base64.decode(_inputString, Base64.DEFAULT);
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

}
