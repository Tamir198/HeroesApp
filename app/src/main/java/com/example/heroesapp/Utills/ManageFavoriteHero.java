package com.example.heroesapp.Utills;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class ManageFavoriteHero {
    private SharedPreferences prefs;

    public ManageFavoriteHero(Context context){

        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void saveSp(String favotireHero,String imagePath){ //saves favorite hero locally
        prefs.edit().putString("favorite_name", favotireHero).apply();
        prefs.edit().putString("favorite_image", imagePath).apply();
        System.out.println("hero save " + favotireHero + "   " + imagePath);
    }

    public String getSpTitle(){ //get the name of favorite hero
        System.out.println("hero recved   " +prefs.getString("favorite_name", "Choose hero"));
        return prefs.getString("favorite_name", "Choose hero");
    }

    public String getSpImage(){ //get the name of favorite hero
        System.out.println("hero recved   " +prefs.getString("favorite_image", "no image"));
        return prefs.getString("favorite_image", "no image");
    }
}
