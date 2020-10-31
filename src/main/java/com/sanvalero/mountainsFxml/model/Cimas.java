package com.sanvalero.mountainsFxml.model;

import java.sql.Time;

/**
 * Creado por @ author: Pedro Orós
 * el 31/10/2020
 */
public class Cimas {

    private String nombre;
    private String altitud;
    private String valle;
    private String tiempoAscenso;
    private String dificultad;
    private String foto;

    public Cimas(String nombre, String altitud, String valle, String tiempoAscenso, String dificultad, String foto) {
        this.nombre = nombre;
        this.altitud = altitud;
        this.valle = valle;
        this.tiempoAscenso = tiempoAscenso;
        this.dificultad = dificultad;
        this.foto = foto;
    }

    public Cimas(String nombre, String altitud, String valle, String tiempoAscenso, String dificultad) {
        this.nombre = nombre;
        this.altitud = altitud;
        this.valle = valle;
        this.tiempoAscenso = tiempoAscenso;
        this.dificultad = dificultad;
    }

    public Cimas(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {

        return nombre;
    }

    public void setNombre(String nombre) {

        this.nombre = nombre;
    }

    public String getAltitud() {

        return altitud;
    }

    public void setAltitud(String altitud) {

        this.altitud = altitud;
    }

    public String getValle() {

        return valle;
    }

    public void setValle(String valle) {

        this.valle = valle;
    }

    public String getTiempoAscenso() {

        return tiempoAscenso;
    }

    public void setTiempoAscenso(String tiempoAscenso) {

        this.tiempoAscenso = tiempoAscenso;
    }

    public String getDificultad() {

        return dificultad;
    }

    public void setDificultad(String dificultad) {

        this.dificultad = dificultad;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return this.nombre + " (" + this.altitud + ") ---> " + this.valle + ".  Tiempo de Ascenso: " + this.tiempoAscenso + ".  Dificultad: " + this.dificultad;
    }
}