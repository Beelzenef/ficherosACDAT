package com.example.escibiracdat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.escibiracdat.operamemoria.EscribirMemorias;
import com.example.escibiracdat.operamemoria.LeerMemorias;

/**
 * Distintas codificaciones
 *
 * @author Elena G (Beelzenef)
 */
public class CodificarActivity extends AppCompatActivity {

    EditText edT_OperacionesCodificar;
    EditText edT_FicheroCodificar;
    EditText edT_NuevoFicheroCodificar;

    Button btn_LeerCodificar;
    Button btn_GuardarCodificar;

    RadioButton rdB_UTF8;
    RadioButton rdB_UTF16;
    RadioButton rdB_ISO;

    EscribirMemorias escribirMemorias;
    LeerMemorias leerMemorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codificar);

        inicializarWidgets();

        leerMemorias = new LeerMemorias(this);
        escribirMemorias = new EscribirMemorias(this);

    }

    private void inicializarWidgets() {
        edT_FicheroCodificar = (EditText) findViewById(R.id.edT_FicheroCodificar);
        edT_NuevoFicheroCodificar = (EditText) findViewById(R.id.edT_NuevoFicheroCodificar);
        edT_OperacionesCodificar = (EditText) findViewById(R.id.edT_OperacionesCodificar);

        btn_LeerCodificar = (Button) findViewById(R.id.btn_LeerCodificar);
        btn_GuardarCodificar = (Button) findViewById(R.id.btn_GuardarCodificar);

        rdB_UTF8 = (RadioButton) findViewById(R.id.rdB_UTF8);
        rdB_UTF16 = (RadioButton) findViewById(R.id.rdB_UTF16);
        rdB_ISO = (RadioButton) findViewById(R.id.rdB_ISO8859);
    }

    public void onClick_Codificando(View v) {
        String ficheroALeer = edT_FicheroCodificar.getText().toString();
        String nuevoFicheroAEscribir = edT_NuevoFicheroCodificar.getText().toString();

        String contenidoOperaciones = edT_OperacionesCodificar.getText().toString();

        String msg = ":D";
        String codificacionElegida = "UTF-8";

        Resultado resultado;


        if (rdB_UTF8.isChecked())
            codificacionElegida = "UTF-8";
        if (rdB_UTF16.isChecked())
            codificacionElegida = "UTF-16";
        if (rdB_UTF16.isChecked())
            codificacionElegida = "ISO-8859-15";

        switch (v.getId()) {
            case R.id.btn_LeerCodificar:
                resultado = leerMemorias.leerExterna(ficheroALeer, codificacionElegida);
                if (resultado.getCodigo()) {
                    edT_OperacionesCodificar.setText(resultado.getContenido());
                }
                else
                    msg = "Error al leer el fichero " + ficheroALeer;
                break;
            case R.id.btn_GuardarCodificar:
                if (nuevoFicheroAEscribir.isEmpty())
                    msg = "Ruta vacía para escribir";
                else {
                    if (escribirMemorias.disponibleEscritura()) {
                        if (escribirMemorias.escribirExterna(nuevoFicheroAEscribir, contenidoOperaciones, false, codificacionElegida))
                            msg = "Escribiendo correctamente";
                        else
                            msg = "Error al escribir el fichero";
                    }
                    else {
                        msg = "Memoria externa no disponible";
                    }
                }
                break;
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
