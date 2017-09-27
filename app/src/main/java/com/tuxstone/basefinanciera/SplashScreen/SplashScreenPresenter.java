package com.tuxstone.basefinanciera.SplashScreen;

import android.content.Context;

import com.tuxstone.basefinanciera.SplashScreen.events.SplashScreenEvent;

public interface SplashScreenPresenter {

    /**
     * Metodo para la creacion del presentador
     */
    void onCreate();

    /**
     * Metodo para la destruccion del presentador
     */
    void onDestroy();

    /**
     * Metodo para la verificacion de los datos
     */
    void validateAccess(Context context);

    /**
     * Metodo para recibir los eventos generados
     *
     * @param splashScreenEvent
     */
    void onEventMainThread(SplashScreenEvent splashScreenEvent);

}
