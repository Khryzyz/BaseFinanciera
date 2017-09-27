package com.tuxstone.basefinanciera.SplashScreen.ui;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tuxstone.basefinanciera.R;
import com.tuxstone.basefinanciera.SplashScreen.SplashScreenPresenter;
import com.tuxstone.basefinanciera.SplashScreen.SplashScreenPresenterImpl;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_splash_screen)
public class SplashScreenActivity extends Activity implements SplashScreenView {

    /**
     * #############################################################################################
     * Declaracion de componentes y variables
     * #############################################################################################
     */
    //Declaracion de los Contoles
    @ViewById
    TextView txvSplashScreenInfo;
    @ViewById
    ProgressBar pgbLoadingSplashScreen;

    /**
     * #############################################################################################
     * Instanciamientos de las clases
     * #############################################################################################
     */
    //Instanciamiento de la interface SplashScreenPresenter
    private SplashScreenPresenter splashScreenPresenter;

    /**
     * #############################################################################################
     * Constructor  de  la clase
     * #############################################################################################
     */
    @AfterViews
    void SplashInit() {

        // Metodo para colocar la orientacion de la app
        setOrientation();

        /**
         * Instanciamiento e inicializacion del presentador
         */
        splashScreenPresenter = new SplashScreenPresenterImpl(this);

        /**
         * Llamada al metodo onCreate del presentador para el registro del bus de datos
         */
        splashScreenPresenter.onCreate();

        /**
         * Llamada al metodo validateAcces del presentador que valida:
         *  - Conexion a internet
         *  - Existencia datos en DB interna
         *  - Coherencia de datos con el servidor
         */
        splashScreenPresenter.validateAccess(this);

    }

    /**
     * #############################################################################################
     * Metodos sobrecargados del sistema
     * #############################################################################################
     */

    /**
     * Metodo sobrecargado de la vista para la destruccion de la activity
     */
    @Override
    public void onDestroy() {
        splashScreenPresenter.onDestroy();
        super.onDestroy();
    }


    private void jsdnjkd() {
    }
    /**
     * #############################################################################################
     * Metodos sobrecargados de la interface
     * #############################################################################################
     */

    /**
     * Metodo para navegar a la ventana inicial
     */
    @Override
    public void navigateToMainScreen() {
        txvSplashScreenInfo.setText(txvSplashScreenInfo.getText() +
                "\n Aqui se muestra la pantalla principal"
        );

        hideProgress();
    }

    /**
     * Metodo para mostrar la barra de progreso
     */
    @Override
    public void showProgress() {
        // Muesra la barra  de progreso
        pgbLoadingSplashScreen.setVisibility(View.VISIBLE);
    }

    /**
     * Metodo para ocultar la barra de progreso
     */
    @Override
    public void hideProgress() {
        //Oculta la barra de progreso
        pgbLoadingSplashScreen.setVisibility(View.GONE);
    }

    /**
     * Metodo para manejar la verificacion exitosa
     */
    @Override
    public void handleVerifySuccess() {
        txvSplashScreenInfo.setText(txvSplashScreenInfo.getText() +
                "\n" +
                getString(R.string.splash_message_verify_success)
        );

        hideProgress();

    }

    /**
     * Metodo para manejar la verificacion erronea
     */
    @Override
    public void handleVerifyError() {
        txvSplashScreenInfo.setText(txvSplashScreenInfo.getText() +
                "\n" +
                getString(R.string.splash_message_verify_error)
        );

        hideProgress();

    }

    /**
     * Metodo para manejar la conexion a internet exitosa
     */
    @Override
    public void handleInternetConnectionSuccess() {
        txvSplashScreenInfo.setText(txvSplashScreenInfo.getText() +
                "\n" +
                getString(R.string.splash_message_internet_success)
        );
    }

    /**
     * Metodo para manejar la conexion a internet erronea
     */
    @Override
    public void onInternetConnectionError() {
        txvSplashScreenInfo.setText(txvSplashScreenInfo.getText() +
                "\n" +
                getString(R.string.splash_message_internet_error)
        );
        handleVerifyError();
    }

    /**
     * Metodo para manejar la conexion a internet exitosa
     */
    @Override
    public void handleNFCDeviceConnectionSuccess() {
        txvSplashScreenInfo.setText(txvSplashScreenInfo.getText() +
                "\n" +
                getString(R.string.splash_message_nfc_device_success)
        );
    }

    /**
     * Metodo para manejar la conexion a internet erronea
     */
    @Override
    public void handleNFCDeviceConnectionError() {
        txvSplashScreenInfo.setText(txvSplashScreenInfo.getText() +
                "\n" +
                getString(R.string.splash_message_nfc_device_error)
        );
        handleVerifyError();
    }

    /**
     * Metodo para manejar el registro de la solicitud exitosa
     */
    @Override
    public void handleSolicitudRegisterSuccess() {
        txvSplashScreenInfo.setText(txvSplashScreenInfo.getText() +
                "\n" +
                getString(R.string.splash_message_register_solicitud_success)
        );
    }

    /**
     * Metodo para manejar el registro de la solicitud erronea
     */
    @Override
    public void handleSolicitudRegisterError() {
        txvSplashScreenInfo.setText(txvSplashScreenInfo.getText() +
                "\n" +
                getString(R.string.splash_message_register_solicitud_error)
        );
        handleVerifyError();
    }

    /**
     * Metodo para manejar el registro interno de la base de datos exitoso
     */
    @Override
    public void handleInternalRegisterSuccess() {
        txvSplashScreenInfo.setText(txvSplashScreenInfo.getText() +
                "\n" +
                getString(R.string.splash_message_register_internal_success)
        );
    }

    /**
     * Metodo para manejar el registro interno de la base de datos erroneo
     */
    @Override
    public void handleInternalRegisterError() {
        txvSplashScreenInfo.setText(txvSplashScreenInfo.getText() +
                "\n" +
                getString(R.string.splash_message_register_internal_error)
        );
        handleVerifyError();
    }

    /**
     * Metodo para manejar el estado de solicitud en espera
     */
    @Override
    public void handleSolicitudsStatusStandBy() {
        txvSplashScreenInfo.setText(txvSplashScreenInfo.getText() +
                "\n" +
                getString(R.string.splash_message_solicitud_status_stand_by)
        );

        handleVerifySuccess();

    }

    /**
     * Metodo para manejar el estado de solicitud aceptado
     */
    @Override
    public void handleSolicitudStatusAccepted() {

        txvSplashScreenInfo.setText(txvSplashScreenInfo.getText() +
                "\n" +
                getString(R.string.splash_message_solicitud_status_accepted)
        );

        navigateToMainScreen();

    }

    /**
     * Metodo para manejar el estado de solicitud rechazado
     */
    @Override
    public void handleSolicitudStatusRejected() {

        txvSplashScreenInfo.setText(txvSplashScreenInfo.getText() +
                "\n" +
                getString(R.string.splash_message_solicitud_status_rejected)
        );

        handleVerifyError();

    }

    /**
     * Metodo para manejar el estado de solicitud error
     */
    @Override
    public void handleSolicitudStatusError() {

        txvSplashScreenInfo.setText(txvSplashScreenInfo.getText() +
                "\n" +
                getString(R.string.splash_message_solicitud_status_error)
        );

        handleVerifyError();

    }

    /**
     * #############################################################################################
     * Metodo propios de la clase
     * #############################################################################################
     */

    /**
     * Metodo que coloca la orientacion de la App de forma predeterminada
     */
    private void setOrientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

}
