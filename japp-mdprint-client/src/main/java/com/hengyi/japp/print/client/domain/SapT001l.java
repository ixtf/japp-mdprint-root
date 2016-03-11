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
public class SapT001l {
    private final StringProperty lgort = new SimpleStringProperty();
    private final StringProperty lgobe = new SimpleStringProperty();

    public String getLgort() {
        return lgort.get();
    }

    public StringProperty lgortProperty() {
        return lgort;
    }

    public void setLgort(String lgort) {
        this.lgort.set(lgort);
    }

    public String getLgobe() {
        return lgobe.get();
    }

    public StringProperty lgobeProperty() {
        return lgobe;
    }

    public void setLgobe(String lgobe) {
        this.lgobe.set(lgobe);
    }
}
