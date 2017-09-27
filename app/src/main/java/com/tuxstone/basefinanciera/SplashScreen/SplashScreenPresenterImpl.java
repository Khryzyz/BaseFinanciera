package com.tuxstone.basefinanciera.SplashScreen;

import android.content.Context;

import com.tuxstone.basefinanciera.SplashScreen.events.SplashScreenEvent;
import com.tuxstone.basefinanciera.SplashScreen.ui.SplashScreenView;
import com.tuxstone.basefinanciera.lib.EventBus;
import com.tuxstone.basefinanciera.lib.GreenRobotEventBus;

public class SplashScreenPresenterImpl implements SplashScreenPresenter {

    /**
     * #############################################################################################
     * Declaracion de componentes y variables
     * #############################################################################################
     */
    //Declaracion del bus de eventos
    EventBus eventBus;

    /**
     * #############################################################################################
     * Instanciamientos de las clases
     * #############################################################################################
     */
    //Instanciamiento de la interface SplashScreenView
    private SplashScreenView splashScreenView;

    //Instanciamiento de la interface SplashScreenInteractor
    private SplashScreenInteractor splashScreenInteractor;

    /**
     * #############################################################################################
     * Constructor de la clase
     * #############################################################################################
     *
     * @param splashScreenView
     */
    public SplashScreenPresenterImpl(SplashScreenView splashScreenView) {
        this.splashScreenView = splashScreenView;
        this.splashScreenInteractor = new SplashScreenInteractorImpl();
        this.eventBus = GreenRobotEventBus.getInstance();
    }

    /**
     * #############################################################################################
     * Metodos sobrecargados de la interface
     * #############################################################################################
     */

    /**
     * Sobrecarga del metodo onCreate de la interface SplashScreenPresenter "crear" el registro al bus de eventos
     */
    @Override
    public void onCreate() {

        eventBus.register(this);

    }

    /**
     * Sobrecarga del metodo onDestroy de la interface SplashScreenPresenter para "eliminar"  el registro al bus de eventos
     */
    @Override
    public void onDestroy() {
        splashScreenView = null;
        eventBus.unregister(this);
    }

    /**
     * Metodo para la verificacion de los datos
     */
    @Override
    public void validateAccess(Context context) {
        if (splashScreenView != null) {
            splashScreenView.showProgress();
            splashScreenInteractor.validateAccess(context);
        }
    }

    /**
     * Sobrecarga del metodo onEventMainThread de la interface SplashScreenPresenter para el manejo de eventos
     *
     * @param splashScreenEvent
     */
    @Override
    public void onEventMainThread(SplashScreenEvent splashScreenEvent) {
        switch (splashScreenEvent.getEventType()) {

            case SplashScreenEvent.onVerifySuccess:
                onVerifySuccess();
                break;

            case SplashScreenEvent.onVerifyError:
                onVerifyError(splashScreenEvent.getErrorMessage());
                break;

            case SplashScreenEvent.onInternetConnectionSuccess:
                onInternetConnectionSuccess();
                break;

            case SplashScreenEvent.onInternetConnectionError:
                onInternetConnectionError(splashScreenEvent.getErrorMessage());
                break;

            case SplashScreenEvent.onNFCDeviceConnectionSuccess:
                onNFCDeviceConnectionSuccess();
                break;

            case SplashScreenEvent.onNFCDeviceConnectionError:
                onNFCDeviceConnectionError(splashScreenEvent.getErrorMessage());
                break;

            case SplashScreenEvent.onSolicitudRegisterSuccess:
                onSolicitudRegisterSuccess();
                break;

            case SplashScreenEvent.onSolicitudRegisterError:
                onSolicitudRegisterError(splashScreenEvent.getErrorMessage());
                break;

            case SplashScreenEvent.onInternalRegisterSuccess:
                onInternalRegisterSuccess();
                break;

            case SplashScreenEvent.onInternalRegisterError:
                onInternalRegisterError(splashScreenEvent.getErrorMessage());
                break;

            case SplashScreenEvent.onSolicitudStatusStandBy:
                onSolicitudStatusStandBy();
                break;

            case SplashScreenEvent.onSolicitudStatusAccepted:
                onSolicitudStatusAccepted();
                break;

            case SplashScreenEvent.onSolicitudStatusRejected:
                onSolicitudStatusRejected();
                break;

            case SplashScreenEvent.onSolicitudStatusError:
                onSolicitudStatusError();
                break;

        }
    }

