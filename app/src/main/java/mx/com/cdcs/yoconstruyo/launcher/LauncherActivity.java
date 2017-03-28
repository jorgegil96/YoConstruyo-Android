package mx.com.cdcs.yoconstruyo.launcher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mx.com.cdcs.yoconstruyo.data.AppDataStore;
import mx.com.cdcs.yoconstruyo.data.AppRepository;
import mx.com.cdcs.yoconstruyo.data.local.AppLocalDataStore;
import mx.com.cdcs.yoconstruyo.data.local.MySharedPreferences;
import mx.com.cdcs.yoconstruyo.login.LoginActivity;
import mx.com.cdcs.yoconstruyo.main.MainActivity;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppDataStore repository = AppRepository.getInstance(AppLocalDataStore.getInstance(
                getSharedPreferences(MySharedPreferences.MY_PREFERENCES, MODE_PRIVATE)));

        if (repository.getToken() != null && !repository.getToken().equals("")) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
