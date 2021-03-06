package com.tuxstone.basefinanciera.lib;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.tuxstone.basefinanciera.global.InfoGlobalTransaccion;

import java.util.HashMap;

public class VolleyTransaction {


    private static final String TAG_ERROR_JSON = "Error Json: ";
    private static final String TAG_ERROR_VOLLEY = "Error Volley: ";

    /**
     * Metodo que hace la moduleTransaccion de datos por el Metodo POST
     *
     * @param context
     * @param parameters
     * @param moduleTransaccion
     * @param callback
     */
    public void getData(Context context,
                        HashMap<String, String> parameters,
                        String moduleTransaccion,
                        final VolleyCallback callback) {

        // Añadir petición GSON a la cola
        VolleySingleton.getInstance(context).addToRequestQueue(

                //Llamado al constructor de la clase GsonRequest
                new GsonRequest<JsonObject>(

                        //@param int method
                        Request.Method.POST,

                        //@param URL
                        InfoGlobalTransaccion.URL_BASE + "/" + moduleTransaccion,

                        //@param Class<T> clazz Clase o modelo en el que se formatean los datos
                        JsonObject.class,

                        //@param headers
                        parameters,

                        //@param Listener
                        new Response.Listener<JsonObject>() {
                            @Override
                            public void onResponse(JsonObject response) {

                                try {
                                    JsonObject jsonObject = response.getAsJsonObject(InfoGlobalTransaccion.FEED_KEY);
                                    callback.onSuccess(jsonObject);

                                } catch (JsonIOException e) {

                                    e.printStackTrace();
                                    Log.e(TAG_ERROR_JSON, e.toString());

                                } catch (JsonParseException e) {

                                    e.printStackTrace();
                                    Log.e(TAG_ERROR_JSON, e.toString());

                                }
                            }
                        },
                        //@param errorListener
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Log.d(TAG_ERROR_VOLLEY, error.getMessage());
                                callback.onError(error.getMessage());

                            }
                        }
                )
        );
    }

    /**
     * Metodo que hace la moduleTransaccion de datos por el Metodo GET
     *
     * @param context
     * @param moduleTransaccion
     * @param callback
     */
    public void getData(Context context,
                        String moduleTransaccion,
                        final VolleyCallback callback) {

        // Añadir petición GSON a la cola
        VolleySingleton.getInstance(context).addToRequestQueue(

                //Llamado al constructor de la clase GsonRequest
                new GsonRequest<JsonObject>(

                        //@param int method
                        Request.Method.GET,

                        //@param URL
                        InfoGlobalTransaccion.URL_BASE + "/" + moduleTransaccion,

                        //@param Class<T> clazz Clase o modelo en el que se formatean los datos
                        JsonObject.class,

                        //@param headers
                        null,

                        //@param Listener
                        new Response.Listener<JsonObject>() {
                            @Override
                            public void onResponse(JsonObject response) {

                                try {

                                    JsonObject jsonObject = response.getAsJsonObject(InfoGlobalTransaccion.FEED_KEY);
                                    callback.onSuccess(jsonObject);


                                } catch (JsonIOException e) {

                                    e.printStackTrace();
                                    Log.e(TAG_ERROR_JSON, e.toString());

                                } catch (JsonParseException e) {

                                    e.printStackTrace();
                                    Log.e(TAG_ERROR_JSON, e.toString());

                                }
                            }
                        },
                        //@param errorListener
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Log.d(TAG_ERROR_VOLLEY, error.getMessage());
                                callback.onError(error.getMessage());

                            }
                        }
                )
        );
    }

    /**
     * Interface de Callback de InfoGlobalTransaccion Volley
     */
    public interface VolleyCallback {
        /**
         * Retorno en exito
         *
         * @param data
         */
        void onSuccess(JsonObject data);

        /**
         * Retorno en fallo
         *
         * @param errorMessage
         */
        void onError(String errorMessage);
    }

}
