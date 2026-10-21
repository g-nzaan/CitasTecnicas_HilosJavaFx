package com.tecnoasistencia;

import com.tecnoasistencia.controlador.CitaTecnicaController;
import com.tecnoasistencia.controlador.ClienteController;
import com.tecnoasistencia.controlador.MenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Punto de entrada principal de la aplicación JavaFX para TecnoAsistencia Nicaragua S.A.
 * Coordina la navegación inicial mediante el Menú Principal, la carga de módulos y la sincronización reactiva en segundo plano (RF01, RF09).
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // 1. Cargar vista del Menú Principal
        FXMLLoader loaderMenu = new FXMLLoader(getClass().getResource("/com/tecnoasistencia/vistas/menu-view.fxml"));
        Parent vistaMenu = loaderMenu.load();
        MenuController menuController = loaderMenu.getController();

        // 2. Cargar vistas de módulos de trabajo (TabPane)
        TabPane panel = new TabPane();
        panel.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Vista de Clientes
        FXMLLoader loaderClientes = new FXMLLoader(getClass().getResource("/com/tecnoasistencia/vistas/cliente-view.fxml"));
        Parent vistaClientes = loaderClientes.load();
        ClienteController clienteController = loaderClientes.getController();
        Tab tabClientes = new Tab("Clientes", vistaClientes);

        // Vista de Citas Técnicas
        FXMLLoader loaderCitas = new FXMLLoader(getClass().getResource("/com/tecnoasistencia/vistas/cita-view.fxml"));
        Parent vistaCitas = loaderCitas.load();
        CitaTecnicaController citaController = loaderCitas.getController();
        Tab tabCitas = new Tab("Citas técnicas", vistaCitas);

        panel.getTabs().addAll(tabClientes, tabCitas);

        // 3. Crear contenedor para los módulos con barra superior que permite retornar al menú
        BorderPane contenedorModulos = new BorderPane();

        HBox barraNavegacion = new HBox(14);
        barraNavegacion.setAlignment(Pos.CENTER_LEFT);
        barraNavegacion.setPadding(new Insets(10, 20, 10, 20));
        barraNavegacion.setStyle("-fx-background-color: #0f172a;");

        Button btnVolverMenu = new Button("← Volver al Menú Principal");
        btnVolverMenu.setStyle(
                "-fx-background-color: #1e293b; " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-font-size: 12px; " +
                "-fx-cursor: hand; " +
                "-fx-background-radius: 6px; " +
                "-fx-padding: 7 14 7 14; " +
                "-fx-border-color: #475569; " +
                "-fx-border-radius: 6px;"
        );

        Label lblTituloBarra = new Label("TecnoAsistencia Nicaragua S.A.  |  Gestión Operativa");
        lblTituloBarra.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 12px; -fx-font-weight: bold;");

        barraNavegacion.getChildren().addAll(btnVolverMenu, lblTituloBarra);

        contenedorModulos.setTop(barraNavegacion);
        contenedorModulos.setCenter(panel);

        // 4. Configurar la escena iniciando en el Menú Principal
        Scene scene = new Scene(vistaMenu, 1220, 760);

        // Acción para volver al Menú Principal desde cualquier módulo
        btnVolverMenu.setOnAction(e -> scene.setRoot(vistaMenu));

        // Enlazar navegación del Menú Principal hacia los módulos respectivos con carga asíncrona
        menuController.setOnAbrirClientes(() -> {
            panel.getSelectionModel().select(tabClientes);
            if (clienteController != null) {
                clienteController.cargarClientes();
            }
            scene.setRoot(contenedorModulos);
        });

        menuController.setOnAbrirCitas(() -> {
            panel.getSelectionModel().select(tabCitas);
            if (citaController != null) {
                citaController.cargarDatos();
            }
            scene.setRoot(contenedorModulos);
        });

        // RF09: Sincronización automática no bloqueante mediante hilos al cambiar entre pestañas
        panel.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab == tabCitas && citaController != null) {
                citaController.cargarDatos();
            } else if (newTab == tabClientes && clienteController != null) {
                clienteController.cargarClientes();
            }
        });

        stage.setTitle("TecnoAsistencia Nicaragua S.A. - Sistema de Gestión de Citas Técnicas");
        stage.setMinWidth(1100);
        stage.setMinHeight(700);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}