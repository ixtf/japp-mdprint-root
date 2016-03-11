/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hengyi.japp.print.client.controller;

import com.hengyi.japp.print.client.domain.SapYmmbanci;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class MdSearchController implements Initializable {

    private Stage stage;
    @FXML
    private DatePicker hsdatField;
    @FXML
    private ComboBox<SapYmmbanci> sapYmmbanciField;
    @FXML
    private TextField matnrField;
    @FXML
    private Label maktxLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void autoCompleteSapMakt(KeyEvent e) {
    }

    @FXML
    private void handleConfirm(ActionEvent e) {
        stage.close();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
