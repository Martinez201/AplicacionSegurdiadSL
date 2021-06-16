package com.example.proyectoampliacion.Adaptadores

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.Navigation
import com.example.proyectoampliacion.Classes_Auxiliares.Factura
import com.example.proyectoampliacion.R
import kotlinx.android.synthetic.main.vista_baj_factura.view.*
import kotlinx.android.synthetic.main.vista_mod_factura.view.*

class AdaptadorFacturasBAJ (private val mContext: Context, private val listaFacturas: List<Factura>): ArrayAdapter<Factura>(mContext,0,listaFacturas) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        //return super.getView(position, convertView, parent)

        val layout =
            LayoutInflater.from(mContext).inflate(R.layout.vista_baj_factura, parent, false);

        val elementoActual = listaFacturas[position];

        layout.tvClienteFACBAJ.text = elementoActual.cliente.toString();
        layout.tvEmpleadoFACBAJ.text = elementoActual.empleado.toString();
        layout.tvEmisionFACBAJ.text = elementoActual.fecha
        layout.tvConceptoFACBAJ.text = elementoActual.concepto;

        layout.btnEditarFACBAJ.setBackgroundColor(Color.RED)

        layout.btnEditarFACBAJ.setOnClickListener() {

            var bundle = Bundle();

            bundle.putInt("elemento", elementoActual.id)

            Navigation.findNavController(it).navigate(R.id.modificarFragment, bundle)

        }


        return layout;
    }
}