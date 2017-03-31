package mx.com.cdcs.yoconstruyo.signup;


import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import mx.com.cdcs.yoconstruyo.data.service.YoConstruyoService;
import mx.com.cdcs.yoconstruyo.model.SignUpResponse;
import mx.com.cdcs.yoconstruyo.util.schedulers.BaseSchedulerProvider;

public class SignUpPresenter {

    private SignUpView view;
    private YoConstruyoService service;
    private BaseSchedulerProvider schedulerProvider;
    private CompositeDisposable disposables;

    public SignUpPresenter(SignUpView view, YoConstruyoService service, BaseSchedulerProvider schedulerProvider) {
        this.view = view;
        this.service = service;
        this.schedulerProvider = schedulerProvider;
    }

    public void start() {
        disposables = new CompositeDisposable();
    }

    public void signUp(String name, String lastname, String email, String password,
                       String passwordConfirmation, String country, String state, String city,
                       String gender, String education, String dob) {

        if (name == null || name.isEmpty()) {
            view.showInvalidFieldsMessage("Nombre inválido");
            return;
        }

        if (lastname == null || lastname.isEmpty()) {
            view.showInvalidFieldsMessage("Apellido inválido");
            return;
        }

        if (email == null || email.isEmpty()) {
            view.showInvalidFieldsMessage("Email invalido");
            return;
        }

        if (password == null || password.isEmpty()) {
            view.showInvalidFieldsMessage("Contraseña inválida");
            return;
        }

        if (passwordConfirmation == null || passwordConfirmation.isEmpty()) {
            view.showInvalidFieldsMessage("Contraseña inválida");
            return;
        }

        if (!password.equals(passwordConfirmation)) {
            view.showInvalidFieldsMessage("Las contraseñas deben coincidir");
            return;
        }

        if (country == null || country.isEmpty()) {
            view.showInvalidFieldsMessage("País inválido");
            return;
        }

        if (state == null || state.isEmpty()) {
            view.showInvalidFieldsMessage("Estado inválido");
            return;
        }

        if (city == null || city.isEmpty()) {
            view.showInvalidFieldsMessage("Ciudad inválida");
            return;
        }

        if (dob == null || dob.isEmpty()) {
            view.showInvalidFieldsMessage("Introduce tu fecha de nacimiento");
            return;
        }

        view.setLoadingIndicator(true);
        disposables.clear();
        disposables.add(service.signUp(name, lastname, email, password, passwordConfirmation,
                gender, dob, country, state, city, education)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(new DisposableSingleObserver<SignUpResponse>() {
                    @Override
                    public void onSuccess(SignUpResponse signUpResponse) {
                        if (isViewAttached()) {
                            view.finishActivity();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("SignUpPresenter", e.toString());
                        if (isViewAttached()) {
                            view.showSignUpErrorMessage();
                            view.setLoadingIndicator(false);
                        }
                    }
                })
        );

    }

    public void stop() {
        view = null;
        if (disposables != null) {
            disposables.clear();
        }
    }

    private boolean isViewAttached() {
        return view != null;
    }
}
