package mx.com.cdcs.yoconstruyo.main;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mx.com.cdcs.yoconstruyo.R;
import mx.com.cdcs.yoconstruyo.data.AppDataStore;
import mx.com.cdcs.yoconstruyo.data.AppRepository;
import mx.com.cdcs.yoconstruyo.data.local.AppLocalDataStore;
import mx.com.cdcs.yoconstruyo.data.local.MySharedPreferences;
import mx.com.cdcs.yoconstruyo.data.service.YoConstruyoService;
import mx.com.cdcs.yoconstruyo.model.Module;
import mx.com.cdcs.yoconstruyo.module.ModuleActivity;
import mx.com.cdcs.yoconstruyo.util.schedulers.SchedulerProvider;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements
        SwipeRefreshLayout.OnRefreshListener, MainView, OnModuleClickListener {

    public static final String MODULE_ID = "moduleId";

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
        moduleAdapter = new ModuleAdapter(this, new ArrayList<Module>(), this);
        recyclerView.setAdapter(moduleAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://cdcs.com.mx/cursos/api/v1/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        YoConstruyoService service = retrofit.create(YoConstruyoService.class);
        AppDataStore repository = AppRepository.getInstance(AppLocalDataStore.getInstance(
                getSharedPreferences(MySharedPreferences.MY_PREFERENCES, MODE_PRIVATE)));

        presenter = new MainPresenter(this, service, repository, SchedulerProvider.getInstance());
        presenter.start();
        presenter.loadModules();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        // Set refresh icon color to white.
        Drawable refreshIcon = menu.findItem(R.id.action_refresh).getIcon();
        if (refreshIcon != null) {
            refreshIcon.mutate();
            refreshIcon.setColorFilter(ContextCompat.getColor(this, R.color.white),
                    PorterDuff.Mode.SRC_ATOP);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                presenter.loadModules();
                return true;
        }

        return super.onOptionsItemSelected(item);
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

    @Override
    public void onModuleClick(Module module) {
        Intent intent = new Intent(this, ModuleActivity.class);
        intent.putExtra(MODULE_ID, module.getId());
        startActivity(intent);
    }
}
