package com.example.proyectoampliacion.Adaptadores

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.Navigation
import com.example.proyectoampliacion.Classes_Auxiliares.Delegacion
import com.example.proyectoampliacion.Classes_Auxiliares.DelegacionSeleccionada
import com.example.proyectoampliacion.Classes_Auxiliares.Persona
import com.example.proyectoampliacion.R
import kotlinx.android.synthetic.main.delegacion_item.view.*
import kotlinx.android.synthetic.main.persona_item.view.*
import kotlinx.android.synthetic.main.persona_item.view.btnAnnadirP

class MiAdaptadorDelegacionesConsulta (private val mContext: Context, private val listaDelegaciones: List<Delegacion>): ArrayAdapter<Delegacion>(mContext,0,listaDelegaciones) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        //return super.getView(position, convertView, parent)

        val layout = LayoutInflater.from(mContext).inflate(R.layout.delegacion_item,parent,false);

        val elementoActual = listaDelegaciones[position];

        layout.tvIDireccionDE.text = elementoActual.direccion + " " + elementoActual.provincia;
        layout.tvTelefonodDE.text = elementoActual.telefono;
        layout.tvIDDe.text = elementoActual.id.toString()
        layout.tvNombreDe.text = elementoActual.nombre


        var bundle: Bundle = Bundle();

        layout.btnAnnadirP.setOnClickListener(){ vista ->

            var elegido = DelegacionSeleccionada(listaDelegaciones[position].id.toString(), listaDelegaciones[position].nombre, listaDelegaciones[position].direccion);

            bundle.putString("nombre",elegido.nombre);
            bundle.putString("apellidos",elegido.direccion);
            bundle.putString("idDe",elegido.id);
            bundle.putInt("tipo",4)

            Navigation.findNavController(vista).navigate(R.id.altasFragment,bundle);


        }

        return layout;
    }
}