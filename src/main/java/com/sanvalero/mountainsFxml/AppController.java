package com.sanvalero.mountainsFxml;

import com.sanvalero.mountainsFxml.model.Cimas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Creado por @ author: Pedro Orós
 * el 31/10/2020
 */
public class AppController implements Initializable {

    private CimasDAO cimasDAO;

    public TextField tfNombre;
    public TextField tfAltitud;
    public TextField tfValle;
    public TextField tfTiempoAscenso;
    public ComboBox<String> cbDificultad;
    public TextField tvFoto;
    public ListView<Cimas> lvLista;
    public Label lAlertas;
    public ImageView ivImagen;

    public AppController() {
        cimasDAO = new CimasDAO();
        cimasDAO.conectar();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> combo = FXCollections.observableArrayList("Muy fácil", "Fácil", "Moderada", "Difícil", "Extrema");
        cbDificultad.setItems(combo);

        cargarLista();
        cargarFoto();

    }

    @FXML
    public void reset(Event event) {
        tfNombre.setText("");
        tfAltitud.setText("");
        tfValle.setText("");
        tfTiempoAscenso.setText("");
        cbDificultad.setValue(null);
        tvFoto.setText("");
        lAlertas.setText("");
    }

    @FXML
    public void guardarCima(Event event) {
        String nombre = tfNombre.getText();
        String altitud = tfAltitud.getText();
        String valle = tfValle.getText();
        String tiempoAscenso = tfTiempoAscenso.getText();
        String dificultad = cbDificultad.getSelectionModel().getSelectedItem();
        String foto = tvFoto.getText();

        Cimas cima = new Cimas(nombre, altitud, valle, tiempoAscenso, dificultad, foto);
        cimasDAO.guardarCima(cima);
        lAlertas.setText("Registro guardado correctamente");

        cargarLista();
    }

    @FXML
    public void modificarCima(Event event) {
        String nombre = tfNombre.getText();
        String altitud = tfAltitud.getText();
        String valle = tfValle.getText();
        String tiempoAscenso = tfTiempoAscenso.getText();
        String dificultad = cbDificultad.getSelectionModel().getSelectedItem();

        Cimas cima = new Cimas(nombre, altitud, valle, tiempoAscenso, dificultad);
        cimasDAO.modificarCima(cima);
        lAlertas.setText("Registro modificado");

        cargarLista();
    }

    @FXML
    public void eliminarCima(Event event) {
        String nombre = tfNombre.getText();

        Cimas cima = new Cimas(nombre);

        cimasDAO.eliminarCima(cima);
        lAlertas.setText("Registro eliminado");

        cargarLista();

    }

    @FXML
    public void cargarLista() {
        ObservableList<Cimas> list = FXCollections.observableArrayList(cimasDAO.listarCoches());
        lvLista.setItems(list);
    }

    @FXML
    public void cargarFoto() {  // ?????????????????????
        File file = new File(tvFoto.getText());
        Image image = new Image(file.toURI().toString());
        ivImagen.setImage(image);
    }

    public void getDetalles(Event event) {
        tfNombre.setText(lvLista.getSelectionModel().selectedItemProperty().getValue().getNombre());
        tfAltitud.setText(lvLista.getSelectionModel().selectedItemProperty().getValue().getAltitud());
        tfValle.setText(lvLista.getSelectionModel().selectedItemProperty().getValue().getValle());
        tfTiempoAscenso.setText(lvLista.getSelectionModel().selectedItemProperty().getValue().getTiempoAscenso());
        cbDificultad.setValue(lvLista.getSelectionModel().selectedItemProperty().getValue().getDificultad());

        String nombre = tfNombre.getText();
        Cimas cima = new Cimas(nombre);
        tvFoto.setText(cimasDAO.getPicture(cima));

    }

}
