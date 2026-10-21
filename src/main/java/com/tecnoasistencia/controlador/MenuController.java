package com.tecnoasistencia.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Controlador para la vista del Menú Principal (menu-view.fxml).
 * Gestiona la navegación inicial del usuario hacia los módulos de Clientes o Citas Técnicas.
 */
public class MenuController {

    @FXML
    private Button btnAccederClientes;

    @FXML
    private Button btnAccederCitas;

    private Runnable onAbrirClientes;
    private Runnable onAbrirCitas;

    /**
     * Define la acción a ejecutar cuando el usuario pulsa para acceder a Clientes.
     * @param onAbrirClientes acción de navegación
     */
    public void setOnAbrirClientes(Runnable onAbrirClientes) {
        this.onAbrirClientes = onAbrirClientes;
    }

    /**
     * Define la acción a ejecutar cuando el usuario pulsa para acceder a Citas Técnicas.
     * @param onAbrirCitas acción de navegación
     */
    public void setOnAbrirCitas(Runnable onAbrirCitas) {
        this.onAbrirCitas = onAbrirCitas;
    }

    @FXML
    private void handleAccederClientes(ActionEvent event) {
        if (onAbrirClientes != null) {
            onAbrirClientes.run();
        }
    }

    @FXML
    private void handleAccederCitas(ActionEvent event) {
        if (onAbrirCitas != null) {
            onAbrirCitas.run();
        }
    }
}
