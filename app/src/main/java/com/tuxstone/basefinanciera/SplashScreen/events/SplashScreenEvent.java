package com.tuxstone.basefinanciera.SplashScreen.events;

/**
 * Clase que maneja los eventos de la Pantalla de Inicio
 */
public class SplashScreenEvent {

    /**
     * Eventos asociados a la Pantalla de Inicio
     */
    // Eventos de verificacion de disponibilidad del sistema
    public final static int onVerifySuccess = 0;
    public final static int onVerifyError = 1;

    // Eventos de verificacion de conexión a internet
    public final static int onInternetConnectionSuccess = 2;
    public final static int onInternetConnectionError = 3;

    // Eventos de verificacion de conexión al dispositivo nfc
    public final static int onNFCDeviceConnectionSuccess = 4;
    public final static int onNFCDeviceConnectionError = 5;

    // Eventos de verificacion de registro de solicitud
    public final static int onSolicitudRegisterSuccess = 6;
    public final static int onSolicitudRegisterError = 7;

    // Eventos de verificacion de registro interno
    public final static int onInternalRegisterSuccess = 8;
    public final static int onInternalRegisterError = 9;

    // Eventos de verificacion de estado de solicitud
    public final static int onSolicitudStatusStandBy = 10;
    public final static int onSolicitudStatusAccepted = 11;
    public final static int onSolicitudStatusRejected = 12;
    public final static int onSolicitudStatusError = 13;

    // Variable que maneja los tipos de eventos
    private int eventType;

    // Variable que maneja los mensajes de error de los eventos
    private String errorMessage;

    //Getters y Setters de la clase

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
