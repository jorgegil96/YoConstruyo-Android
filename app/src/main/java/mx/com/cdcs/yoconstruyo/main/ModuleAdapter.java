package mx.com.cdcs.yoconstruyo.main;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mx.com.cdcs.yoconstruyo.R;
import mx.com.cdcs.yoconstruyo.model.Module;

public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.ViewHolder> {

    private Context context;
    private List<Module> modules;

    public ModuleAdapter(Context context, List<Module> modules) {
        this.context = context;
        this.modules = modules;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.module_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        Module module = modules.get(i);

        Picasso.with(context).load(module.getImage()).into(holder.ivImage);
        holder.tvTitle.setText(module.getTitle());
        if (module.isComplete()) {
            holder.ivCheck.setColorFilter(ContextCompat.getColor(context, R.color.checkCompleted));
        } else {
            holder.ivCheck.setColorFilter(ContextCompat.getColor(context, R.color.checkNormal));
        }
    }

    @Override
    public int getItemCount() {
        return null != modules ? modules.size() : 0;
    }

    public void setData(List<Module> data) {
        modules.clear();
        modules.addAll(data);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_module) ImageView ivImage;
        @BindView(R.id.text_module_title) TextView tvTitle;
        @BindView(R.id.image_check) ImageView ivCheck;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
