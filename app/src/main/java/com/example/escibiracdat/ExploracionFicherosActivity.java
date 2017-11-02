package com.example.escibiracdat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ExploracionFicherosActivity extends AppCompatActivity {

    private static final int ABRIRFICHERO_REQUEST_CODE = 1;
    private TextView txtV_VerFicheros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exploracion_ficheros);
        txtV_VerFicheros = (TextView) findViewById(R.id.txtV_VerFicheros);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ABRIRFICHERO_REQUEST_CODE)
            if (resultCode == RESULT_OK) {
                // Mostramos en la etiqueta la ruta del archivo seleccionado
                String ruta = data.getData().getPath();
                txtV_VerFicheros.setText(ruta);
            }
            else
                Toast.makeText(this, "Error: " + resultCode, Toast.LENGTH_SHORT).show();
    }

    //region onClick
    public void onClick_ExplorarFiles(View v) {

        if (v.getId() == R.id.btn_ExplorarFicheros) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("file/*");
            if (intent.resolveActivity(getPackageManager()) != null)
                startActivityForResult(intent, ABRIRFICHERO_REQUEST_CODE);
            else
                //informar que no hay ninguna aplicación para manejar ficheros
                Toast.makeText(this, "No hay aplicación para manejar ficheros", Toast.LENGTH_SHORT).show();
        }
    }
    //endregion

}

