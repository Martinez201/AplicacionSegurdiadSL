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
import kotlinx.android.synthetic.main.vista_albaranes.view.*
import kotlinx.android.synthetic.main.vista_mod_albaranes.view.*
import kotlinx.android.synthetic.main.vista_mod_clientes.view.*

class AdaptadorAlbaranesMOD(private val mContext: Context, private val listaAlbaranes: List<Albaran>): ArrayAdapter<Albaran>(mContext,0,listaAlbaranes)  {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        //return super.getView(position, convertView, parent)

        val layout = LayoutInflater.from(mContext).inflate(R.layout.vista_mod_albaranes,parent,false);

        val elementoActual = listaAlbaranes[position];

        layout.tvFechaALMOD.text = elementoActual.fecha;
        layout.tvProveedorALMOD.text = elementoActual.proveedor;
        layout.tvIdALMOD.text = elementoActual.id.toString();

        layout.btnEditarALMOD.setBackgroundColor(Color.parseColor("#343a40"))
        layout.btnContenidoALMOD.setBackgroundColor(Color.parseColor("#00599f"))

        layout.btnEditarALMOD.setOnClickListener(){


            var bundle = Bundle();

            bundle.putInt("elemento",elementoActual.id)
            bundle.putInt("tipo_formulario",0)

            Navigation.findNavController(it).navigate(R.id.modificarFragment,bundle)

        }

        return layout;
    }
}