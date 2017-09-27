package com.tuxstone.basefinanciera.models;

import java.sql.Timestamp;

public class RegistroDispositivo {

    /**
     * Modelo para el registro del Dispositivo
     */
    private int id;
    private String codigo_dispositivo;
    private String codigo_servidor;
    private Timestamp registro;
    private Boolean estado;

    /**
     * Constructor vacio
     */
    public RegistroDispositivo() {
    }

    /**
     * Constructor con parametros
     */
    public RegistroDispositivo(int id, String codigo_dispositivo, String codigo_servidor, Timestamp registro, Boolean estado) {
        this.id = id;
        this.codigo_dispositivo = codigo_dispositivo;
        this.codigo_servidor = codigo_servidor;
        this.registro = registro;
        this.estado = estado;
    }

    /**
     * Getters y Setters del modelo para el registro del Dispositivo
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo_dispositivo() {
        return codigo_dispositivo;
    }

    public void setCodigo_dispositivo(String codigo_dispositivo) {
        this.codigo_dispositivo = codigo_dispositivo;
    }

    public String getCodigo_servidor() {
        return codigo_servidor;
    }

    public void setCodigo_servidor(String codigo_servidor) {
        this.codigo_servidor = codigo_servidor;
    }

    public Timestamp getRegistro() {
        return registro;
    }

    public void setRegistro(Timestamp registro) {
        this.registro = registro;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
