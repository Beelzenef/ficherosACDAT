package com.example.escibiracdat.operamemoria;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import com.example.escibiracdat.Resultado;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Leyendo de memoria interna y memoria externa
 *
 * @author Elena G (Beelzenef)
 */

public class LeerMemorias {

    private Context contexto;

    // Constructor
    public LeerMemorias(Context c) {
        this.contexto = c;
    }

    // De memoria externa

    public Resultado leerExterna(String fichero, String codigo) {
        File miFichero, tarjeta;
//tarjeta = Environment.getExternalStoragePublicDirectory("datos/programas/");
//tarjeta.mkdirs();
        tarjeta = Environment.getExternalStorageDirectory();
        miFichero = new File(tarjeta.getAbsolutePath(), fichero);
        return leer(miFichero, codigo);
    }

    // De memoria interna

    public Resultado leerInterna(String fichero, String codigo) {
        File miFichero;
        //mifichero = new File(getApplicationContext().getFilesDir(), nombreFichero);
        miFichero = new File(contexto.getFilesDir(), fichero);
        return leer(miFichero, codigo);
    }

    private Resultado leer(File fichero, String codigo) {
        FileInputStream fis = null;
        InputStreamReader isw = null;
        BufferedReader in = null;
        //String linea;
        StringBuilder miCadena = new StringBuilder();
        Resultado resultado = new Resultado();
        int n;
        resultado.setCodigo(true);
        try {
            fis = new FileInputStream(fichero);
            isw = new InputStreamReader(fis, codigo);
            in = new BufferedReader(isw);
            while ((n = in.read()) != -1)
                miCadena.append((char) n);
            //while ((linea = in.readLine()) != null)
            //miCadena.append(linea).append('\n');
        } catch (IOException e) {

            //Log.e("Error", e.getMessage());
            resultado.setCodigo(false);
            resultado.setMensaje(e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                    resultado.setContenido(miCadena.toString());
                }
            } catch (IOException e) {
                //Log.e("Error al cerrar", e.getMessage());
                resultado.setCodigo(false);
                resultado.setMensaje(e.getMessage());
            }
        }
        return resultado;
    }

    public Resultado leerRaw(String fichero) {
        //fichero tendrá el nombre del fichero raw sin la extensión
        InputStream is = null;
        StringBuilder miCadena = new StringBuilder();
        int n;
        Resultado resultado = new Resultado();
        resultado.setCodigo(true);
        try {
//is = contexto.getResources().openRawResource(R.raw.numero);
            is = contexto.getResources().openRawResource(
                    contexto.getResources().getIdentifier(fichero, "raw", contexto.getPackageName()));
            while ((n = is.read()) != -1) {
                miCadena.append((char) n);
            }
        } catch (Exception e) {
            //Log.e("Error", e.getMessage());
            resultado.setCodigo(false);
            resultado.setMensaje(e.getMessage());
        } finally {
            try {
                if (is != null) {
                    is.close();
                    resultado.setContenido(miCadena.toString());
                }
            } catch (Exception e) {
                //Log.e("Error al cerrar", e.getMessage());
                resultado.setCodigo(false);
                resultado.setMensaje(e.getMessage());
            }
        }
        return resultado;
    }

    public Resultado leerAsset(String fichero) {
        AssetManager am = contexto.getAssets();
        InputStream is = null;
        StringBuilder miCadena = new StringBuilder();
        int n;
        Resultado resultado = new Resultado();
        resultado.setCodigo(true);
        try {
            is = am.open(fichero);
            while ((n = is.read()) != -1) {
                miCadena.append((char) n);
            }
        } catch (IOException e) {
            //Log.e("Error", e.getMessage());
            resultado.setCodigo(false);
            resultado.setMensaje(e.getMessage());
        } finally {
            try {
                if (is != null) {
                    is.close();
                    resultado.setContenido(miCadena.toString());
                }
            } catch (Exception e) {
                //Log.e("Error al cerrar", e.getMessage());
                resultado.setCodigo(false);
                resultado.setMensaje(e.getMessage());
            }
        }
        return resultado;
    }
}
