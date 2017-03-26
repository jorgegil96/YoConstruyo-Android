package mx.com.cdcs.yoconstruyo.module;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mx.com.cdcs.yoconstruyo.R;
import mx.com.cdcs.yoconstruyo.model.Submodule;

public class SubmoduleAdapter extends RecyclerView.Adapter<SubmoduleAdapter.ViewHolder> {

    private Context context;
    private List<Submodule> submodules;
    private OnSubmoduleClickListener listener;

    public SubmoduleAdapter(Context context, List<Submodule> submodules,
                            OnSubmoduleClickListener listener) {
        this.context = context;
        this.submodules = submodules;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.submodule_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        final Submodule submodule = submodules.get(i);

        Picasso.with(context).load(submodule.getThumbnail()).into(holder.ivImage);
        holder.tvTitle.setText(submodule.getTitle());
        if (submodule.isCompleted()) {
            holder.ivCheck.setColorFilter(ContextCompat.getColor(context, R.color.checkCompleted));
        } else {
            holder.ivCheck.setColorFilter(ContextCompat.getColor(context, R.color.checkNormal));
        }

        holder.layoutSubmodule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSubmoduleClick(submodule);
            }
        });
    }

    @Override
    public int getItemCount() {
        return null != submodules ? submodules.size() : 0;
    }

    public void setData(List<Submodule> data) {
        submodules.clear();
        submodules.addAll(data);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout_submodule) RelativeLayout layoutSubmodule;
        @BindView(R.id.image_submodule) ImageView ivImage;
        @BindView(R.id.text_submodule_title) TextView tvTitle;
        @BindView(R.id.image_check) ImageView ivCheck;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
