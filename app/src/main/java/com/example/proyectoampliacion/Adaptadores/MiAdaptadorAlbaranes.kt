package com.example.proyectoampliacion.Adaptadores

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.proyectoampliacion.Classes_Auxiliares.Albaran
import com.example.proyectoampliacion.R
import kotlinx.android.synthetic.main.vista_albaranes.view.*
import kotlinx.android.synthetic.main.vista_clientes.view.*

class MiAdaptadorAlbaranes(private val mContext: Context, private val listaAlbaranes: List<Albaran>): ArrayAdapter<Albaran>(mContext,0,listaAlbaranes)  {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        //return super.getView(position, convertView, parent)

        val layout = LayoutInflater.from(mContext).inflate(R.layout.vista_albaranes,parent,false);

        val elementoActual = listaAlbaranes[position];

        layout.tvFecha.text = elementoActual.fecha;
        layout.tvProveedor.text = elementoActual.proveedor;
        layout.tvId.text = elementoActual.id.toString();

        layout.btnContenido.setBackgroundColor(Color.parseColor("#00599f"))

        return layout;
    }
}