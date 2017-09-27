package com.tuxstone.basefinanciera.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.UUID;

/**
 * Clase que maneja:
 * - DML de la base de datos
 * - instancia general de acceso a datos
 */
public final class AppDatabase extends SQLiteOpenHelper {

    // Mapeado rápido de indices
    public static final int COLUMN_ID = 0;
    public static final int COLUMN_COD_DISPOSITIVO = 1;
    public static final int COLUMN_COD_SERVIDOR = 2;
    public static final int COLUMN_REGISTRO = 3;
    public static final int COLUMN_ESTADO = 4;

    //Instancia singleton
    private static AppDatabase singleton;

    //Etiqueta de depuración
    private static final String TAG = AppDatabase.class.getSimpleName();

    /**
     * Constructor de la clase
     * Crea la base de datos si no existe
     *
     * @param context
     */
    private AppDatabase(Context context) {
        super(context,
                DatabaseManager.DatabaseApp.DATABASE_NAME,
                null,
                DatabaseManager.DatabaseApp.DATABASE_VERSION);
    }

    /**
     * Retorna la instancia unica del singleton
     *
     * @param context contexto donde se ejecutarán las peticiones
     * @return Instancia
     */
    public static synchronized AppDatabase getInstance(Context context) {

        if (singleton == null) {

            //Todo: Borrar esta linea despues de probar el acceso y eliminacion de la base de datos
            //context.deleteDatabase(DatabaseManager.DatabaseApp.DATABASE_NAME);

            singleton = new AppDatabase(context.getApplicationContext());
        }
        return singleton;
    }

    /**
     * Metodo ejecutado en el evento de la instalacion de la aplicacion
     * Crea las tablas necesarias para el funcionamiento de la aplicacion
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Crear la tabla 'REGISTRO'
        db.execSQL(DatabaseManager.TableRegistro.CREATE_TABLE_REGISTRO);
    }

    /**
     * Metodo ejecutado en la actualizacion de la aplicacion
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Añade los cambios que se realizarán en el esquema

        db.execSQL(DatabaseManager.TableRegistro.DROP_TABLE_REGISTRO);

        onCreate(db);
    }

    /**
     * Metodo para insertar registro inicial en la Base de Datos
     */
    public boolean insertRegistroInicial(String cod_dispositivo) {

        // Inicializacion de la variable de contenidos del registro
        ContentValues values = new ContentValues();

        // Almacena los valores a insertar
        values.put(DatabaseManager.TableRegistro.COLUMN_COD_DISPOSITIVO, cod_dispositivo);
        values.put(DatabaseManager.TableRegistro.COLUMN_COD_SERVIDOR, "");
        values.put(DatabaseManager.TableRegistro.COLUMN_REGISTRO, "time('now')");
        values.put(DatabaseManager.TableRegistro.COLUMN_ESTADO, true);

        // Insercion del registro en la base de datos
        Long countRegistro = getWritableDatabase().insert(
                DatabaseManager.TableRegistro.TABLE_NAME_REGISTRO,
                null,
                values
        );
        if (countRegistro == 1)
            return true;
        else
            return false;

    }

    /**
     * Metodo para Obtener todos los registros de la tabla base_financiera
     *
     * @return cursor con los registros
     */
    public String obtenerRegistro() {

        String cod_dispositivo;

        Cursor queryCodDispositivo;

        queryCodDispositivo = getWritableDatabase().rawQuery(
                "SELECT * FROM " + DatabaseManager.TableRegistro.TABLE_NAME_REGISTRO, null
        );

        queryCodDispositivo.moveToFirst();

        cod_dispositivo = queryCodDispositivo.getString(COLUMN_COD_DISPOSITIVO);

        return cod_dispositivo;
    }

    /**
     * Metodo para Obtener el conteo de los registros de la tabla base_financiera
     *
     * @return conteo de los registros
     */
    public int obtenerConteoRegistro() {

        int countRegistro;

        Cursor queryCount;

        queryCount = getWritableDatabase().rawQuery(
                "SELECT COUNT(1) FROM " + DatabaseManager.TableRegistro.TABLE_NAME_REGISTRO, null
        );

        queryCount.moveToFirst();
//Todo: cambiar esto al indice correspondiente
        countRegistro = queryCount.getInt(0);

        return countRegistro;
    }
}