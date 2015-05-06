package edu.usf.cutr.trackerlib.io;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by cagricetin on 4/28/15.
 */
public class PreferenceHelper {

    private static final String PREF_NAME = "edu.usf.cutr.trackerlib.io.pref";

    public static void saveString(Context context, String key, String value){
        SharedPreferences sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void saveInt(Context context, String key, Integer value){
        SharedPreferences sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void saveLong(Context context, String key, long value){
        SharedPreferences sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static void saveBoolean(Context context, String key, boolean value){
        SharedPreferences sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static String getString(Context context, String key){
        SharedPreferences sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPref.getString(key, "");
    }

    public static Integer getInteger(Context context, String key){
        SharedPreferences sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPref.getInt(key, -1);
    }

    public static long getLong(Context context, String key){
        SharedPreferences sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPref.getLong(key, -1);
    }

    public static boolean getBoolean(Context context, String key){
        SharedPreferences sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(key, false);
    }
}
