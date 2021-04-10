package com.example.proyectoampliacion.Fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.proyectoampliacion.R
import kotlinx.android.synthetic.main.fragment_inicio.*

class InicioFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inicio, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        btnInicio.setOnClickListener { it-> view

            Navigation.findNavController(view).navigate(R.id.menuPrincipalFragment);

        }

    }


}