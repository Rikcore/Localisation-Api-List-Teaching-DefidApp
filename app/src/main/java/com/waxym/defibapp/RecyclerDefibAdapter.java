package com.waxym.defibapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerDefibAdapter extends RecyclerView.Adapter<RecyclerDefibAdapter.ViewHolder> {

    private List<Record> list;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    RecyclerDefibAdapter(Context context, List<Record> list) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.defib_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Record currentRecord = list.get(i);
        viewHolder.textViewLocationName.setText(String.format(context.getString(R.string.site_name), currentRecord.getFields().getNomSite()));
        viewHolder.textViewAdress.setText(String.format(context.getString(R.string.adress), currentRecord.getFields().getAdresse()));
        viewHolder.textViewDistance.setText(String.format(context.getString(R.string.meter), String.valueOf(currentRecord.getFields().getDistance())));
        if(currentRecord.getFields().getImplantation() != null){
            viewHolder.textViewImplantation.setText(String.format(context.getString(R.string.implantation), currentRecord.getFields().getImplantation()));
        } else {
            viewHolder.textViewImplantation.setText(context.getString(R.string.no_implantation));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public Record getItem(int id) {
        return list.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewLocationName;
        TextView textViewAdress;
        TextView textViewDistance;
        TextView textViewImplantation;

        ViewHolder(View itemView) {
            super(itemView);
            textViewLocationName = itemView.findViewById(R.id.textViewLocationName);
            textViewAdress = itemView.findViewById(R.id.textViewAdress);
            textViewDistance = itemView.findViewById(R.id.textViewDistance);
            textViewImplantation = itemView.findViewById(R.id.textViewImplantation);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
