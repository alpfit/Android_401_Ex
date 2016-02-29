package com.and401.tareas.burbuja;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by apillay on 2/28/2016.
 */
public class BurbujaBaseActivity extends Activity {

    // Variables de alcance default
    final static int ALEATORIO = 0;
    final static int UNICA = 1;
    final static int ESTATICO = 2;
    static int tipoVelocidad = ALEATORIO;
    static final String TAG = "BURBUJA";

    @Override
    public void onBackPressed() {
        openOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean menuSelecionado = true;
        switch (item.getItemId()) {
            case R.id.menu_modo_estatico: {
                tipoVelocidad = ESTATICO;
                break;
            }
            case R.id.menu_velociad_unica: {
                tipoVelocidad = UNICA;
                break;
            }
            case R.id.menu_modo_aleatorio: {
                tipoVelocidad = ALEATORIO;
                break;
            }
            case R.id.quit: {
                salirSolicitado();
                break;
            }
            default: {
                menuSelecionado = super.onOptionsItemSelected(item);
                break;
            }
        }
        return menuSelecionado;
    }

    private void salirSolicitado() {
        super.onBackPressed();
    }
}
