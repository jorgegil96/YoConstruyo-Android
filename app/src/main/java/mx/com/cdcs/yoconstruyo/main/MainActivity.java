package mx.com.cdcs.yoconstruyo.main;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import mx.com.cdcs.yoconstruyo.R;

public class MainActivity extends AppCompatActivity implements
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

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
    }

    @Override
    public void onRefresh() {

    }
}
