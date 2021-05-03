package com.example.proyectoampliacion.Fragmentos.Mantenimiento

import android.app.ActionBar
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.InputType
import android.text.style.BackgroundColorSpan
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.helper.widget.Layer
import androidx.core.view.isVisible
import com.example.proyectoampliacion.R
import kotlinx.android.synthetic.main.fragment_altas.*
import java.util.*


class AltasFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_altas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        arguments?.let {



            when(it.getInt("tipo")){

                0->{
                    tvTitulo.setText("Altas Albaranes")
                }
                1->{
                    tvTitulo.setText("Altas Almacén")
                }
                2->{
                    tvTitulo.setText("Altas Clientes")
                    construirFormClientes();
                }
                3->{
                    tvTitulo.setText("Altas Delegación")
                }
                4->{
                    tvTitulo.setText("Altas Empleados")
                }
                5->{
                    tvTitulo.setText("Altas Facturas")
                }
                6->{
                    tvTitulo.setText("Altas Partes")
                }
                7->{
                    tvTitulo.setText("Altas Presupuestos")
                }
            }

        }



    }

    fun construirFormClientes(){

        val txtNombre:EditText = EditText(this.context);
        val txtApellidos:EditText = EditText(this.context);
        val txtCiudad:EditText = EditText(this.context);
        val txtcPostal:EditText = EditText(this.context);
        val txtDni:EditText = EditText(this.context);
        val txtEmail:EditText = EditText(this.context);
        val txtTelefono:EditText = EditText(this.context);
        val txtNacimiento:EditText = EditText(this.context);
        val txtDireccion:EditText = EditText(this.context);
        val txtProvincia:EditText = EditText(this.context);
        val cbEstado:CheckBox= CheckBox(this.context);
        val btnCancelar:Button = Button(this.context);
        val btnGuardar:Button = Button(this.context);
        val btnLimpiar:Button = Button(this.context);

        val contenedorNombre:LinearLayout = LinearLayout(this.context);
        contenedorNombre.orientation = LinearLayout.HORIZONTAL;
        val contenedorApellidos:LinearLayout = LinearLayout(this.context);
        contenedorApellidos.orientation = LinearLayout.HORIZONTAL;
        val contenedorCiudad:LinearLayout = LinearLayout(this.context);
        contenedorCiudad.orientation = LinearLayout.HORIZONTAL;
        val contenedorcPostal:LinearLayout = LinearLayout(this.context);
        contenedorcPostal.orientation = LinearLayout.HORIZONTAL;
        val contenedorDni:LinearLayout = LinearLayout(this.context);
        contenedorDni.orientation = LinearLayout.HORIZONTAL;
        val contenedorEmail:LinearLayout = LinearLayout(this.context);
        contenedorEmail.orientation = LinearLayout.HORIZONTAL;
        val contenedorTelefono:LinearLayout = LinearLayout(this.context);
        contenedorTelefono.orientation = LinearLayout.HORIZONTAL;
        val contenedorNacimiento:LinearLayout = LinearLayout(this.context);
        contenedorNacimiento.orientation = LinearLayout.HORIZONTAL;
        val contenedorDireccion:LinearLayout = LinearLayout(this.context)
        contenedorDireccion.orientation = LinearLayout.HORIZONTAL;
        val contenedorProvincia:LinearLayout = LinearLayout(this.context);
        contenedorProvincia.orientation = LinearLayout.HORIZONTAL;
        val contenedorEstado:LinearLayout = LinearLayout(this.context);
        contenedorEstado.orientation = LinearLayout.HORIZONTAL;
        val contenedorBotones:LinearLayout = LinearLayout(this.context);
        contenedorBotones.orientation = LinearLayout.HORIZONTAL;

        cbEstado.text = "Alta";

        contenedorNombre.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorNombre.orientation = LinearLayout.HORIZONTAL;
        contenedorApellidos.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorApellidos.orientation = LinearLayout.HORIZONTAL;
        contenedorCiudad.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorCiudad.orientation = LinearLayout.HORIZONTAL;
        contenedorDireccion.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorDireccion.orientation = LinearLayout.HORIZONTAL;
        contenedorDni.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorDni.orientation = LinearLayout.HORIZONTAL;
        contenedorEmail.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorEmail.orientation = LinearLayout.HORIZONTAL;
        contenedorProvincia.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorProvincia.orientation = LinearLayout.HORIZONTAL;
        contenedorTelefono.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorTelefono.orientation = LinearLayout.HORIZONTAL;
        contenedorcPostal.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorcPostal.orientation = LinearLayout.HORIZONTAL;
        contenedorEstado.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorEstado.orientation = LinearLayout.HORIZONTAL;

        txtNombre.hint = "Introduzca Nombre";
        txtApellidos.hint = "Introduzca Apellidos";
        txtcPostal.hint = "Introduzca Codigo Postal ";
        txtDireccion.hint = "Introduzca Dirección"
        txtCiudad.hint = "Introduzca Ciudad";
        txtProvincia.hint = "Introduzca Provincia";
        txtEmail.hint = "Introduzca  Email"
        txtTelefono.hint = "Introduzca Teléfono";
        txtDni.hint = "Introduzca D.N.I";
        txtNacimiento.hint = "Introduza Fecha de Nacimiento";


        txtNombre.width = 800;
        txtNombre.maxLines = 1;
        txtNombre.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtApellidos.width= 800;
        txtApellidos.maxLines = 1
        txtApellidos.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtCiudad.width= 800;
        txtCiudad.maxLines = 1
        txtCiudad.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtDireccion.width= 800;
        txtDireccion.maxLines = 1
        txtDireccion.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtEmail.width= 800;
        txtEmail.maxLines = 1
        txtEmail.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtProvincia.width= 800;
        txtProvincia.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtcPostal.width= 800;
        txtcPostal.maxLines = 1
        txtcPostal.inputType = InputType.TYPE_CLASS_NUMBER;
        txtcPostal.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtTelefono.width= 800
        txtTelefono.maxLines = 1
        txtTelefono.inputType = InputType.TYPE_CLASS_PHONE
        txtTelefono.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtDni.width= 800;
        txtDni.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtNacimiento.width= 800;
        txtNacimiento.maxLines = 1
        txtNacimiento.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtNacimiento.inputType = InputType.TYPE_CLASS_DATETIME;

        btnCancelar.text = "Cancelar"
        btnGuardar.text = "Guardar"
        btnLimpiar.text = "Limpiar"

        contenedorBotones.gravity = Gravity.CENTER
        contenedorBotones.addView(btnGuardar);
        contenedorBotones.addView(btnCancelar);
        contenedorBotones.addView(btnLimpiar)

        contenedorEstado.gravity= Gravity.CENTER;
        contenedorEstado.addView(cbEstado);
        contenedorNombre.gravity= Gravity.CENTER;
        contenedorNombre.addView(txtNombre);
        contenedorApellidos.gravity= Gravity.CENTER;
        contenedorApellidos.addView(txtApellidos);
        contenedorCiudad.gravity= Gravity.CENTER;
        contenedorCiudad.addView(txtCiudad);
        contenedorDireccion.gravity= Gravity.CENTER;
        contenedorDireccion.addView(txtDireccion);
        contenedorDni.gravity= Gravity.CENTER;
        contenedorDni.addView(txtDni);
        contenedorEmail.gravity= Gravity.CENTER;
        contenedorEmail.addView(txtEmail);
        contenedorProvincia.gravity= Gravity.CENTER;
        contenedorProvincia.addView(txtProvincia);
        contenedorcPostal.gravity= Gravity.CENTER;
        contenedorcPostal.addView(txtcPostal);
        contenedorTelefono.gravity= Gravity.CENTER;
        contenedorTelefono.addView(txtTelefono);
        contenedorNacimiento.gravity= Gravity.CENTER;
        contenedorNacimiento.addView(txtNacimiento);

        contenedor.addView(contenedorNombre);
        contenedor.addView(contenedorApellidos);
        contenedor.addView(contenedorDireccion);
        contenedor.addView(contenedorCiudad);
        contenedor.addView(contenedorProvincia);
        contenedor.addView(contenedorEmail);
        contenedor.addView(contenedorTelefono);
        contenedor.addView(contenedorcPostal);
        contenedor.addView(contenedorDni);
        contenedor.addView(contenedorNacimiento);
        contenedor.addView(contenedorEstado);
        contenedor.addView(contenedorBotones);

    }
}