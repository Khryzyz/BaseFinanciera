package com.tuxstone.basefinanciera.database;

/**
 * Clase que maneja:
 * - Scripts de la base de datos
 * - Atributos de la base de datos
 * - DDL de la base de datos
 */

public class DatabaseManager {

    //Etiqueta para Depuración
    private static final String TAG = DatabaseManager.class.getSimpleName();

    // Informacion de los tipos de los campos
    public static final String STRING_TYPE = "TEXT";
    public static final String INT_TYPE = "INTEGER";

    //Informacion de las  caracteristicas de los campos
    public static final String PRIMARY_KEY = "PRIMARY KEY";
    public static final String AUTOINCREMENT = "AUTOINCREMENT";
    public static final String ATTR_NULL = "NULL";
    public static final String ATTR_NOT_NULL = "NOT NULL";

    /**
     * Informacion de la base de datos
     */
    public static class DatabaseApp {

        public static final String DATABASE_NAME = "app_base_financiera.db";
        public static final int DATABASE_VERSION = 17;

    }

    /**
     * Tabla Registro:
     * - Modelado de la tabla registro
     * - Scripts de la tabla registro
     */
    public static class TableRegistro {

        /**
         * Modelado de la tabla registro
         * Nombre de la tabla
         */
        public static final String TABLE_NAME_REGISTRO = "base_financiera";

        /**
         * Modelado de la tabla registro
         * Columnas de la tabla
         */
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_COD_DISPOSITIVO = "codigo_dispositivo";
        public static final String COLUMN_COD_SERVIDOR = "codigo_servidor";
        public static final String COLUMN_REGISTRO = "registro";
        public static final String COLUMN_ESTADO = "estado";

        /**
         * Scripts de la tabla registro
         * Comando CREATE para la tabla Registro de Base Financiera
         */
        public static final String CREATE_TABLE_REGISTRO =
                "CREATE TABLE " + TABLE_NAME_REGISTRO + "(" +
                        COLUMN_ID + " " + INT_TYPE + " " + PRIMARY_KEY + " " + AUTOINCREMENT + "," +
                        COLUMN_COD_DISPOSITIVO + " " + STRING_TYPE + ATTR_NOT_NULL + "," +
                        COLUMN_COD_SERVIDOR + " " + STRING_TYPE + "," +
                        COLUMN_REGISTRO + " " + STRING_TYPE + "," +
                        COLUMN_ESTADO + " " + STRING_TYPE + ")";

        /**
         * Scripts de la tabla registro
         * Comando DROP para la tabla Registro de Base Financiera
         */
        public static final String DROP_TABLE_REGISTRO =
                "DROP TABLE IF EXISTS '" + TABLE_NAME_REGISTRO + "'";


    }

}
