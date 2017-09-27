package com.tuxstone.basefinanciera.SplashScreen;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.JsonObject;
import com.tuxstone.basefinanciera.SplashScreen.events.SplashScreenEvent;
import com.tuxstone.basefinanciera.database.AppDatabase;
import com.tuxstone.basefinanciera.database.DatabaseManager;
import com.tuxstone.basefinanciera.global.InfoGlobalTransaccion;
import com.tuxstone.basefinanciera.global.InfoTransaccion;
import com.tuxstone.basefinanciera.lib.EventBus;
import com.tuxstone.basefinanciera.lib.GreenRobotEventBus;
import com.tuxstone.basefinanciera.lib.VolleyTransaction;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.UUID;

public class SplashScreenRepositoryImpl implements SplashScreenRepository {

    /**
     * #############################################################################################
     * Declaracion de componentes y variables
     * #############################################################################################
     */

    String cod_dispositivo;

    // TAGS de informacion del modulo
    private final static String MSG_SUCCESS = "OK";

    private final static String TAG_CONEXION_INTERNET_INFO = "Info: Internet ";
    private final static String TAG_CONEXION_INTERNET_ERROR = "Error: Internet ";
    private final static String MSG_CONEXION_INTERNET_ERROR = "La conexión a internet no esta disponible ";

    private final static String TAG_DISPOSITIVO_NFC_INFO = "Info: NFC ";
    private final static String TAG_DISPOSITIVO_NFC_ERROR = "Error: NFC ";
    private final static String MSG_DISPOSITIVO_NFC_ERROR = "La conexión al dispositivo NFC no esta disponible ";

    private final static String TAG_SOLICITUD_REGISTER_INFO = "Info: Solicitud ";
    private final static String TAG_SOLICITUD_REGISTER_ERROR = "Error: Solicitud ";
    private final static String MSG_SOLICITUD_REGISTER_ERROR = "Registro de solicitud fallida ";

    private final static String TAG_SOLICITUD_STATUS_INFO = "Info: Estado ";
    private final static String TAG_SOLICITUD_STATUS_ERROR = "Error: Estado ";

    private final static String TAG_INTERNAL_REGISTER_INFO = "Info: Interno ";
    private final static String TAG_INTERNAL_REGISTER_ERROR = "Error: Interno ";
    private final static String MSG_INTERNAL_REGISTER_ERROR = "Registro interno falliido ";

    private final static String MSG_INTERNAL_REGISTER_NOT_FOUND = "Registro no encontrado";
    private final static String MSG_INTERNAL_REGISTER_UNIQUE = "Unico registro encontrado";
    private final static String MSG_INTERNAL_REGISTER_MULTIPLE = "Multiples registros encontrados";

    /**
     * #############################################################################################
     * Constructor de la clase
     * #############################################################################################
     */
    public SplashScreenRepositoryImpl() {
    }

    /**
     * #############################################################################################
     * Metodos sobrecargados de la interface
     * #############################################################################################
     */

    /**
     * Metodo que verifica:
     * - Existencia de datos
     * - Validez de datos
     *
     * @param context
     */
    @Override
    public void validateAcces(final Context context) {

        /**
         * Verificacion:
         * - conexión a internet
         * - dispositivo NFC
         */
        if (verifyInternetConnection(context) && verifyDeviceNFC(context)) {

            // Conexión a internet encontrada
            Log.i(TAG_CONEXION_INTERNET_INFO, MSG_SUCCESS);

            postEvent(SplashScreenEvent.onInternetConnectionSuccess);

            // Conexion con dispositivo NFC encontrado
            Log.i(TAG_DISPOSITIVO_NFC_INFO, MSG_SUCCESS);

            postEvent(SplashScreenEvent.onNFCDeviceConnectionSuccess);

            Log.i("Conteo", AppDatabase.getInstance(context).obtenerConteoRegistro() + "");

            switch (AppDatabase.getInstance(context).obtenerConteoRegistro()) {

                /**
                 * En caso de que el conteo de registros sea 0 (cero)
                 *  Significa que no se ha registrado una solicitud en el sistema
                 *  por tanto tampoco se ha hecho un registro interno del dispositivo en relacion
                 *  al servidor
                 */
                case 0:

                    Log.i(TAG_INTERNAL_REGISTER_INFO, MSG_INTERNAL_REGISTER_NOT_FOUND);

                    verifyRegisterServer(context);

                    break;
                case 1:

                    Log.i(TAG_INTERNAL_REGISTER_INFO, MSG_INTERNAL_REGISTER_UNIQUE + " " + AppDatabase.getInstance(context).obtenerRegistro());

                    verifyRegisterServer(context, AppDatabase.getInstance(context).obtenerRegistro());

                    break;

                default:

                    Log.i(TAG_INTERNAL_REGISTER_ERROR, MSG_INTERNAL_REGISTER_MULTIPLE);

                    postEvent(SplashScreenEvent.onVerifyError, MSG_INTERNAL_REGISTER_MULTIPLE);

                    break;
            }

        } else {
            /**
             * Si la verificacion fallo por la conexion a internet
             */
            if (!verifyInternetConnection(context)) {

                // Conexión a internet NO encontrada
                Log.i(TAG_CONEXION_INTERNET_ERROR, MSG_CONEXION_INTERNET_ERROR);

                postEvent(SplashScreenEvent.onInternetConnectionError, MSG_CONEXION_INTERNET_ERROR);

            }
            /**
             * Si la verificacion fallo por la conexion al dispositivo NFC
             */
            if (!verifyDeviceNFC(context)) {

                // Conexión a dispositivo NFC no encontrada
                Log.i(TAG_DISPOSITIVO_NFC_ERROR, MSG_DISPOSITIVO_NFC_ERROR);

                postEvent(SplashScreenEvent.onNFCDeviceConnectionError, MSG_DISPOSITIVO_NFC_ERROR);

            }
        }
    }

