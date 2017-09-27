package com.tuxstone.basefinanciera.SplashScreen.ui;

/**
 * Interface usada en la actividad de la Pantalla principal
 */
public interface SplashScreenView {

    /**
     * Metodo para navegar a la ventana inicial
     */
    void navigateToMainScreen();

    /**
     * Metodo para mostrar la barra de progreso
     */
    void showProgress();

    /**
     * Metodo para ocultar la barra de progreso
     */
    void hideProgress();

    /**
     * Metodo para manejar la verificacion exitosa
     */
    void handleVerifySuccess();

    /**
     * Metodo para manejar la verificacion erronea
     */
    void handleVerifyError();

    /**
     * Metodo para manejar la conexion a internet exitosa
     */
    void handleInternetConnectionSuccess();

    /**
     * Metodo para manejar la conexion a internet erronea
     */
    void onInternetConnectionError();

    /**
     * Metodo para manejar la conexion al dispositivo NFC exitosa
     */
    void handleNFCDeviceConnectionSuccess();

    /**
     * Metodo para manejar la conexion al dispositivo NFC erronea
     */
    void handleNFCDeviceConnectionError();

    /**
     * Metodo para manejar la conexion a internet exitosa
     */
    void handleSolicitudRegisterSuccess();

    /**
     * Metodo para manejar el registro de la solicitud erronea
     */
    void handleSolicitudRegisterError();

    /**
     * Metodo para manejar el registro interno de la base de datos exitoso
     */
    void handleInternalRegisterSuccess();

    /**
     * Metodo para manejar el registro interno de la base de datos erroneo
     */
    void handleInternalRegisterError();

    /**
     * Metodo para manejar el estado de solicitud en espera
     */
    void handleSolicitudsStatusStandBy();

    /**
     * Metodo para manejar el estado de solicitud aceptado
     */
    void handleSolicitudStatusAccepted();

    /**
     * Metodo para manejar el estado de solicitud rechazado
     */
    void handleSolicitudStatusRejected();

    /**
     * Metodo para manejar el estado de solicitud error
     */
    void handleSolicitudStatusError();
}
