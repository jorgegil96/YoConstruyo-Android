package mx.com.cdcs.yoconstruyo.data;

public class AppRepository implements AppDataStore {

    private static AppRepository INSTANCE = null;

    private final AppDataStore localDataStore;

    public static AppRepository getInstance(AppDataStore localDataStore) {
        if (INSTANCE == null) {
            INSTANCE = new AppRepository(localDataStore);
        }
        return INSTANCE;
    }

    private AppRepository(AppDataStore localDataStore) {
        this.localDataStore = localDataStore;
    }

    @Override
    public void saveToken(String token) {
        localDataStore.saveToken(token);
    }

    @Override
    public void saveEmail(String email) {
        localDataStore.saveEmail(email);
    }

    @Override
    public String getToken() {
        return localDataStore.getToken();
    }

    @Override
    public String getEmail() {
        return localDataStore.getEmail();
    }
}