    /**
     * #############################################################################################
     * Metodo propios de la clase
     * #############################################################################################
     */

    /**
     * Metodo que verifica existencia de conexión a internet
     *
     * @param context
     * @return
     */
    private boolean verifyInternetConnection(Context context) {

        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    /**
     * Metodo que verifica existencia de conexión al dipositivo NFC
     *
     * @param context
     * @return
     */
    private boolean verifyDeviceNFC(Context context) {

        //Todo: Hacer la funcion para validar el dispositivo NFC
        return true;

    }

    /**
     * Metodo que verifica en el server:
     * - Existencia de la solicitud del dispositivo
     * - En caso de no existir registra la solicitud
     *
     * @param context
     * @return
     */
    private void verifyRegisterServer(final Context context) {
        verifyRegisterServer(context, "");
    }

    /**
     * Metodo que verifica en el server:
     * - Existencia de la solicitud del dispositivo
     * - En caso de no existir registra la solicitud
     *
     * @param context
     * @return
     */
    private void verifyRegisterServer(final Context context, String cod_disp) {


        final HashMap<String, String> parameters = new HashMap<>();

        if (cod_disp.equals("")) {

            // Creacion identificador unico de dispositivo
            cod_dispositivo = UUID.randomUUID().toString();

            parameters.put("cod_dispositivo", cod_dispositivo);

        } else {

            parameters.put("cod_dispositivo", cod_disp);

            cod_dispositivo = cod_disp;

        }

        VolleyTransaction volleyTransaction = new VolleyTransaction();

        volleyTransaction.getData(context,
                parameters,
                InfoGlobalTransaccion.DATA_TRANSACCION,
                new VolleyTransaction.VolleyCallback() {
                    @Override
                    public void onSuccess(JsonObject data) {

                        Log.i(TAG_SOLICITUD_REGISTER_INFO, data.get(InfoTransaccion.TRANS_KEY_MODULE).getAsString() + " : " +
                                data.get(InfoTransaccion.TRANS_KEY_DATA).getAsString() + "," +
                                data.get(InfoTransaccion.TRANS_KEY_MESSAGE).getAsString());

                        //Si el estado de la transaccion es correcto
                        if (data.get(InfoTransaccion.TRANS_KEY_STATUS).getAsString().equals(InfoTransaccion.TRANS_RESULT_STATUS_SUCCESS)) {

                            //Valida el modulo que devuelve la respuesta
                            switch (data.get(InfoTransaccion.TRANS_KEY_MODULE).getAsString()) {

                                //El registro de la solicitud ya existia el modulo devolvio el estado
                                case InfoTransaccion.TRANS_RESULT_MODULE_STATUS:

                                    switch (data.get(InfoTransaccion.TRANS_KEY_DATA).getAsString()) {

                                        //La solicitud ha sido aceptada
                                        case InfoTransaccion.TRANS_RESULT_MODULE_STATUS_ACCEPTED:

                                            Log.i(TAG_SOLICITUD_STATUS_INFO, MSG_SUCCESS + " " + InfoTransaccion.TRANS_RESULT_MODULE_STATUS_ACCEPTED);

                                            postEvent(SplashScreenEvent.onSolicitudStatusAccepted);

                                            break;

                                        //La solicitud ha sido rechazada
                                        case InfoTransaccion.TRANS_RESULT_MODULE_STATUS_REJECTED:

                                            Log.i(TAG_SOLICITUD_STATUS_INFO, MSG_SUCCESS + " " + InfoTransaccion.TRANS_RESULT_MODULE_STATUS_REJECTED);

                                            postEvent(SplashScreenEvent.onSolicitudStatusRejected);

                                            break;
                                        //La solicitud esta a la espera
                                        case InfoTransaccion.TRANS_RESULT_MODULE_STATUS_STANDBY:

                                            Log.i(TAG_SOLICITUD_STATUS_INFO, MSG_SUCCESS + " " + InfoTransaccion.TRANS_RESULT_MODULE_STATUS_STANDBY);

                                            postEvent(SplashScreenEvent.onSolicitudStatusStandBy);

                                            break;
                                        //La solicitud ha generado error
                                        case InfoTransaccion.TRANS_RESULT_MODULE_STATUS_ERROR:


                                            Log.i(TAG_SOLICITUD_STATUS_INFO, MSG_SUCCESS + " " + InfoTransaccion.TRANS_RESULT_MODULE_STATUS_ERROR);

                                            postEvent(SplashScreenEvent.onSolicitudStatusError);

                                            break;

                                    }
                                    break;

                                //Se realizo la insercion de la solicitud
                                case InfoTransaccion.TRANS_RESULT_MODULE_INSERT:

                                    Log.i(TAG_SOLICITUD_REGISTER_INFO, MSG_SUCCESS);

                                    postEvent(SplashScreenEvent.onSolicitudRegisterSuccess);

                                    verifyInitialRegister(context, cod_dispositivo);

                                    break;

                                //La transaccion o la consulta de la solicitud regresaron error
                                case InfoTransaccion.TRANS_RESULT_MODULE_TRANSACTION:
                                case InfoTransaccion.TRANS_RESULT_MODULE_SELECT:

                                    Log.i(TAG_SOLICITUD_REGISTER_ERROR, MSG_SOLICITUD_REGISTER_ERROR);

                                    postEvent(SplashScreenEvent.onSolicitudRegisterError,
                                            data.get(InfoTransaccion.TRANS_KEY_DATA).getAsString() +
                                                    "," +
                                                    data.get(InfoTransaccion.TRANS_KEY_MESSAGE).getAsString());
                                    break;
                            }

                        } else {

                            Log.i(TAG_SOLICITUD_REGISTER_ERROR, MSG_SOLICITUD_REGISTER_ERROR);

                            postEvent(SplashScreenEvent.onSolicitudRegisterError,
                                    data.get(InfoTransaccion.TRANS_KEY_DATA).getAsString() +
                                            "," +
                                            data.get(InfoTransaccion.TRANS_KEY_MESSAGE).getAsString());
                        }
                    }

                    @Override
                    public void onError(String errorMessage) {

                        Log.i(TAG_SOLICITUD_REGISTER_INFO, MSG_SOLICITUD_REGISTER_ERROR);

                        postEvent(SplashScreenEvent.onSolicitudRegisterError, MSG_SOLICITUD_REGISTER_ERROR);

                    }
                });

    }

    /**
     * Metodo que verifica el registro inicial:
     * - Existencia del registro inicial en el dispositivo
     * - En caso de no existir crea el registro inicial
     *
     * @param context
     */
    private void verifyInitialRegister(Context context, String cod_disp) {

        try {
            if (AppDatabase.getInstance(context).insertRegistroInicial(cod_disp)) {

                Log.i(TAG_INTERNAL_REGISTER_INFO, MSG_SUCCESS);

                postEvent(SplashScreenEvent.onInternalRegisterSuccess);

                verifyRegisterServer(context, cod_disp);

            } else {

                Log.i(TAG_INTERNAL_REGISTER_ERROR, MSG_INTERNAL_REGISTER_ERROR);

                postEvent(SplashScreenEvent.onInternalRegisterError, MSG_INTERNAL_REGISTER_ERROR);

            }

        } catch (Exception e) {

            e.printStackTrace();

            Log.i(TAG_INTERNAL_REGISTER_ERROR, MSG_INTERNAL_REGISTER_ERROR + " " + e.getMessage());

            postEvent(SplashScreenEvent.onInternalRegisterError, MSG_INTERNAL_REGISTER_ERROR + " " + e.getMessage());

        }
    }

    /**
     * Metodo que registra los eventos
     *
     * @param type
     * @param errorMessage
     */
    private void postEvent(int type, String errorMessage) {
        SplashScreenEvent splashScreenEvent = new SplashScreenEvent();
        splashScreenEvent.setEventType(type);
        if (errorMessage != null) {
            splashScreenEvent.setErrorMessage(errorMessage);
        }

        EventBus eventBus = GreenRobotEventBus.getInstance();

        eventBus.post(splashScreenEvent);
    }

    /**
     * Sobrecarga del metodo postevent
     *
     * @param type
     */
    private void postEvent(int type) {

        postEvent(type, null);

    }

}
