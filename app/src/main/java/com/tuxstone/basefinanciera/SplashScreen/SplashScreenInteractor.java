package com.tuxstone.basefinanciera.SplashScreen;

import android.content.Context;

public interface SplashScreenInteractor {

    /**
     * Metodo que verifica:
     * - Existencia de datos
     * - Validez de datos
     *
     * @param context
     */
    void validateAccess(Context context);

}
