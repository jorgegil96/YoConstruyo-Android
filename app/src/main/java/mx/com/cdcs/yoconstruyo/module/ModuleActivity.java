package mx.com.cdcs.yoconstruyo.module;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import mx.com.cdcs.yoconstruyo.R;
import mx.com.cdcs.yoconstruyo.data.AppDataStore;
import mx.com.cdcs.yoconstruyo.data.AppRepository;
import mx.com.cdcs.yoconstruyo.data.local.AppLocalDataStore;
import mx.com.cdcs.yoconstruyo.data.local.MySharedPreferences;
import mx.com.cdcs.yoconstruyo.data.service.YoConstruyoService;
import mx.com.cdcs.yoconstruyo.main.MainActivity;
import mx.com.cdcs.yoconstruyo.model.Submodule;
import mx.com.cdcs.yoconstruyo.submoduledetail.DetailActivity;
import mx.com.cdcs.yoconstruyo.util.schedulers.SchedulerProvider;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ModuleActivity extends AppCompatActivity implements
        SwipeRefreshLayout.OnRefreshListener, ModuleView, OnSubmoduleClickListener {

    public static final String SUB_MODULE_ID = "subModuleId";
    public static final String SUB_MODULE_TITLES = "subModuleTitles";
    public static final String SUB_MODULE_INDEXES = "subModuleIndexes";

    @BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private ModulePresenter presenter;
    private SubmoduleAdapter submoduleAdapter;

    private int moduleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        setTitle(getIntent().getStringExtra(MainActivity.MODULE_TITLE));

        moduleId = getIntent().getIntExtra(MainActivity.MODULE_ID, 0);

        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        submoduleAdapter = new SubmoduleAdapter(this, new ArrayList<Submodule>(), this);
        recyclerView.setAdapter(submoduleAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://cdcs.com.mx/cursos/api/v1/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        YoConstruyoService service = retrofit.create(YoConstruyoService.class);
        AppDataStore repository = AppRepository.getInstance(AppLocalDataStore.getInstance(
                getSharedPreferences(MySharedPreferences.MY_PREFERENCES, MODE_PRIVATE)));

        presenter = new ModulePresenter(this, service, repository, SchedulerProvider.getInstance());
        presenter.start();
        presenter.loadSubmodules(moduleId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
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
        presenter.loadSubmodules(moduleId);
    }

    @Override
    public void onSubmoduleClick(Submodule submodule) {
        presenter.subModuleClick(submodule);
    }

    @Override
    public void startDetailActivity(Submodule submodule, Map<Integer, String> subModulesTitles,
                                    Map<Integer, Integer> subModulesIndexes) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(SUB_MODULE_INDEXES, (HashMap) subModulesIndexes);
        intent.putExtra(SUB_MODULE_TITLES, (HashMap) subModulesTitles);
        intent.putExtra(MainActivity.MODULE_ID, moduleId);
        intent.putExtra(SUB_MODULE_ID, submodule.getId());
        startActivity(intent);
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        swipeRefreshLayout.setRefreshing(active);
    }

    @Override
    public void showSubmodules(List<Submodule> submodules) {
        submoduleAdapter.setData(submodules);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSubmodules() {
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingErrorToast() {
        Toast.makeText(this, R.string.error_loading_submodules, Toast.LENGTH_LONG).show();
    }
}
