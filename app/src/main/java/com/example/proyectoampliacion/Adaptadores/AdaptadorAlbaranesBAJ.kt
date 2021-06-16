package com.example.proyectoampliacion.Adaptadores

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.Navigation
import com.example.proyectoampliacion.Classes_Auxiliares.Albaran
import com.example.proyectoampliacion.R
import kotlinx.android.synthetic.main.vista_baj_albaran.view.*

class AdaptadorAlbaranesBAJ (private val mContext: Context, private val listaAlbaranes: List<Albaran>): ArrayAdapter<Albaran>(mContext,0,listaAlbaranes)  {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        //return super.getView(position, convertView, parent)

        val layout = LayoutInflater.from(mContext).inflate(R.layout.vista_baj_albaran,parent,false);

        val elementoActual = listaAlbaranes[position];

        layout.tvFechaALBAJ.text = elementoActual.fecha;
        layout.tvProveedorALBAJ.text = elementoActual.proveedor;
        layout.tvIdALBAJ.text = elementoActual.id.toString();

        layout.btnBorrarAL.setBackgroundColor(Color.RED)

        layout.btnBorrarAL.setOnClickListener(){


            var bundle = Bundle();

            bundle.putInt("elemento",elementoActual.id)

            Navigation.findNavController(it).navigate(R.id.modificarFragment,bundle)

        }

        return layout;
    }
}