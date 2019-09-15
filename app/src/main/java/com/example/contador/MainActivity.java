package com.example.contador;

import android.app.Activity;
import android.os.Bundle;
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
        contador=0;
        //A partir del video 18
        textoResultado=(TextView)findViewById(R.id.contadorTexto);

        //Para que al pulsar el valor de OK en el teclado, el valor
        //que está en el campo de reseteo, suba también como valor de reseteo
        EventoTeclado teclado=new EventoTeclado();
        EditText v_reseteo=(EditText)findViewById(R.id.n_reseteo);
        v_reseteo.setOnEditorActionListener(teclado);

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
