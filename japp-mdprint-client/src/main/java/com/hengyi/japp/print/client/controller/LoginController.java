/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hengyi.japp.print.client.controller;

import com.hengyi.japp.print.client.Event.SapT001LoginEvent;
import com.hengyi.japp.print.client.Util;
import com.hengyi.japp.print.client.domain.SapT001;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Region;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.hengyi.japp.print.client.Constant.eventBus;
import static com.hengyi.japp.print.client.MainApp.operatorService;

/**
 * @author jzb
 */
public class LoginController implements Initializable {
    @FXML
    private ChoiceBox<SapT001> sapT001ChoiceBox;
    @FXML
    private Region region;
    @FXML
    private ProgressIndicator p;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            List<SapT001> items = operatorService.listSapT001();
            sapT001ChoiceBox.setItems(FXCollections.observableArrayList(items));
            sapT001ChoiceBox.setValue(items.get(0));
        } catch (Exception e) {
            Util.exceptionDialog(e);
            System.exit(-1);
        }
    }

    @FXML
    private void handleLogin() throws InterruptedException {
        LoginTask loginTask = new LoginTask(sapT001ChoiceBox.getValue());
        p.visibleProperty().bind(loginTask.runningProperty());
        region.visibleProperty().bind(loginTask.runningProperty());
        new Thread(loginTask).start();
    }

    static class LoginTask extends Task<Void> {
        private final SapT001 sapT001;

        public LoginTask(SapT001 sapT001) {
            this.sapT001 = sapT001;
        }

        @Override
        protected void failed() {
            //TODO dialog
        }

        @Override
        protected void succeeded() {
            eventBus.post(new SapT001LoginEvent(sapT001));
        }

        @Override
        protected Void call() throws Exception {
            operatorService.handleLogin(sapT001);
            return null;
        }
    }
}
