package com.example.proyectoampliacion.Fragmentos.Mantenimiento

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyectoampliacion.R
import kotlinx.android.synthetic.main.fragment_bajas.*
import kotlinx.android.synthetic.main.nav_header_main.*


class BajasFragment : Fragment() {
   

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
 
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bajas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        arguments?.let {

            prueba.setText("TIPO ==> " + it.getInt("tipo"))

        }
    }

}