package mx.com.cdcs.yoconstruyo.model;

public class SignUpResponse {

    private boolean registration;

    public SignUpResponse(boolean registration) {
        this.registration = registration;
    }

    public boolean isRegistration() {
        return registration;
    }

    public void setRegistration(boolean registration) {
        this.registration = registration;
    }
}
