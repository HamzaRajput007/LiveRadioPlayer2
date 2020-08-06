package com.example.liveradioplayer;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefHelper {

    Context context;
    SharedPreferences sharedPreferences;

    public SharedPrefHelper(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("LiveRadioSharedPres" , Context.MODE_PRIVATE);
    }

    public void putToSharedPrefs(boolean value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Constants.favouritesName , value);
        editor.commit();
    }

    public boolean getFavouritesFromSharedPrefs(){
        boolean favouriteValue = sharedPreferences.getBoolean(Constants.favouritesName , false);
        return favouriteValue;
    }
}
