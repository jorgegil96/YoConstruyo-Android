package mx.com.cdcs.yoconstruyo.module;

import android.util.Log;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import mx.com.cdcs.yoconstruyo.data.service.YoConstruyoService;
import mx.com.cdcs.yoconstruyo.model.Submodule;
import mx.com.cdcs.yoconstruyo.util.schedulers.BaseSchedulerProvider;

public class ModulePresenter {

    private ModuleView view;
    private YoConstruyoService service;
    private BaseSchedulerProvider schedulerProvider;
    private CompositeDisposable disposables;

    public ModulePresenter(ModuleView view, YoConstruyoService service,
                           BaseSchedulerProvider schedulerProvider) {
        this.view = view;
        this.service = service;
        this.schedulerProvider = schedulerProvider;
    }

    public void start() {
        disposables = new CompositeDisposable();
    }

    public void loadSubmodules(int moduleId) {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjExLCJpc3MiOiJodHRwOlwvXC9jZGNzLmNvbS5teFwvY3Vyc29zXC9hcGlcL3YxXC9hdXRoZW50aWNhdGUiLCJpYXQiOjE0OTA0OTY4NzMsImV4cCI6MTQ5MDUwMDQ3MywibmJmIjoxNDkwNDk2ODczLCJqdGkiOiI0MGExZTA4ZjI0YWY2Y2RhN2U1M2NhY2Q4NDY5YjZkMyJ9.Oh2FU1XzHNdx3bDVYyDvnYuaLXkbTvjf1xj4YcmXZwo";
        view.setLoadingIndicator(true);
        view.hideSubmodules();
        disposables.clear();
        disposables.add(service.getSubmodules(String.valueOf(moduleId), token)
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
