package com.sanvalero.mountainsFxml.dao;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Creado por @ author: Pedro Orós
 * el 31/10/2020
 */
public class BaseDAO {

    protected Connection conexion;
    private final String USUARIO = "root";
    private final String PASSWORD = "";

    public Connection conectar() {
        int eligeMotor = Integer.parseInt(JOptionPane.showInputDialog("Elige motor de BBDD: (1)MySQL, (2)Postgre"));

        if(eligeMotor == 1) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver"); /* <--- Busco en MVNrepository (en internet),
                                                        copio el xml de mysql connector y lo pego en el POM*/
                conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mountains?serverTimezone=UTC",
                        USUARIO, PASSWORD);
            } catch (ClassNotFoundException cnfe) {
                cnfe.printStackTrace();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
            return conexion;

        } /*else {
            try {
                Class.forName("org.postgresql.Driver").newInstance();
                conexion = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/basededatos",
                        "usuario", "contraseña");
            } catch (ClassNotFoundException cnfe) {
                cnfe.prinStackTrace();
            } catch (SQLException sqle) {
                sqle.prinStackTrace();
            } catch (InstantiationException ie) {
                ie.prinStackTrace();
            } catch (IllegalAccessException iae) {
                iae.prinStackTrace();
            }
        }*/
        return null;
    }

    public void desconectar() {
        try {
            conexion.close();
            conexion = null;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

}
