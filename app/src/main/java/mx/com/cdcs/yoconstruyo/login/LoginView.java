package mx.com.cdcs.yoconstruyo.login;

public interface LoginView {

    void setLoadingIndicator(boolean active);

    void showLoginErrorMessage();

    void hideLoginForm();

    void showLoginForm();

    void showInvalidCredentialsMessage(String errorMessage);

    void startMainActivity();

}