    /**
     * #############################################################################################
     * Metodo propios de la clase
     * #############################################################################################
     */

    /**
     * Metodo para manejar la verificacion exitosa
     */
    private void onVerifySuccess() {
        if (splashScreenView != null) {
            splashScreenView.handleVerifySuccess();
            splashScreenView.hideProgress();
        }
    }

    /**
     * Metodo para manejar la verificacion erronea
     */
    private void onVerifyError(String errorMessage) {
        if (splashScreenView != null) {
            splashScreenView.handleVerifyError();
            splashScreenView.hideProgress();
        }
    }

    /**
     * Metodo para manejar la conexion a internet exitosa
     */
    private void onInternetConnectionSuccess() {
        if (splashScreenView != null) {
            splashScreenView.handleInternetConnectionSuccess();
        }
    }

    /**
     * Metodo para manejar la conexion a internet erronea
     */
    private void onInternetConnectionError(String errorMessage) {
        if (splashScreenView != null) {
            splashScreenView.onInternetConnectionError();
            onVerifyError(errorMessage);
        }
    }

    /**
     * Metodo para manejar la conexion al dispositivo NFC exitosa
     */
    private void onNFCDeviceConnectionSuccess() {
        if (splashScreenView != null) {
            splashScreenView.handleNFCDeviceConnectionSuccess();
        }
    }

    /**
     * Metodo para manejar la conexion al dispositivo NFC erronea
     */
    private void onNFCDeviceConnectionError(String errorMessage) {
        if (splashScreenView != null) {
            splashScreenView.handleNFCDeviceConnectionError();
            onVerifyError(errorMessage);
        }
    }

    /**
     * Metodo para manejar el registro de la solicitud exitosa
     */
    private void onSolicitudRegisterSuccess() {
        if (splashScreenView != null) {
            splashScreenView.handleSolicitudRegisterSuccess();
        }
    }

    /**
     * Metodo para manejar el registro de la solicitud erronea
     *
     * @param errorMessage
     */
    private void onSolicitudRegisterError(String errorMessage) {
        if (splashScreenView != null) {
            splashScreenView.handleSolicitudRegisterError();
        }
    }

    /**
     * Metodo para manejar el registro interno de la base de datos exitoso
     */
    private void onInternalRegisterSuccess() {
        if (splashScreenView != null) {
            splashScreenView.handleInternalRegisterSuccess();
        }
    }

    /**
     * Metodo para manejar el registro interno de la base de datos erroneo
     *
     * @param errorMessage
     */
    private void onInternalRegisterError(String errorMessage) {
        if (splashScreenView != null) {
            splashScreenView.handleInternalRegisterError();
        }
    }

    /**
     * Metodo para manejar el estado de solicitud en espera
     */
    private void onSolicitudStatusStandBy() {
        if (splashScreenView != null) {
            splashScreenView.handleSolicitudsStatusStandBy();
        }
    }

    /**
     * Metodo para manejar el estado de solicitud aceptado
     */
    private void onSolicitudStatusAccepted() {
        if (splashScreenView != null) {
            splashScreenView.handleSolicitudStatusAccepted();
        }
    }

    /**
     * Metodo para manejar el estado de solicitud rechazado
     */
    private void onSolicitudStatusRejected() {
        if (splashScreenView != null) {
            splashScreenView.handleSolicitudStatusRejected();
        }
    }

    /**
     * Metodo para manejar el estado de solicitud rechazado
     */
    private void onSolicitudStatusError() {
        if (splashScreenView != null) {
            splashScreenView.handleSolicitudStatusError();
        }
    }
}
