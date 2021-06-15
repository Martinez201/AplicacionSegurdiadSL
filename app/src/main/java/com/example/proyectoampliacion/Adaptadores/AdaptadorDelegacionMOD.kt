package com.example.proyectoampliacion.Adaptadores

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.Navigation
import com.example.proyectoampliacion.Classes_Auxiliares.Delegacion
import com.example.proyectoampliacion.R
import kotlinx.android.synthetic.main.vista_delegacion.view.*
import kotlinx.android.synthetic.main.vista_mod_delegacion.view.*

class AdaptadorDelegacionMOD(private val mContext: Context, private val listaDelegaciones: List<Delegacion>):
    ArrayAdapter<Delegacion>(mContext,0,listaDelegaciones) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        //return super.getView(position, convertView, parent)

        val layout = LayoutInflater.from(mContext).inflate(R.layout.vista_mod_delegacion,parent,false);

        val elementoActual = listaDelegaciones[position];

        layout.tvNombreDMOD.text = elementoActual.nombre;
        layout.tvDireccionDMOD.text = elementoActual.direccion;
        layout.tvEmailDMOD.text = elementoActual.email;
        layout.tvTelefonoDMOD.text = elementoActual.telefono;


        layout.btnEditarDMOD.setOnClickListener(){

            var bundle = Bundle();

            bundle.putInt("elemento",elementoActual.id)
            bundle.putInt("tipo_formulario",3)

            Navigation.findNavController(it).navigate(R.id.modificarFragment,bundle)
        }

        return layout;
    }
}