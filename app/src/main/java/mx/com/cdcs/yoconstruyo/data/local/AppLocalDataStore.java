package mx.com.cdcs.yoconstruyo.data.local;

import android.content.SharedPreferences;

import mx.com.cdcs.yoconstruyo.data.AppDataStore;

public class AppLocalDataStore implements AppDataStore {

    private static AppLocalDataStore INSTANCE = null;

    private SharedPreferences sharedPreferences;

    public static AppLocalDataStore getInstance(SharedPreferences sharedPreferences) {
        if (INSTANCE == null) {
            INSTANCE = new AppLocalDataStore(sharedPreferences);
        }
        return INSTANCE;
    }

    private AppLocalDataStore(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void saveToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MySharedPreferences.TOKEN_KEY, token);
        editor.apply();
    }

    @Override
    public void saveEmail(String email) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MySharedPreferences.EMAIL_KEY, email);
        editor.apply();
    }

    @Override
    public String getToken() {
        return sharedPreferences.getString(MySharedPreferences.TOKEN_KEY, null);
    }

    @Override
    public String getEmail() {
        return sharedPreferences.getString(MySharedPreferences.EMAIL_KEY, null);
    }
}
