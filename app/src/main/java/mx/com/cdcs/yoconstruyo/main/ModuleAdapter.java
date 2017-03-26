package mx.com.cdcs.yoconstruyo.main;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mx.com.cdcs.yoconstruyo.R;
import mx.com.cdcs.yoconstruyo.model.Module;

public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.ViewHolder> {

    private static final String BASE_URL = "http://cdcs.com.mx/cursos/";

    private Context context;
    private List<Module> modules;
    private OnModuleClickListener listener;

    public ModuleAdapter(Context context, List<Module> modules, OnModuleClickListener listener) {
        this.context = context;
        this.modules = modules;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.module_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        final Module module = modules.get(i);

        Picasso.with(context).load(BASE_URL + module.getThumbnail())
                .placeholder(R.drawable.module)
                .into(holder.ivImage);
        holder.tvTitle.setText(module.getTitle());
        if (module.isCompleted()) {
            holder.ivCheck.setVisibility(View.VISIBLE);
        } else {
            holder.ivCheck.setVisibility(View.INVISIBLE);
        }

        holder.cardModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onModuleClick(module);
            }
        });
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

        @BindView(R.id.card_module) CardView cardModule;
        @BindView(R.id.image_module) ImageView ivImage;
        @BindView(R.id.text_module_title) TextView tvTitle;
        @BindView(R.id.image_check) ImageView ivCheck;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
