package mx.com.cdcs.yoconstruyo.main;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mx.com.cdcs.yoconstruyo.R;
import mx.com.cdcs.yoconstruyo.data.service.YoConstruyoService;
import mx.com.cdcs.yoconstruyo.model.Module;
import mx.com.cdcs.yoconstruyo.util.schedulers.SchedulerProvider;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements
        SwipeRefreshLayout.OnRefreshListener, MainView {

    @BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private MainPresenter presenter;
    private ModuleAdapter moduleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        moduleAdapter = new ModuleAdapter(this, null);
        recyclerView.setAdapter(moduleAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.google.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        YoConstruyoService service = retrofit.create(YoConstruyoService.class);

        presenter = new MainPresenter(this, service, SchedulerProvider.getInstance());
        presenter.start();
        presenter.loadModules();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.stop();
    }

    @Override
    public void onRefresh() {
        presenter.loadModules();
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        swipeRefreshLayout.setRefreshing(active);
    }

    @Override
    public void showModules(List<Module> modules) {
        moduleAdapter.setData(modules);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideModules() {
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingErrorToast() {
        Toast.makeText(this, R.string.error_loading_modules, Toast.LENGTH_LONG).show();
    }
}
