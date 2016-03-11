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
public class SapT001w {
    private final StringProperty werks = new SimpleStringProperty();
    private final StringProperty name1 = new SimpleStringProperty();

    public String getWerks() {
        return werks.get();
    }

    public StringProperty werksProperty() {
        return werks;
    }

    public void setWerks(String werks) {
        this.werks.set(werks);
    }

    public String getName1() {
        return name1.get();
    }

    public StringProperty name1Property() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1.set(name1);
    }
}
