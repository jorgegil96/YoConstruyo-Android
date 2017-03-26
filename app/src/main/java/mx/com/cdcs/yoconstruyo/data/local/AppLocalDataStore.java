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

    }

    @Override
    public void saveEmail(String email) {

    }

    @Override
    public String getToken() {
        return null;
    }

    @Override
    public String getEmail() {
        return null;
    }
}
