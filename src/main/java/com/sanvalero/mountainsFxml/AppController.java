package com.sanvalero.mountainsFxml;

import com.sanvalero.mountainsFxml.dao.CimasDAO;
import com.sanvalero.mountainsFxml.domain.Cimas;
import com.sanvalero.mountainsFxml.util.R;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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

        /*try {
        Stage secondStage = new Stage();
        FXMLLoader loader2 = new FXMLLoader();
        loader2.setLocation(R.getUI("inicio_mountains.fxml"));
        loader2.setController(new InitController());
        VBox inicio = loader2.load();
        Scene scene2 = new Scene(inicio);
        secondStage.setScene(scene2);
        secondStage.initModality(Modality.APPLICATION_MODAL);
        secondStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }*/

        cimasDAO = new CimasDAO("root", "", 1);
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

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Modificando Registro");
            alert.setContentText("¿Estás seguro de que deseas modificarlo?");
            Optional<ButtonType> action = alert.showAndWait();

            if (action.get() == ButtonType.OK) {
                Cimas cima = new Cimas(nombre, altitud, valle, tiempoAscenso, dificultad, foto);
                cimasDAO.modificarCima(cima);
                lAlertas.setText("Registro modificado");
            }

        }

        cargarLista();

    }

    @FXML
    public void eliminarCima(Event event) {
        String nombre = tfNombre.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminando Registro");
        alert.setContentText("¿Estás seguro de que deseas eliminarlo?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            Cimas cima = new Cimas(nombre);
            cimasDAO.eliminarCima(cima);
            lAlertas.setText("Registro eliminado");
        }

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
// TODO 3- Aviso de que cima guardada ya existe
// TODO 4- Hacer Dialog de conexión a BBDD y petición de User y Password


// C:\\Users\\shady\\Documents\\Proyectos_IntelliJ\\mountainsFxml\\src\\main\\resources\\images\\fondo_app.jpg