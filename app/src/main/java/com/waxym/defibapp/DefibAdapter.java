package com.waxym.defibapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class DefibAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Record> recordList;

    public DefibAdapter(Context context, List<Record> recordList){
        this.context = context;
        this.recordList = recordList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return recordList.size();
    }

    @Override
    public Object getItem(int position) {
        return recordList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.defib_item, parent, false);
        }
        TextView textViewLocationName = convertView.findViewById(R.id.textViewLocationName);
        TextView textViewAdress = convertView.findViewById(R.id.textViewAdress);
        TextView textViewDistance = convertView.findViewById(R.id.textViewDistance);
        TextView textViewImplantation = convertView.findViewById(R.id.textViewImplantation);

        Record currentDefib = recordList.get(position);
        textViewLocationName.setText(String.format("Implantation : %s", currentDefib.getFields().getNomSite()));
        textViewAdress.setText(String.format("Adresse : %s", currentDefib.getFields().getAdresse()));
        textViewDistance.setText(String.format("%s mètres", String.valueOf(currentDefib.getFields().getDistance())));
        textViewImplantation.setText(String.format("Implantation : %s", currentDefib.getFields().getImplantation()));

        return convertView;
    }
}