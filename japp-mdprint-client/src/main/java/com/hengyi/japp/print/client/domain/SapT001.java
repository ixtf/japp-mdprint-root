/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hengyi.japp.print.client.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

import static com.hengyi.japp.print.client.Constant.destination;
import static com.hengyi.japp.print.client.Constant.objectMapper;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

/**
 * @author Administrator
 */
public class SapT001 {
    private final StringProperty bukrs = new SimpleStringProperty();
    private final StringProperty butxt = new SimpleStringProperty();
    private final ListProperty<SapYmmbanci> sapYmmbancis = new SimpleListProperty<>();
    private final ListProperty<SapZpackage> sapZpackages = new SimpleListProperty<>();
    private final ListProperty<SapT001w> sapT001ws = new SimpleListProperty<>();
    private final ListProperty<SapYmmzhix> sapYmmzhixs = new SimpleListProperty<>();
    private final ListProperty<SapYmmtongg> sapYmmtonggs = new SimpleListProperty<>();
    private final ListProperty<SapYmmmach> sapYmmmachs = new SimpleListProperty<>();

    public void fetchSapYmmbancis() throws Exception {
        if (sapYmmbancis.get() != null) return;
        String json = destination.path("sapT001s").path(getBukrs()).path("sapYmmbancis").request(APPLICATION_JSON_TYPE).get(String.class);
        List<SapYmmbanci> list = objectMapper.readValue(json, TypeFactory.defaultInstance().constructCollectionType(List.class, SapYmmbanci.class));
        sapYmmbancis.set(FXCollections.observableArrayList(list));
    }

    public void fetchSapZpackages() throws Exception {
        if (sapZpackages.get() != null) return;
        String json = destination.path("sapT001s").path(getBukrs()).path("sapZpackages").request(APPLICATION_JSON_TYPE).get(String.class);
        List<SapZpackage> list = objectMapper.readValue(json, TypeFactory.defaultInstance().constructCollectionType(List.class, SapZpackage.class));
        sapZpackages.set(FXCollections.observableArrayList(list));
    }

    public String getBukrs() {
        return bukrs.get();
    }

    public StringProperty bukrsProperty() {
        return bukrs;
    }

    public void setBukrs(String bukrs) {
        this.bukrs.set(bukrs);
    }

    public String getButxt() {
        return butxt.get();
    }

    public StringProperty butxtProperty() {
        return butxt;
    }

    public void setButxt(String butxt) {
        this.butxt.set(butxt);
    }

    public ObservableList<SapYmmbanci> getSapYmmbancis() {
        return sapYmmbancis.get();
    }

    public ListProperty<SapYmmbanci> sapYmmbancisProperty() {
        return sapYmmbancis;
    }

    public void setSapYmmbancis(List<SapYmmbanci> sapYmmbancis) {
        this.sapYmmbancis.set(FXCollections.observableArrayList(sapYmmbancis));
    }

    public ObservableList<SapT001w> getSapT001ws() {
        return sapT001ws.get();
    }

    public ListProperty<SapT001w> sapT001wsProperty() {
        return sapT001ws;
    }

    public void setSapT001ws(List<SapT001w> sapT001ws) {
        this.sapT001ws.set(FXCollections.observableArrayList(sapT001ws));
    }

    public ObservableList<SapZpackage> getSapZpackages() {
        return sapZpackages.get();
    }

    public ListProperty<SapZpackage> sapZpackagesProperty() {
        return sapZpackages;
    }

    public void setSapZpackages(List<SapZpackage> sapZpackages) {
        this.sapZpackages.set(FXCollections.observableArrayList(sapZpackages));
    }

    public ObservableList<SapYmmzhix> getSapYmmzhixs() {
        return sapYmmzhixs.get();
    }

    public ListProperty<SapYmmzhix> sapYmmzhixsProperty() {
        return sapYmmzhixs;
    }

    public void setSapYmmzhixs(List<SapYmmzhix> sapYmmzhixs) {
        this.sapYmmzhixs.set(FXCollections.observableArrayList(sapYmmzhixs));
    }

    public ObservableList<SapYmmtongg> getSapYmmtonggs() {
        return sapYmmtonggs.get();
    }

    public ListProperty<SapYmmtongg> sapYmmtonggsProperty() {
        return sapYmmtonggs;
    }

    public void setSapYmmtonggs(List<SapYmmtongg> sapYmmtonggs) {
        this.sapYmmtonggs.set(FXCollections.observableArrayList(sapYmmtonggs));
    }

    public ObservableList<SapYmmmach> getSapYmmmachs() {
        return sapYmmmachs.get();
    }

    public ListProperty<SapYmmmach> sapYmmmachsProperty() {
        return sapYmmmachs;
    }

    public void setSapYmmmachs(List<SapYmmmach> sapYmmmachs) {
        this.sapYmmmachs.set(FXCollections.observableArrayList(sapYmmmachs));
    }

    @Override
    public String toString() {
        return getButxt();
    }


    public List<SapMara> autoCompleteSapMara(String q) throws Exception {
        String json = destination.path("sapT001s").path(getBukrs()).path("sapMaras").queryParam("q", q).queryParam("pageSize", 10).request(APPLICATION_JSON_TYPE).get(String.class);
        JsonNode list = objectMapper.readTree(json).get("result");
        return objectMapper.convertValue(list, TypeFactory.defaultInstance().constructCollectionType(List.class, SapMara.class));
    }
}
