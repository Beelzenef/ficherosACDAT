package com.example.escibiracdat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.escibiracdat.operamemoria.EscribirMemorias;
import com.example.escibiracdat.operamemoria.LeerMemorias;

/**
 * @author Elena G (Beelzenef)
 */
public class LeerFicherosActivity extends AppCompatActivity {

    EditText edT_LeerRaw;
    EditText edT_LeerAssets;
    EditText edT_LeerMemoriaInt;
    EditText edT_LeerMemoriaExt;

    TextView txtV_Resultado;

    public static final String NUMERO = "numeros";
    public static final String VALOR = "valor.txt";
    public static final String DATO = "dato.txt";
    public static final String DATO_SD = "dato_sd.txt";
    public static final String OPERACIONES = "operaciones.txt";
    public static final String CODIFICACION = "UTF-8";

    LeerMemorias leerMemorias;
    EscribirMemorias escribirMemorias;

    Resultado resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leer_ficheros);

        leerMemorias = new LeerMemorias(getApplicationContext());
        escribirMemorias = new EscribirMemorias(getApplicationContext());

        iniciar();
    }

    private void iniciar() {

        edT_LeerRaw = (EditText) findViewById(R.id.edT_LeerRaw);
        edT_LeerAssets = (EditText) findViewById(R.id.edT_LeerAssets);
        edT_LeerMemoriaInt = (EditText) findViewById(R.id.edT_LeerMemoriaInt);
        edT_LeerMemoriaExt = (EditText) findViewById(R.id.edT_LeerMemoriaExt);

        txtV_Resultado = (TextView) findViewById(R.id.txtV_Resultado);

        LeyendoDeRaw();
        LeyendoDeAssets();
        LeyendoDeInterna();
        LeyendodeSD();

        // Escribiendo en memoria interna para después leer
        if (escribirMemorias.escribirInterna(DATO, "7", false, CODIFICACION))
            LeyendoDeInterna();
        else {
            Toast.makeText(getApplicationContext(), "Error al escribir en memoria interna", Toast.LENGTH_SHORT).show();
        }

        // Escribiendo en memoria externa para después leer
        if (escribirMemorias.disponibleEscritura()) {
            if (escribirMemorias.escribirExterna(DATO_SD, "4", false, CODIFICACION))
                LeyendodeSD();
            else {
                Toast.makeText(getApplicationContext(), "Error al escribir en memoria externa", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void LeyendoDeAssets() {
        resultado = leerMemorias.leerAsset(VALOR);

        if (resultado.getCodigo()) {
            edT_LeerAssets.setText(resultado.getContenido());
        }
        else {
            edT_LeerAssets.setText("0");
            Toast.makeText(getApplicationContext(), "Error al leer de assets", Toast.LENGTH_SHORT).show();
        }
    }

    private void LeyendoDeInterna() {
        resultado = leerMemorias.leerInterna(DATO, CODIFICACION);

        if (resultado.getCodigo()) {
            edT_LeerMemoriaInt.setText(resultado.getContenido());
        }
        else {
            edT_LeerMemoriaInt.setText("0");
            Toast.makeText(getApplicationContext(), "Error al leer de memoria interna", Toast.LENGTH_SHORT).show();
        }
    }

    private void LeyendodeSD() {
        resultado = leerMemorias.leerInterna(DATO_SD, CODIFICACION);

        if (resultado.getCodigo()) {
            edT_LeerMemoriaExt.setText(resultado.getContenido());
        }
        else {
            edT_LeerMemoriaExt.setText("0");
            Toast.makeText(getApplicationContext(), "Error al leer de memoria externa", Toast.LENGTH_SHORT).show();
        }
    }

    private void LeyendoDeRaw() {
        resultado = leerMemorias.leerRaw(NUMERO);

        if (resultado.getCodigo()) {
            edT_LeerRaw.setText(resultado.getContenido());
        }
        else {
            edT_LeerRaw.setText("0");
            Toast.makeText(getApplicationContext(), "Error al leer de raw/", Toast.LENGTH_SHORT).show();
        }
    }

    private void Suma() {

        int cantidad = 0;
        String operacion;
        String mensaje;

        String op1 = edT_LeerRaw.getText().toString();
        String op2 = edT_LeerAssets.getText().toString();
        String op3 = edT_LeerMemoriaInt.getText().toString();
        String op4 = edT_LeerMemoriaExt.getText().toString();

        try {
            cantidad = Integer.parseInt(op1) + Integer.parseInt(op2) + Integer.parseInt(op3) + Integer.parseInt(op4);
            operacion = op1 + "+" + op2 + "+" + op3 + "+" + op4 + "=" + String.valueOf(cantidad);
        } catch (NumberFormatException e) {

            Log.e("Error", e.getMessage());
            operacion = "0";
            mensaje = e.getMessage();

        }

        txtV_Resultado.setText("Resultado de la operacion: " + String.valueOf(cantidad));
        if (escribirMemorias.disponibleEscritura()) {
            if (escribirMemorias.escribirExterna(OPERACIONES, operacion + '\n', true, CODIFICACION))
                mensaje = "Operación escrita correctamente";
            else
                mensaje = "Error al escribir en la memoria externa";
        }
        else {
            mensaje = "Memoria externa no disponible";
        }

        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();

    }

    public void onClick_SumaLeerFicheros(View v) {
        switch (v.getId()) {
            case R.id.btn_SumaLeerFicheros:
                Suma();
                break;
        }
    }
}
