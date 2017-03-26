package mx.com.cdcs.yoconstruyo.login;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import mx.com.cdcs.yoconstruyo.data.AppDataStore;
import mx.com.cdcs.yoconstruyo.data.service.YoConstruyoService;
import mx.com.cdcs.yoconstruyo.model.LoginResponse;
import mx.com.cdcs.yoconstruyo.util.schedulers.BaseSchedulerProvider;

public class LoginPresenter {

    private LoginView view;
    private YoConstruyoService service;
    private AppDataStore repository;
    private BaseSchedulerProvider schedulerProvider;
    private CompositeDisposable disposables;

    public LoginPresenter(LoginView view, YoConstruyoService service, AppDataStore repository, BaseSchedulerProvider schedulerProvider) {
        this.view = view;
        this.service = service;
        this.repository = repository;
        this.schedulerProvider = schedulerProvider;
    }

    public void start() {
        disposables = new CompositeDisposable();
    }

    public void login(final String email, String password) {
        view.hideLoginForm();
        view.setLoadingIndicator(true);
        disposables.add(service.login(email, password)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSuccess(new Consumer<LoginResponse>() {
                    @Override
                    public void accept(LoginResponse response) throws Exception {
                        repository.saveEmail(email);
                        repository.saveToken(response.getToken());
                    }
                })
                .subscribeWith(new DisposableSingleObserver<LoginResponse>() {
                    @Override
                    public void onSuccess(LoginResponse response) {
                        if (isViewAttached()) {
                            view.startMainActivity();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        if (isViewAttached()) {
                            view.showInvalidCredentialsMessage(t.getMessage());
                            view.setLoadingIndicator(false);
                            view.showLoginForm();
                        }
                    }
                })
        );
    }

    public void signUp() {

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
