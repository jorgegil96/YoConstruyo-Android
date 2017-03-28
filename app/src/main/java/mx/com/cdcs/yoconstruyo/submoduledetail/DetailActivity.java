package mx.com.cdcs.yoconstruyo.submoduledetail;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mx.com.cdcs.yoconstruyo.R;
import mx.com.cdcs.yoconstruyo.main.MainActivity;
import mx.com.cdcs.yoconstruyo.module.ModuleActivity;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.webView) WebView webView;
    @BindView(R.id.button_left) Button btnLeft;
    @BindView(R.id.button_right) Button btnRight;

    private int moduleId;
    private int subModuleId;
    private Map<Integer, String> subModulesTitles;
    private Map<Integer, Integer> subModulesIndexes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        swipeRefreshLayout.setRefreshing(true);

        moduleId = getIntent().getIntExtra(MainActivity.MODULE_ID, -1);
        subModuleId = getIntent().getIntExtra(ModuleActivity.SUB_MODULE_ID, -1);

        // Map<Id, Title>
        subModulesTitles = (HashMap<Integer, String>) getIntent().getSerializableExtra(ModuleActivity.SUB_MODULE_TITLES);
        // Map<Id, Index>
        subModulesIndexes = (HashMap<Integer, Integer>) getIntent().getSerializableExtra(ModuleActivity.SUB_MODULE_INDEXES);

        setTitle(subModulesTitles.get(subModuleId));

        if (moduleId == -1 || subModuleId == -1) {
            return;
        }

        if (subModulesIndexes.get(subModuleId) > 1) {
            btnLeft.setText("Anterior");
        } else {
            btnLeft.setText("Regresar");
        }

        if (subModulesIndexes.get(subModuleId) < subModulesIndexes.size()) {
            btnRight.setText("Siguiente");
        } else {
            btnRight.setText("Terminar");
        }


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                btnLeft.setVisibility(View.VISIBLE);
                btnRight.setVisibility(View.VISIBLE);
                webView.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setEnabled(false);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://cdcs.com.mx/cursos/module/" + moduleId + "/submodule/" + subModuleId + "/mobile");
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

    @OnClick(R.id.button_left)
    public void onLeftClick() {
        int prev = getPreviousSubModule();

        if (prev == -1) {
            finish();
        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(ModuleActivity.SUB_MODULE_INDEXES, (HashMap) subModulesIndexes);
            intent.putExtra(ModuleActivity.SUB_MODULE_TITLES, (HashMap) subModulesTitles);
            intent.putExtra(MainActivity.MODULE_ID, moduleId);
            intent.putExtra(ModuleActivity.SUB_MODULE_ID, prev);
            startActivity(intent);
            finish();
        }
    }

    @OnClick(R.id.button_right)
    public void onRightClick() {
        int nxt = getNextSubmodule();

        if (nxt == -1) {
            finish();
        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(ModuleActivity.SUB_MODULE_INDEXES, (HashMap) subModulesIndexes);
            intent.putExtra(ModuleActivity.SUB_MODULE_TITLES, (HashMap) subModulesTitles);
            intent.putExtra(MainActivity.MODULE_ID, moduleId);
            intent.putExtra(ModuleActivity.SUB_MODULE_ID, nxt);
            startActivity(intent);
            finish();
        }
    }

    private int getPreviousSubModule() {
        if (subModulesIndexes.get(subModuleId) > 1) {
            int previousIndex = subModulesIndexes.get(subModuleId) - 1;
            for (Map.Entry<Integer, Integer> entry : subModulesIndexes.entrySet()) {
                if (entry.getValue() == previousIndex) {
                    Log.d("DetailActivity", "previous: " + entry.getKey());
                    return entry.getKey();
                }
            }
        }
        return -1;
    }

    private int getNextSubmodule() {
        if (subModulesIndexes.get(subModuleId) < subModulesIndexes.size()) {
            int nextIndex = subModulesIndexes.get(subModuleId) + 1;
            for (Map.Entry<Integer, Integer> entry : subModulesIndexes.entrySet()) {
                if (entry.getValue() == nextIndex) {
                    return entry.getKey();
                }
            }
        }
        return -1;
    }
}
