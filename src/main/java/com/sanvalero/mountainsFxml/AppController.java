package com.sanvalero.mountainsFxml;

import com.sanvalero.mountainsFxml.dao.CimasDAO;
import com.sanvalero.mountainsFxml.domain.Cimas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    //public MenuItem miQuit;

    public AppController() {
        cimasDAO = new CimasDAO();
        cimasDAO.conectar();
    }

    @FXML
    public void desconectar() {
        cimasDAO.desconectar();
        lAlertas.setText("Base de datos desconectada");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> combo = FXCollections.observableArrayList("Muy fácil", "Fácil", "Moderada", "Difícil", "Extrema");
        cbDificultad.setItems(combo);

        cargarLista();
    }

    @FXML
    public void reset(Event event) {
        tfNombre.setText("");
        tfAltitud.setText("");
        tfValle.setText("");
        tfTiempoAscenso.setText("");
        cbDificultad.setValue(null);
        tvFoto.setText("");
        ivImagen.setImage(null);
        lAlertas.setText("");
    }

    @FXML
    public void guardarCima(Event event) {
        String nombre = tfNombre.getText();
        if (nombre.equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campo Incompleto");
            alert.setContentText("El campo nombre es obligatorio");
            alert.show();
        } else {
            String altitud = tfAltitud.getText();
            String valle = tfValle.getText();
            String tiempoAscenso = tfTiempoAscenso.getText();
            String dificultad = cbDificultad.getSelectionModel().getSelectedItem();
            String foto = tvFoto.getText();

            Cimas cima = new Cimas(nombre, altitud, valle, tiempoAscenso, dificultad, foto);
            cimasDAO.guardarCima(cima);
            lAlertas.setText("Registro guardado correctamente");
        }

        cargarLista();
    }

    @FXML
    public void modificarCima(Event event) {
        String nombre = tfNombre.getText();
        if (nombre.equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campo Incompleto");
            alert.setContentText("El campo nombre es obligatorio");
            alert.show();
        } else {
            String altitud = tfAltitud.getText();
            String valle = tfValle.getText();
            String tiempoAscenso = tfTiempoAscenso.getText();
            String dificultad = cbDificultad.getSelectionModel().getSelectedItem();
            String foto = tvFoto.getText();
            // TODO Que ponga Registro modificado (línea 112) después de aceptar el mensaje del Alert (líneas 105-108)
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Modificando Registro");
            alert.setContentText("¿Estás seguro de que deseas modificarlo?");
            alert.show();

            Cimas cima = new Cimas(nombre, altitud, valle, tiempoAscenso, dificultad, foto);
            cimasDAO.modificarCima(cima);
            lAlertas.setText("Registro modificado");

            /*if (alert.getResult() == ButtonType.OK) {
            Cimas cima = new Cimas(nombre, altitud, valle, tiempoAscenso, dificultad, foto);
            cimasDAO.modificarCima(cima);
            lAlertas.setText("Registro modificado");

            cargarLista();
        }*/
        }

        cargarLista();

    }

    @FXML
    public void eliminarCima(Event event) {
        String nombre = tfNombre.getText();
        // TODO Que ponga Registro eliminado (línea 129) después de aceptar el mensaje del Alert (líneas 121-124)
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminando Registro");
        alert.setContentText("¿Estás seguro de que deseas eliminarlo?");
        alert.show();

        Cimas cima = new Cimas(nombre);

        cimasDAO.eliminarCima(cima);
        lAlertas.setText("Registro eliminado");

        cargarLista();
    }

    @FXML
    public void cargarLista() {
        ObservableList<Cimas> list = FXCollections.observableArrayList(cimasDAO.listarCimas());
        lvLista.setItems(list);
    }

    @FXML
    public void cargarFoto(String url) throws FileNotFoundException {
        Image image = new Image(new FileInputStream(url));
        ivImagen.setImage(image);
    }

    public void getDetalles(Event event) throws FileNotFoundException {
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

    }

}

// TODO 1- Estilos en la ventana Scene Builder (Marcos a ImageView y quizás más componentes, igual foto de fondo desenfocada a anchorPane...)
// TODO 2- Corregir error en cimas.fxml
// TODO 3- Hacer los TODO de modificar y eliminar (definidos más arriba)
// TODO 4- Aviso de que coche guardado ya existe
// TODO 5- Hacer Dialog de conexión a BBDD y petición de User y Password
// TODO 6- Rellenar la BBDD con registros con fotos chulas

// C:\\Users\\shady\\Documents\\Proyectos_IntelliJ\\mountainsFxml\\src\\main\\resources\\images\\ibon_plan.JPG