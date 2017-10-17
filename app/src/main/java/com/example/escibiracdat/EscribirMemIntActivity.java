package com.example.escibiracdat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author Elena G (Beelzenef)
 */
public class EscribirMemIntActivity extends AppCompatActivity {

    EditText edT_operando1;
    EditText edT_operando2;

    Button btn_Suma;

    TextView txtV_Resultado;
    TextView txtV_PropiedadesFichero;

    Memoria miMemoria;

    public final static String NOMBREFICHERO = "resultado.txt";
    String textoAEscribir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escribir_mem_int);

        // Identificando controles
        edT_operando1 = (EditText)findViewById(R.id.edT_Operando1);
        edT_operando2 = (EditText)findViewById(R.id.edT_Operando2);
        btn_Suma = (Button) findViewById(R.id.btn_Suma);
        txtV_Resultado = (TextView)findViewById(R.id.txtV_Resultado);
        txtV_PropiedadesFichero = (TextView)findViewById(R.id.txtV_PropiedadesFicheroInt);

        miMemoria = new Memoria(getApplicationContext());
    }

    public void onClik_SumarOperandos(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_Suma:
                Calculando();
                break;
        }
    }

    boolean operadoresNoVacios()
    {
        return !(edT_operando1.getText().length() == 0 || edT_operando2.getText().length() == 0);
    }

    void Calculando() {
        if (operadoresNoVacios()) {
            int resultado;

            int op1 = Integer.parseInt(edT_operando1.getText().toString());
            int op2 = Integer.parseInt(edT_operando2.getText().toString());

            resultado = op1 + op2;
            textoAEscribir = String.valueOf(resultado);
            txtV_Resultado.setText(textoAEscribir);

            EscribiendoMemInt();
        }
    }

    void EscribiendoMemInt()
    {
        if (miMemoria.escribirInterna(NOMBREFICHERO, textoAEscribir, false, "UTF-8"))
            txtV_PropiedadesFichero.setText(miMemoria.mostrarPropiedadesInterna(NOMBREFICHERO));
        else
            txtV_PropiedadesFichero.setText("Error al escribir en el fichero " + NOMBREFICHERO);
    }
}
