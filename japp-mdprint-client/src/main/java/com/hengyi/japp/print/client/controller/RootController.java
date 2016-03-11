/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hengyi.japp.print.client.controller;

import com.hengyi.japp.common.event.LogoutEvent;
import com.hengyi.japp.print.client.domain.Md;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import static com.hengyi.japp.print.client.Constant.eventBus;
import static com.hengyi.japp.print.client.MainApp.*;

/**
 * FXML Controller class
 *
 * @author jzb
 */
public class RootController {

    @FXML
    private void handleMdNew() throws Exception {
        Md md = new Md();
        md.setSapT001(sapT001);
        gotoMd(md);
    }

    @FXML
    private void handleMdManage() throws Exception {
        gotoMds();
    }

    @FXML
    private void logout(ActionEvent e) {
        eventBus.post(new LogoutEvent(null, null));
    }

}
