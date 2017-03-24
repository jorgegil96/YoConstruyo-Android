package mx.com.cdcs.yoconstruyo.module;

import java.util.List;

import mx.com.cdcs.yoconstruyo.model.Submodule;

public interface ModuleView {

    void setLoadingIndicator(boolean active);

    void showSubmodules(List<Submodule> submodules);

    void hideSubmodules();

    void showLoadingErrorToast();
}
