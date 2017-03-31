package mx.com.cdcs.yoconstruyo.signup;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mx.com.cdcs.yoconstruyo.R;
import mx.com.cdcs.yoconstruyo.data.service.YoConstruyoService;
import mx.com.cdcs.yoconstruyo.util.schedulers.SchedulerProvider;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupActivity extends AppCompatActivity implements SignUpView, OnDateSelected {

    private static final String TAG = "SignupActivity";

    @BindView(R.id.button_dob) TextView btnDob;
    @BindView(R.id.button_signup) Button btnSignUp;
    @BindView(R.id.edit_name) EditText etName;
    @BindView(R.id.edit_last_name) EditText etLastName;
    @BindView(R.id.edit_password) EditText etPassword;
    @BindView(R.id.edit_password_confirm) EditText etPasswordConfirm;
    @BindView(R.id.edit_email) EditText etEmail;
    @BindView(R.id.edit_country) EditText etCountry;
    @BindView(R.id.edit_state) EditText etState;
    @BindView(R.id.edit_city) EditText etCity;
    @BindView(R.id.spinner_gender) Spinner spinnerGender;
    @BindView(R.id.spinner_education) Spinner spinnerEducation;
    @BindView(R.id.text_login) TextView tvLogin;

    private SignUpPresenter presenter;
    private String gender;
    private String education;
    private String dob;
    private MaterialDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        gender = "Hombre";
        education = "Primaria";

        loadingDialog = new MaterialDialog.Builder(this)
                .title(R.string.registrando)
                .content(R.string.porfavor_espera)
                .progress(true, 0)
                .build();

        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerEducation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                education = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://cdcs.com.mx/cursos/api/v1/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        YoConstruyoService service = retrofit.create(YoConstruyoService.class);
        presenter = new SignUpPresenter(this, service, SchedulerProvider.getInstance());
        presenter.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        presenter.stop();
    }

    @Override
    public void onDateSelected(String date) {
        dob = date;
        btnDob.setText(date);
    }

    @OnClick(R.id.button_dob)
    public void onClickDobButtonClick() {
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @OnClick(R.id.text_login)
    public void onClickLogin() {
        finish();
    }

    @OnClick(R.id.button_signup)
    public void onSignUpButtonClick() {
        String name = etName.getText().toString();
        String lastName = etLastName.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String passwordConfirmation = etPasswordConfirm.getText().toString();
        String country = etCountry.getText().toString();
        String state = etState.getText().toString();
        String city = etCity.getText().toString();

        presenter.signUp(name, lastName, email, password, passwordConfirmation, country, state,
                city, gender, education, dob);
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (active) {
            loadingDialog.show();
        } else {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void showInvalidFieldsMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSignUpErrorMessage() {
        Toast.makeText(this, "Error registrando usuario. Intenta de nuevo más tarde", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishActivity() {
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        finish();
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        private OnDateSelected listener;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            // Verify that the host activity implements the callback interface
            try {
                listener = (OnDateSelected) context;
            } catch (ClassCastException e) {
                // The activity doesn't implement the interface, throw exception
                throw new ClassCastException(context.toString()
                        + " must implement OnDateSelected");
            }
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            month++;
            String sMonth;
            if (month < 10) {
                sMonth = "0" + String.valueOf(month);
            } else {
                sMonth = String.valueOf(month);
            }

            String sDay;
            if (day < 10) {
                sDay = "0" + String.valueOf(day);
            } else {
                sDay = String.valueOf(day);
            }

            listener.onDateSelected("" + year + "/" + sMonth + "/" + sDay);
        }
    }
}
