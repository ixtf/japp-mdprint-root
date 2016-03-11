/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hengyi.japp.print.client.domain;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Administrator
 */
public class SapYmmbanci {
    private final StringProperty zbanci = new SimpleStringProperty();
    private final StringProperty btext = new SimpleStringProperty();

    public String getZbanci() {
        return zbanci.get();
    }

    public StringProperty zbanciProperty() {
        return zbanci;
    }

    public void setZbanci(String zbanci) {
        this.zbanci.set(zbanci);
    }

    public String getBtext() {
        return btext.get();
    }

    public StringProperty btextProperty() {
        return btext;
    }

    public void setBtext(String btext) {
        this.btext.set(btext);
    }
}
