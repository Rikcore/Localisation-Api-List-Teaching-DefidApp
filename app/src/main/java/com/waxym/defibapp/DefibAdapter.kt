package com.waxym.defibapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class DefibAdapter(private val context: Context, private val recordList: List<Record>) : BaseAdapter() {
    private val layoutInflater: LayoutInflater

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return recordList.size
    }

    override fun getItem(position: Int): Any {
        return recordList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.defib_item, parent, false)
        }
        val textViewLocationName = convertView!!.findViewById<TextView>(R.id.textViewLocationName)
        val textViewAdress = convertView.findViewById<TextView>(R.id.textViewAdress)
        val textViewDistance = convertView.findViewById<TextView>(R.id.textViewDistance)
        val textViewImplantation = convertView.findViewById<TextView>(R.id.textViewImplantation)

        val currentDefib = recordList[position]
        textViewLocationName.setText(String.format(context.getString(R.string.site_name), currentDefib.fields!!.nomSite))
        textViewAdress.setText(String.format(context.getString(R.string.adress), currentDefib.fields!!.adresse))
        textViewDistance.setText(String.format(context.getString(R.string.meter), currentDefib.fields!!.distance.toString()))
        if (currentDefib.fields!!.implantation != null) {
            textViewImplantation.setText(String.format(context.getString(R.string.implantation), currentDefib.fields!!.implantation))
        } else {
            textViewImplantation.text = context.getString(R.string.no_implantation)
        }

        return convertView
    }
}
