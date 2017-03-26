package mx.com.cdcs.yoconstruyo.module;

import android.util.Log;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import mx.com.cdcs.yoconstruyo.data.AppDataStore;
import mx.com.cdcs.yoconstruyo.data.service.YoConstruyoService;
import mx.com.cdcs.yoconstruyo.model.Submodule;
import mx.com.cdcs.yoconstruyo.util.schedulers.BaseSchedulerProvider;

public class ModulePresenter {

    private ModuleView view;
    private YoConstruyoService service;
    private AppDataStore repository;
    private BaseSchedulerProvider schedulerProvider;
    private CompositeDisposable disposables;

    public ModulePresenter(ModuleView view, YoConstruyoService service, AppDataStore repository,
                           BaseSchedulerProvider schedulerProvider) {
        this.view = view;
        this.service = service;
        this.repository = repository;
        this.schedulerProvider = schedulerProvider;
    }

    public void start() {
        disposables = new CompositeDisposable();
    }

    public void loadSubmodules(int moduleId) {
        view.setLoadingIndicator(true);
        view.hideSubmodules();
        disposables.clear();
        disposables.add(service.getSubmodules(String.valueOf(moduleId), repository.getToken())
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(new DisposableSingleObserver<List<Submodule>>() {
                    @Override
                    public void onSuccess(List<Submodule> submodules) {
                        if (isViewAttached()) {
                            if (!submodules.isEmpty()) {
                                view.showSubmodules(submodules);
                            } else {
                                view.showLoadingErrorToast();
                            }
                            view.setLoadingIndicator(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isViewAttached()) {
                            view.showLoadingErrorToast();
                            view.setLoadingIndicator(false);
                        }
                        Log.d("Presenter", e.toString());
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
