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
public class SapYmmmach {
    private final StringProperty zmcnum = new SimpleStringProperty();
    private final StringProperty zplant = new SimpleStringProperty();

    public String getZmcnum() {
        return zmcnum.get();
    }

    public StringProperty zmcnumProperty() {
        return zmcnum;
    }

    public void setZmcnum(String zmcnum) {
        this.zmcnum.set(zmcnum);
    }

    public String getZplant() {
        return zplant.get();
    }

    public StringProperty zplantProperty() {
        return zplant;
    }

    public void setZplant(String zplant) {
        this.zplant.set(zplant);
    }

    @Override
    public String toString() {
        return getZmcnum() + "\t" + getZplant();
    }
}
