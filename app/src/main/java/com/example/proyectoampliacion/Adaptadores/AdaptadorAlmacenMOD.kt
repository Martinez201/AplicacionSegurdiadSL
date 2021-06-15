package com.example.proyectoampliacion.Adaptadores

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.Navigation
import com.example.proyectoampliacion.Classes_Auxiliares.Almacen
import com.example.proyectoampliacion.R
import kotlinx.android.synthetic.main.vista_mod_productos.view.*

class AdaptadorAlmacenMOD (private val mContext: Context, private val listaProductos: List<Almacen>):
    ArrayAdapter<Almacen>(mContext,0,listaProductos) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        //return super.getView(position, convertView, parent)

        val layout = LayoutInflater.from(mContext).inflate(R.layout.vista_mod_productos,parent,false);

        val elementoActual = listaProductos[position];

        layout.tvNombrePROMOD.text = elementoActual.nombre;
        layout.tvPrecioPROMOD.text = elementoActual.precio.toString();
        layout.tvCantidadPROMOD.text = elementoActual.cantidad.toString();
        layout.tvTipoPROMOD.text = elementoActual.tipo;


        layout.btnEditarPROMOD.setOnClickListener(){

            var bundle = Bundle();

            bundle.putInt("elemento",elementoActual.id)
            bundle.putInt("tipo_formulario",1)

            Navigation.findNavController(it).navigate(R.id.modificarFragment,bundle)

        }

        return layout;
    }
}