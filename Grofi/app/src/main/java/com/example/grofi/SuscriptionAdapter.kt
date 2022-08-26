package com.example.grofi

import android.content.Context
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.grofi.databinding.ItemSuscriptionBinding

/*
-Proyecto: Grofi
-Package:  com.example.grofi
-Autor: Mariano Cervantes
-Realizado el 02/04/2022 11:55 a. m.
*/
class SuscriptionAdapter(private var suscriptions: MutableList<SuscriptionEntity>, private var listener: OnClickListener):
    RecyclerView.Adapter<SuscriptionAdapter.ViewHolder>(){

    private lateinit var mContext: Context

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = ItemSuscriptionBinding.bind(view)

        fun setListener(suscriptionEntity: SuscriptionEntity){
            with(binding.root){
                setOnClickListener { listener.onClick(suscriptionEntity.id) }
                setOnLongClickListener {
                    listener.onDeleteSuscription(suscriptionEntity)
                    true
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_suscription, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val suscription = suscriptions.get(position)
        with(holder){
            setListener(suscription)
            binding.tvName.text = suscription.name
            binding.tvNpersonas.text = suscription.numPerson.toString()
            binding.tvFecha.text = suscription.date
            binding.tvCostoPN.text = suscription.costSuscriprionP.toString()
            binding.tvCostoTN.text = suscription.costSuscriprion.toString()

            Glide.with(mContext)
                .load(suscription.imgUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.imgIcon)

        }
    }

    override fun getItemCount(): Int = suscriptions.size

    fun add(suscriptionEntity: SuscriptionEntity) {
        suscriptions.add(suscriptionEntity)

        notifyItemInserted(suscriptions.size-1)
    }

    fun setSuscription(suscriprions: MutableList<SuscriptionEntity>) {
        this.suscriptions = suscriprions
        notifyDataSetChanged()
    }

    fun update(suscriptionEntity: SuscriptionEntity) {
        val index = suscriptions.indexOf(suscriptionEntity)
        if (index !=-1){
            suscriptions.set(index, suscriptionEntity)
            notifyItemChanged(index)
        }
    }
    fun delete(suscriptionEntity: SuscriptionEntity) {
        val index = suscriptions.indexOf(suscriptionEntity)
        if (index !=-1){
            suscriptions.removeAt(index)
            notifyItemRemoved(index)
        }
    }


}