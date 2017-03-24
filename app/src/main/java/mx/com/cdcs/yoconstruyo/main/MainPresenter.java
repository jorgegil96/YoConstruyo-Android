package mx.com.cdcs.yoconstruyo.main;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import mx.com.cdcs.yoconstruyo.data.service.YoConstruyoService;
import mx.com.cdcs.yoconstruyo.model.Module;
import mx.com.cdcs.yoconstruyo.util.schedulers.BaseSchedulerProvider;

public class MainPresenter {

    private MainView view;
    private YoConstruyoService service;
    private BaseSchedulerProvider schedulerProvider;
    private CompositeDisposable disposables;

    public MainPresenter(MainView view, YoConstruyoService service,
                         BaseSchedulerProvider schedulerProvider) {
        this.view = view;
        this.service = service;
        this.schedulerProvider = schedulerProvider;
    }

    public void start() {
        disposables = new CompositeDisposable();
    }

    public void loadModules() {
        view.setLoadingIndicator(true);
        view.hideModules();
        disposables.clear();
        disposables.add(service.getModules()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(new DisposableSingleObserver<List<Module>>() {
                    @Override
                    public void onSuccess(List<Module> modules) {
                        if (isViewAttached()) {
                            if (!modules.isEmpty()) {
                                view.showModules(modules);
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
