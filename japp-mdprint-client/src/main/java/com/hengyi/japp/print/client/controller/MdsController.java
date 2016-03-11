/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hengyi.japp.print.client.controller;

import com.hengyi.japp.print.client.domain.Md;
import com.hengyi.japp.print.client.domain.Xd;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.concurrent.ScheduledService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author jzb
 */
public class MdsController implements Initializable {

    private final Logger log = LoggerFactory.getLogger(MdsController.class);
    private static ScheduledService<Void> uploadScheduledService;
    @FXML
    private TableView<Md> mdTable;
    @FXML
    private TableColumn<Md, Boolean> checkBoxColumn;
    @FXML
    private TableColumn<Md, String> uploadedColumn;
    @FXML
    private TableColumn<Md, String> printColumn;
    @FXML
    private TableColumn<Md, String> zdzflgColumn;
    @FXML
    private TableView<Xd> xdTable;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configureMdTable();
        mdTable.getSelectionModel().selectedItemProperty().addListener((ov, oldV, newV) -> {
            if (newV != null) {
                xdTable.setItems(newV.xdsProperty());
            }
        });
//        mdTable.setItems(observableArrayList(queryService.query(command)));
        if (uploadScheduledService != null) {
//TODO 如果正在上传sap，禁止所有按钮的操作
        }
    }

    private void configureMdTable() {
        checkBoxColumn.setCellFactory(CheckBoxTableCell.forTableColumn(checkBoxColumn));
//        printColumn.setCellValueFactory(p -> new ReadOnlyStringWrapper(p.getValue().isZprint() ? "是" : "否"));
        uploadedColumn.setCellValueFactory(p -> new ReadOnlyStringWrapper(p.getValue().getUploaded() ? "是" : "否"));
        zdzflgColumn.setCellValueFactory(p -> new ReadOnlyStringWrapper(p.getValue().getZdzflg() ? "是" : "否"));
    }

    @FXML
    private void handleSearchMd(ActionEvent e) throws Exception {
    }

    private List<Md> getSelectedMds() {
        return null;
    }

    @FXML
    private void handleUpload(ActionEvent e) {
    }

    @FXML
    private void handlePrintMd(ActionEvent e) {
    }

    @FXML
    private void handlePrintXd(ActionEvent e) {
    }

    @FXML
    private void handleDeleteMd(ActionEvent e) {
    }

}
