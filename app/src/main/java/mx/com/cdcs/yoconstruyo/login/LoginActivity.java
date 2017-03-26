package mx.com.cdcs.yoconstruyo.login;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mx.com.cdcs.yoconstruyo.R;
import mx.com.cdcs.yoconstruyo.data.AppDataStore;
import mx.com.cdcs.yoconstruyo.data.AppRepository;
import mx.com.cdcs.yoconstruyo.data.local.AppLocalDataStore;
import mx.com.cdcs.yoconstruyo.data.local.MySharedPreferences;
import mx.com.cdcs.yoconstruyo.data.service.YoConstruyoService;
import mx.com.cdcs.yoconstruyo.main.MainActivity;
import mx.com.cdcs.yoconstruyo.util.schedulers.SchedulerProvider;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.edit_email) EditText etEmail;
    @BindView(R.id.edit_password) EditText etPassword;
    @BindView(R.id.button_login) Button btnLogin;
    @BindView(R.id.text_sign_up) TextView tvSignUp;
    @BindView(R.id.text_forgot_password) TextView tvForgotPassword;
    @BindView(R.id.input_layout_email) TextInputLayout tilEmail;
    @BindView(R.id.input_layout_password) TextInputLayout tilPassword;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://cdcs.com.mx/cursos/api/v1/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        YoConstruyoService service = retrofit.create(YoConstruyoService.class);
        AppDataStore repository = AppRepository.getInstance(AppLocalDataStore.getInstance(
                getSharedPreferences(MySharedPreferences.MY_PREFERENCES, MODE_PRIVATE)));

        presenter = new LoginPresenter(this, service, repository, SchedulerProvider.getInstance());
        presenter.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.stop();
    }

    @OnClick(R.id.button_login)
    public void onLoginButtonClick() {
        presenter.login(etEmail.getText().toString(), etPassword.getText().toString());
    }

    @OnClick(R.id.text_sign_up)
    public void onSignUpTextViewClick() {
        presenter.signUp();
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (active) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showLoginErrorMessage() {
        Toast.makeText(this, R.string.login_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoginForm() {
        etEmail.setVisibility(View.INVISIBLE);
        etPassword.setVisibility(View.INVISIBLE);
        btnLogin.setVisibility(View.INVISIBLE);
        tvSignUp.setVisibility(View.INVISIBLE);
        tvForgotPassword.setVisibility(View.INVISIBLE);
        tilEmail.setVisibility(View.INVISIBLE);
        tilPassword.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showLoginForm() {
        etEmail.setVisibility(View.VISIBLE);
        etPassword.setVisibility(View.VISIBLE);
        btnLogin.setVisibility(View.VISIBLE);
        tvSignUp.setVisibility(View.VISIBLE);
        tvForgotPassword.setVisibility(View.VISIBLE);
        tilEmail.setVisibility(View.VISIBLE);
        tilPassword.setVisibility(View.VISIBLE);
    }

    @Override
    public void showInvalidCredentialsMessage(String errorMessage) {
        Toast.makeText(this, R.string.invalid_credentials, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
