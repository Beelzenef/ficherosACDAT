package com.example.escibiracdat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * Lanzadera de Activities
 * @author Elena G (Beelzenef)
 */
public class InicioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
    }

    public void onClik_elegirEj(View v) {

        Intent unIntento = null;

        switch (v.getId()) {
            case R.id.btn_EscribirMemInt:
                unIntento = new Intent(InicioActivity.this, EscribirMemIntActivity.class);
                break;
            case R.id.btn_EscribirMemExt:
                unIntento = new Intent(InicioActivity.this, EscribirMemExtActivity.class);
                break;
            case R.id.btn_LeerFicheros:
                unIntento = new Intent(InicioActivity.this, LeerFicherosActivity.class);
                break;
            case R.id.btn_Codificar:
                unIntento = new Intent(InicioActivity.this, CodificarActivity.class);
                break;
        }
        startActivity(unIntento);
    }
}
