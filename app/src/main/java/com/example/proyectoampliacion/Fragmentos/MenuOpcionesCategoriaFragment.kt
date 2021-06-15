package com.example.proyectoampliacion.Fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import com.example.proyectoampliacion.R
import kotlinx.android.synthetic.main.fragment_menu_principal.*

class MenuOpcionesCategoriaFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_opciones_categoria, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var menu = view.findViewById<GridLayout>(R.id.menuSubGrid);

        arguments?.let {
            val tipoMenu = it?.getInt("tipo")

            when(tipoMenu){

                0->{
                    titulo.text = "Menú Albaranes"
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Menú Albaranes"
                }
                1->{
                    titulo.text = "Menú Almacén"
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Menú Almacén"
                }
                2->{
                    titulo.text = "Menú Clientes"
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Menú Clientes"
                }
                3->{
                    titulo.text = "Menú Delegación"
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Menú Delegación"
                }
                4->{
                    titulo.text = "Menú Empleados"
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Menú Empleados"
                }
                5->{
                    titulo.text = "Menú Facturas"
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Menú Facturas"
                }
                6->{
                    titulo.text = "Menú Partes"
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Menú Partes"
                }
                7->{
                    titulo.text = "Menú Presupuestos"
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Menú Presupuestos"
                }
            }

            setSingleEvent(menu,view,tipoMenu)
        }



    }


    fun setSingleEvent(referencia: GridLayout, view: View, tipo:Int){

        val bundle = Bundle();

        for (i in 0..referencia.childCount - 1){

            var cardView: CardView = referencia.getChildAt(i) as CardView;
            cardView.setOnClickListener { it

                when(i){

                    0->{

                        bundle.putInt("tipo",tipo);
                        Navigation.findNavController(view).navigate(R.id.listarFragment,bundle);
                    }
                    1->{

                        bundle.putInt("tipo",tipo);
                        Navigation.findNavController(view).navigate(R.id.altasFragment,bundle);

                    }
                    2->{

                        bundle.putInt("tipo",tipo);
                        Navigation.findNavController(view).navigate(R.id.listaModificarFragment,bundle);

                    }
                    3->{

                        bundle.putInt("tipo",tipo);
                        Navigation.findNavController(view).navigate(R.id.bajasFragment,bundle);

                    }
                    4->{

                        Navigation.findNavController(view).navigate(R.id.menuPrincipalFragment);
                    }

                }

            }
        }

    }

}