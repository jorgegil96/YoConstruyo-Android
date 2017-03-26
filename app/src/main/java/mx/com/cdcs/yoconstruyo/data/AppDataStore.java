package mx.com.cdcs.yoconstruyo.data;

public interface AppDataStore {

    void saveToken(String token);

    void saveEmail(String email);

    String getToken();

    String getEmail();

}
