package com.example.contador;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    public int contador;

    //A partir del video 18
    TextView textoResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //A partir del video 18
        textoResultado=(TextView)findViewById(R.id.contadorTexto);

        contador=0;
        //Mostramos el valor inicial
        textoResultado.setText(""+contador);

        //Para que al pulsar el valor de OK en el teclado, el valor
        //que está en el campo de reseteo, suba también como valor de reseteo
        EventoTeclado teclado=new EventoTeclado();
        EditText v_reseteo=(EditText)findViewById(R.id.n_reseteo);
        v_reseteo.setOnEditorActionListener(teclado);

    }

    /*
    //Video 56 - Persistencia de datos con Bundle -- Para que se almacene el valor
    //del contador aunque modifiquemos la orientación del dispositivo
    public void onSaveInstanceState (Bundle estado) {
        //Se almacena en el bundle el valor del contador cuando la
        //app cambie de estado
        estado.putInt("cuenta", contador);

        //Almacenamos el bundle creado en la clase padre
        super.onSaveInstanceState(estado);
    }

    //Video 56 - Persistencia de datos con Bundle -- Para recuperar la información
    //almacenada
    public void onRestoreInstanceState (Bundle estado) {
        //Recupero la información del bundle de la clase padre
        super.onRestoreInstanceState(estado);
        //Recupero el valor deseado
        contador=estado.getInt("cuenta");

        //Muestro el valor
        textoResultado.setText(""+contador);
    }
    */

    //Video 57 - Persistencia de datos con SharedPreference
    //Se trabaja sobre el método onPause ya que sobre este método se pasará siempre
    public void onPause() {
        //Acceso al método de la clase padre
        super.onPause();

        //Paso 1-Se obtiene el SharedPreference de nuestra aplicación (contexto propio=this)
        SharedPreferences datos= PreferenceManager.getDefaultSharedPreferences(this);

        //Paso 2-Hacer editable el SharedPreference
        SharedPreferences.Editor miEditor=datos.edit();

        //Paso 3-Establecer la información a almacenar
        miEditor.putInt("cuenta", contador);

        //Paso 4-Transferir la inforamación al objeto SharedPreference
        miEditor.apply();
    }

    //Recuperar la información --> Se trabaja sobre el método onResumen
    public void onResume() {
        //Acceso al método de la clase padre
        super.onResume();

        //Recuperar la información del SharedPreference
        SharedPreferences datos= PreferenceManager.getDefaultSharedPreferences(this);
        contador=datos.getInt("cuenta", 0);

        textoResultado.setText(""+contador);
    }


    class EventoTeclado implements TextView.OnEditorActionListener{
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                reseteaContador(null);
            }
            return false;
        }
    }

    public void incrementaContador (View vista){
        contador++;
       // mostrarResultado();   Utilizado hasta video 18
        textoResultado.setText("" + contador);
    }

    public void decrementaContador (View vista){
        contador--;
        // mostrarResultado(); Utilizado hasta video 18
        //A partir del video 18
        if (contador<0){
            CheckBox negativos=(CheckBox)findViewById(R.id.negativos);
            if (!negativos.isChecked()){
                contador=0;
            }
        }
        textoResultado.setText("" + contador);
    }

    public void reseteaContador (View vista){
       // contador=0; Utilizado hasta video 18
       // mostrarResultado(); Utilizado hasta video 18
        EditText numero_reset=(EditText)findViewById(R.id.n_reseteo);
        try {
            contador = Integer.parseInt(numero_reset.getText().toString());
        } catch (Exception e){
            contador=0;
        }
        numero_reset.setText("");
        textoResultado.setText("" + contador);

        //Forzar a que el dispositivo de entrada de datos sea el teclado
        InputMethodManager miteclado=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        //Ocultar el teclado cuando se pulse el botón de Resetear
        miteclado.hideSoftInputFromWindow(numero_reset.getWindowToken(),0);


    }

    /*Utilizado hasta video 18 --> Luego ya se crea el objeto en la propia clase
    public void mostrarResultado(){
        TextView textoResultado=(TextView)findViewById(R.id.contadorTexto);
        textoResultado.setText("" + contador);
    }*/

}
