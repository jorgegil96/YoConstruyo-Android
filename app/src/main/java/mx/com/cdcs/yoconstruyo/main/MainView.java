package mx.com.cdcs.yoconstruyo.main;

import java.util.List;

import mx.com.cdcs.yoconstruyo.model.Module;

public interface MainView {

    void setLoadingIndicator(boolean active);

    void showModules(List<Module> modules);

    void hideModules();

    void showLoadingErrorToast();
}
