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

    private final String GUARDAR = "INSERT INTO cimas (nombre, altitud, valle, tiempoAscenso, dificultad, imagen) VALUES (?, ?, ?, ?, ?, ?)";
    private final String MODIFICAR = "UPDATE cimas SET nombre = ?, altitud = ?, valle = ?, tiempoAscenso = ?, dificultad = ?, imagen = ? WHERE nombre = ?";
    private final String ELIMINAR = "DELETE FROM cimas WHERE nombre = ?";
    private final String LISTAR = "SELECT * FROM cimas";
    private final String FOTO = "SELECT imagen FROM cimas WHERE nombre = ?";

    public CimasDAO(String USUARIO, String PASSWORD, int eligeMotor) {
        super(USUARIO, PASSWORD, eligeMotor);
    }

    public void guardarCima(Cimas cima) {
        PreparedStatement sentencia = null;
        try {
            sentencia = conexion.prepareStatement(GUARDAR);

            sentencia.setString(1, cima.getNombre());
            sentencia.setString(2,cima.getAltitud());
            sentencia.setString(3, cima.getValle());
            sentencia.setString(4, cima.getTiempoAscenso());
            sentencia.setString(5, cima.getDificultad());
            sentencia.setString(6, cima.getFoto());

            sentencia.executeUpdate();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        finally {
            if(sentencia != null) {
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
            }
        }
    }

    public void modificarCima(Cimas cima) {
        PreparedStatement sentencia = null;
        try {
            sentencia = conexion.prepareStatement(MODIFICAR);

            sentencia.setString(1, cima.getNombre());
            sentencia.setString(2, cima.getAltitud());
            sentencia.setString(3, cima.getValle());
            sentencia.setString(4, cima.getTiempoAscenso());
            sentencia.setString(5, cima.getDificultad());
            sentencia.setString(6, cima.getFoto());
            sentencia.setString(7, cima.getNombre());

            sentencia.executeUpdate();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        finally {
            if(sentencia != null) {
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
            }
        }
    }

    public void eliminarCima(Cimas cima) {
        PreparedStatement sentencia = null;
        try {
            sentencia = conexion.prepareStatement(ELIMINAR);

            sentencia.setString(1, cima.getNombre());

            sentencia.executeUpdate();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        finally {
            if(sentencia != null) {
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
            }
        }
    }

    public List<Cimas> listarCimas() {
        PreparedStatement sentencia = null;
        List<Cimas> lista = new ArrayList<>();

        try {
            sentencia = conexion.prepareStatement(LISTAR);
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

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        finally {
            if(sentencia != null) {
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
            }
        }
        return  lista;
    }

    public String getPicture(Cimas cima) {
        PreparedStatement sentencia = null;
        String url;

        try {
            sentencia = conexion.prepareStatement(FOTO);
            sentencia.setString(1, cima.getNombre());
            ResultSet resultado = sentencia.executeQuery();

            while(resultado.next()) {
                                                            //?????????????
                url = resultado.getString(1);    // SOLUCIONADO! En esta línea 150 estaba el problema
                return  url;                                // Hay que poner el column index de los campos solicitados en la
            }                                               // Consulta SELECT imagen FROM... <-- En este caso sólo hay uno: "imagen"
                                                            // Por eso el índice es 1 (columnIndex 1 en línea 150)
        } catch (SQLException sqle) {
            sqle.printStackTrace();

        }
        finally {
            if(sentencia != null) {
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
            }
        }

        return null;
    }

}
