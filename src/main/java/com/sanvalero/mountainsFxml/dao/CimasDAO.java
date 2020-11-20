package com.sanvalero.mountainsFxml.dao;

import com.sanvalero.mountainsFxml.domain.Cimas;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Creado por @ author: Pedro Orós
 * el 31/10/2020
 */
public class CimasDAO extends BaseDAO {

    public void guardarCima(Cimas cima) throws SQLException {
        String sql = "INSERT INTO cimas (nombre, altitud, valle, tiempoAscenso, dificultad, imagen) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement sentencia = null;

            sentencia = conexion.prepareStatement(sql);

            sentencia.setString(1, cima.getNombre());
            sentencia.setString(2,cima.getAltitud());
            sentencia.setString(3, cima.getValle());
            sentencia.setString(4, cima.getTiempoAscenso());
            sentencia.setString(5, cima.getDificultad());
            sentencia.setString(6, cima.getFoto());

            sentencia.executeUpdate();

    }

    public void modificarCima(Cimas cima) throws SQLException {
        String sql = "UPDATE cimas SET nombre = ?, altitud = ?, valle = ?, tiempoAscenso = ?, dificultad = ?, imagen = ? WHERE nombre = ?";
        PreparedStatement sentencia = null;

            sentencia = conexion.prepareStatement(sql);

            sentencia.setString(1, cima.getNombre());
            sentencia.setString(2, cima.getAltitud());
            sentencia.setString(3, cima.getValle());
            sentencia.setString(4, cima.getTiempoAscenso());
            sentencia.setString(5, cima.getDificultad());
            sentencia.setString(6, cima.getFoto());
            sentencia.setString(7, cima.getNombreViejo());

            sentencia.executeUpdate();

    }

    public void eliminarCima(Cimas cima) throws SQLException {
        String sql = "DELETE FROM cimas WHERE nombre = ?";
        PreparedStatement sentencia = null;

            sentencia = conexion.prepareStatement(sql);

            sentencia.setString(1, cima.getNombre());

            sentencia.executeUpdate();

    }

    public List<Cimas> listarCimas() throws SQLException {
        String sql = "SELECT * FROM cimas";
        PreparedStatement sentencia = null;

        List<Cimas> lista = new ArrayList<>();

            sentencia = conexion.prepareStatement(sql);
            ResultSet resultado = sentencia.executeQuery();

            while (resultado.next()) {
                Cimas cima = new Cimas(
                        resultado.getString(2),
                        resultado.getString(3),
                        resultado.getString(4),
                        resultado.getString(5),
                        resultado.getString(6)
                );
                lista.add(cima);

            }

        return  lista;
    }
    public String getPicture(Cimas cima) throws SQLException {
        String sql = "SELECT imagen FROM cimas WHERE nombre = ?";
        PreparedStatement sentencia = null;
        String url;

        sentencia = conexion.prepareStatement(sql);
        sentencia.setString(1, cima.getNombre());
        ResultSet resultado = sentencia.executeQuery();

        if(resultado.next()) {
                                                            //?????????????
            url = resultado.getString(1);    // SOLUCIONADO! En esta línea 99 estaba el problema
            return  url;                                // Hay que poner el column index de los campos solicitados en la
        }                                               // Consulta SELECT imagen FROM... <-- En este caso sólo hay uno: "imagen"
                                                            // Por eso el índice es 1 (columnIndex 1 en línea 99)
        return null;
    }

    public boolean existeCoche(String nombre) throws SQLException {
        String sql = "SELECT * FROM cimas WHERE nombre = ?";
        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setString(1, nombre);
        ResultSet resultado = sentencia.executeQuery();

        return resultado.next();
    }

}
