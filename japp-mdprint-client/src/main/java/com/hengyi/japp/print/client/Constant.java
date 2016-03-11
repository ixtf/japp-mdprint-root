/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hengyi.japp.print.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.common.eventbus.EventBus;
import com.hengyi.japp.print.client.domain.*;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author jzb
 */
public class Constant {

    public static final String ADMIN_UUID = "d3590bbd-b232-405b-8cc4-3adeecaf41d7";

    public static final ResourceBundle MSG = ResourceBundle.getBundle("messages", Locale.CHINA);
    public static final EventBus eventBus = new EventBus();
    public static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true)
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
            .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    public static final WebTarget destination = ClientBuilder.newClient(new ClientConfig(JacksonJsonProvider.class)).target("http://127.0.0.1:9999/api");

    public static final class fxml {
        public static final String LOGIN = "/fxml/login.fxml";
        public static final String ROOT = "/fxml/root.fxml";
        public static final String MD = "/fxml/md.fxml";
        public static final String MDS = "/fxml/mds.fxml";
        public static final String MD_SEARCH = "/fxml/md_search.fxml";
        public static final String MD_PRINT = "/fxml/md_print_3000.fxml";
    }

    public static final Callback<ListView<SapT001w>, ListCell<SapT001w>> SAPT001WCELLFACTORY = l -> new ListCell<SapT001w>() {
        @Override
        protected void updateItem(SapT001w item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
                setGraphic(null);
            } else {
                setText(item.getWerks() + "\t" + item.getName1());
            }
        }

    };

    public static final Callback<ListView<SapT001l>, ListCell<SapT001l>> SAPT001LCELLFACTORY = l -> new ListCell<SapT001l>() {

        @Override
        protected void updateItem(SapT001l item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
                setGraphic(null);
            } else {
                setText(item.getLgort() + "\t" + item.getLgobe());
            }
        }

    };
    public static final Callback<ListView<SapYmmzhix>, ListCell<SapYmmzhix>> SAPYMMZHIXCELLFACTORY = l -> new ListCell<SapYmmzhix>() {

        @Override
        protected void updateItem(SapYmmzhix item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
                setGraphic(null);
            } else {
                setText(item.getYzxtype() + "\t" + item.getYzxspec());
            }
        }
    };
    public static final Callback<ListView<SapYmmtongg>, ListCell<SapYmmtongg>> SAPYMMTONGGCELLFACTORY = l -> new ListCell<SapYmmtongg>() {

        @Override
        protected void updateItem(SapYmmtongg item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
                setGraphic(null);
            } else {
                setText(item.getZtgtype() + "\t" + item.getZtgspec());
            }
        }
    };
    public static final Callback<ListView<SapYmmmach>, ListCell<SapYmmmach>> SAPYMMMACHCELLFACTORY = l -> new ListCell<SapYmmmach>() {

        @Override
        protected void updateItem(SapYmmmach item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
                setGraphic(null);
            } else {
                setText(item.getZmcnum() + "\t" + item.getZplant());
            }
        }
    };
    public static final Callback<ListView<SapYmmche>, ListCell<SapYmmche>> SAPYMMCHECELLFACTORY = l -> new ListCell<SapYmmche>() {

        @Override
        protected void updateItem(SapYmmche item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
                setGraphic(null);
            } else {
                setText(item.toString());
            }
        }
    };
}
