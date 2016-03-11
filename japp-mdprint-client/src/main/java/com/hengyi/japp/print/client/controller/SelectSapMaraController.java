/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hengyi.japp.print.client.controller;

import com.google.common.collect.Lists;
import com.hengyi.japp.print.client.domain.SapMara;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author jzb
 */
public class SelectSapMaraController implements Initializable {

    private Stage stage;
    @FXML
    private TextField searchField;
    @FXML
    private TableView<SapMara> sapMaktTable;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        searchField.textProperty().addListener((oV, oldV, newV) -> {
            if (isBlank(newV)) {
                List<SapMara> sapMaras = Lists.newArrayList();
                sapMaktTable.setItems(observableArrayList(sapMaras));
            }
        });
    }

    @FXML
    private void handleConfirm(ActionEvent e) {
        SapMara sapMara = sapMaktTable.getSelectionModel().getSelectedItem();
        if (sapMara == null) {
        } else {
        }
    }

    @FXML
    private void handleCancel(ActionEvent e) {
        stage.close();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
