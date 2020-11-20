package com.sanvalero.mountainsFxml;

import com.sanvalero.mountainsFxml.dao.CimasDAO;
import com.sanvalero.mountainsFxml.domain.Cimas;
import com.sanvalero.mountainsFxml.util.AlertUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
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
        try {
            cimasDAO.conectar();
        } catch (ClassNotFoundException cnfe) {
            AlertUtils.mostrarError("Error al iniciar la aplicación");
        } catch (SQLException sql) {
            AlertUtils.mostrarError("No se ha podido conectar");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @FXML
    public void desconectar() {
        try {
            cimasDAO.desconectar();
            lAlertas.setText("Base de datos desconectada");
        } catch (SQLException sql) {
            AlertUtils.mostrarError("No se ha podido desconectar");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> combo = FXCollections.observableArrayList("Muy fácil", "Fácil", "Moderada", "Difícil", "Extrema");
        cbDificultad.setItems(combo);

        cargarLista();
    }

    @FXML
    public void reset(Event event) {
        limpiaCajas();
    }

    @FXML
    public void guardarCima(Event event) {
        try {
            String nombre = tfNombre.getText();
            if (nombre.equals("")) {
                AlertUtils.mostrarError("El campo nombre es obligatorio");
                return;
            }
            String altitud = tfAltitud.getText();
            String valle = tfValle.getText();
            String tiempoAscenso = tfTiempoAscenso.getText();
            String dificultad = cbDificultad.getSelectionModel().getSelectedItem();
            String foto = tvFoto.getText();

            Cimas cima = new Cimas(nombre, altitud, valle, tiempoAscenso, dificultad, foto);
            if(cimasDAO.existeCoche(cima.getNombre())) {
                AlertUtils.mostrarError("La cima ya existe en la BBDD");
                return;
            }
            cimasDAO.guardarCima(cima);
            lAlertas.setText("Registro guardado correctamente");
            cargarLista();
        } catch (SQLException sql) {
            AlertUtils.mostrarError("Error al guardar cima");
        }

    }

    @FXML
    public void modificarCima(Event event) {
        try {
            AlertUtils.mostrarConfirmacion("Modificación");

            String nombreViejo = lvLista.getSelectionModel().selectedItemProperty().getValue().getNombre();
            String nombre = tfNombre.getText();
            if (nombre.equals("")) {
                AlertUtils.mostrarError("Debes elegir un coche de la lista para modificar");
                return;
            }
            String altitud = tfAltitud.getText();
            String valle = tfValle.getText();
            String tiempoAscenso = tfTiempoAscenso.getText();
            String dificultad = cbDificultad.getSelectionModel().getSelectedItem();
            String foto = tvFoto.getText();

            Cimas cima = new Cimas(nombreViejo, nombre, altitud, valle, tiempoAscenso, dificultad, foto);
            cimasDAO.modificarCima(cima);
            lAlertas.setText("Registro modificado");

            cargarLista();
        } catch (SQLException sql) {
            AlertUtils.mostrarError("Error al modificar");
        }

    }

    @FXML
    public void eliminarCima(Event event) {
        try {
            AlertUtils.mostrarConfirmacion("Eliminación");

            String nombre = tfNombre.getText();

            Cimas cima = new Cimas(nombre);
            cimasDAO.eliminarCima(cima);
            lAlertas.setText("Registro eliminado");
            cargarLista();
        } catch (SQLException sql) {
            AlertUtils.mostrarError("No se ha podido eliminar");
        }

    }

    @FXML
    public void cargarLista() {
        ObservableList<Cimas> list = null;
        try {
            list = FXCollections.observableArrayList(cimasDAO.listarCimas());
            lvLista.setItems(list);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @FXML
    public void cargarFoto(String url) {
        Image image = null;
        try {
            image = new Image(new FileInputStream(url));
        } catch (FileNotFoundException fnfe) {
            AlertUtils.mostrarError("No se ha encontrado la foto");
        }
        ivImagen.setImage(image);
    }

    @FXML
    public void importar(ActionEvent Event) {

    }

    @FXML
    public void exportar(ActionEvent Event) {
        try {
            FileChooser fileChooser = new FileChooser();
            File fichero = fileChooser.showSaveDialog(null);
            FileWriter fileWriter = new FileWriter(fichero);

            CSVPrinter printer = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withHeader("Nombre", "Altitud", "Valle", "Tiempo de Ascenso", "Dificultad"));

            List<Cimas> cimas = cimasDAO.listarCimas();
            for (Cimas cima : cimas) {
                printer.printRecord(cima.getNombre(), cima.getAltitud(), cima.getValle(), cima.getTiempoAscenso(), cima.getDificultad());
            }
            printer.close();
            lAlertas.setText("Datos transferidos a su fichero");
        } catch (SQLException sql) {
            AlertUtils.mostrarError("Error al conectar con la base de datos");
        } catch (IOException ioe) {
            AlertUtils.mostrarError("Error al exportar los datos");
        }
    }

    public void getDetalles(Event event) {
        try {
        tfNombre.setText(lvLista.getSelectionModel().selectedItemProperty().getValue().getNombre());
        tfAltitud.setText(lvLista.getSelectionModel().selectedItemProperty().getValue().getAltitud());
        tfValle.setText(lvLista.getSelectionModel().selectedItemProperty().getValue().getValle());
        tfTiempoAscenso.setText(lvLista.getSelectionModel().selectedItemProperty().getValue().getTiempoAscenso());
        cbDificultad.setValue(lvLista.getSelectionModel().selectedItemProperty().getValue().getDificultad());

        String nombre = tfNombre.getText();
        Cimas cima = new Cimas(nombre);

        if(cimasDAO.getPicture(cima) != null) {
            tvFoto.setText(cimasDAO.getPicture(cima));
            cargarFoto(tvFoto.getText());
        }
        } catch (SQLException sql) {
            AlertUtils.mostrarError("Error al cargar la foto");
        }

    }

    private void limpiaCajas() {
        tfNombre.setText("");
        tfAltitud.setText("");
        tfValle.setText("");
        tfTiempoAscenso.setText("");
        cbDificultad.setValue(null);
        tvFoto.setText("");
        ivImagen.setImage(null);
        lAlertas.setText("");
    }

}

// Plantilla para meter URL de fotos:
// C:\\Users\\shady\\Documents\\Proyectos_IntelliJ\\mountainsFxml\\src\\main\\resources\\images\\nombre_foto.jpg o png