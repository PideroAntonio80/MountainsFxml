package com.sanvalero.mountainsFxml;

import com.sanvalero.mountainsFxml.dao.CimasDAO;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Creado por @ author: Pedro Orós
 * el 06/11/2020
 */
public class InitController{/* implements Initializable {

    public ToggleGroup grupoBotones;
    public RadioButton rbMysql;
    public RadioButton rbPostgre;
    public TextField tfUsuario;
    public TextField tfPassword;
    public Button bConectar;

    private CimasDAO cimasDAO;

    public int eleccion;
    String usuario;
    String password;

    public InitController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        grupoBotones = new ToggleGroup();
        rbMysql.setToggleGroup(grupoBotones);
        rbPostgre.setToggleGroup(grupoBotones);

    }

    @FXML
    public void conect(Event event) {
        if (tfUsuario != null) {
        usuario = tfUsuario.getText();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Atención");
            alert.setContentText("Debes rellenar el campo Ususario");
            alert.show();
        }

        password = tfPassword.getText();

        RadioButton miBoton = (RadioButton) grupoBotones.getSelectedToggle();

        if (miBoton.getId() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Atención");
            alert.setContentText("Debes elegir un motor de base de datos");
            alert.show();
        }
        else if (miBoton.getId().equals("rbMysql")) eleccion = 1;
        else eleccion= 2;

        System.out.println(usuario);
        System.out.println(password);
        System.out.println(eleccion);
        System.out.println(miBoton.getId());
        cimasDAO = new CimasDAO(usuario, password, eleccion);
        cimasDAO.conectar();

    }*/

}
