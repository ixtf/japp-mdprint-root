/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hengyi.japp.print.client.domain;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;

/**
 * @author Administrator
 */
public class SapYmmche {
    private StringProperty zchnum = new SimpleStringProperty();
    private StringProperty ctext = new SimpleStringProperty();
    private ObjectProperty<BigDecimal> zchwght = new SimpleObjectProperty<>(BigDecimal.ZERO);

    public String getZchnum() {
        return zchnum.get();
    }

    public StringProperty zchnumProperty() {
        return zchnum;
    }

    public void setZchnum(String zchnum) {
        this.zchnum.set(zchnum);
    }

    public String getCtext() {
        return ctext.get();
    }

    public StringProperty ctextProperty() {
        return ctext;
    }

    public void setCtext(String ctext) {
        this.ctext.set(ctext);
    }

    public BigDecimal getZchwght() {
        return zchwght.get();
    }

    public ObjectProperty<BigDecimal> zchwghtProperty() {
        return zchwght;
    }

    public void setZchwght(BigDecimal zchwght) {
        this.zchwght.set(zchwght);
    }
}
