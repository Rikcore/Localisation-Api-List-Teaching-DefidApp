package com.waxym.defibapp

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class RecyclerDefibAdapter internal constructor(private val context: Context, private val list: List<Record>) : RecyclerView.Adapter<RecyclerDefibAdapter.ViewHolder>() {
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.defib_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val currentRecord = list[i]
        viewHolder.textViewLocationName.text = context.getString(R.string.site_name).plus(" " + currentRecord.fields!!.nomSite)
        viewHolder.textViewAdress.text = context.getString(R.string.adress).plus(" " + currentRecord.fields!!.adresse)
        viewHolder.textViewDistance.text = currentRecord.fields!!.distance.toString().plus(context.getString(R.string.meter))
        if (currentRecord.fields!!.implantation != null) {
            viewHolder.textViewImplantation.text = context.getString(R.string.implantation).plus(" " + currentRecord.fields!!.implantation)
        } else {
            viewHolder.textViewImplantation.text = context.getString(R.string.no_implantation)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun getItem(id: Int): Record {
        return list[id]
    }

    fun setClickListener(itemClickListener: ItemClickListener) {
        this.mClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        internal var textViewLocationName: TextView = itemView.findViewById(R.id.textViewLocationName)
        internal var textViewAdress: TextView = itemView.findViewById(R.id.textViewAdress)
        internal var textViewDistance: TextView = itemView.findViewById(R.id.textViewDistance)
        internal var textViewImplantation: TextView = itemView.findViewById(R.id.textViewImplantation)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            if (mClickListener != null) mClickListener!!.onItemClick(view, adapterPosition)
        }
    }
}