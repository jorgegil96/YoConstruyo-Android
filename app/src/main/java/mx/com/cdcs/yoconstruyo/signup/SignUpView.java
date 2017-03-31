package mx.com.cdcs.yoconstruyo.signup;

public interface SignUpView {

    void setLoadingIndicator(boolean active);

    void showInvalidFieldsMessage(String message);

    void showSignUpErrorMessage();

    void finishActivity();
}
