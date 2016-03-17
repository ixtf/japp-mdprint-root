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
public class SapYmmtongg {
    private final StringProperty ztgtype = new SimpleStringProperty();
    private final StringProperty ztgspec = new SimpleStringProperty();
    private final ObjectProperty<BigDecimal> ztgwght = new SimpleObjectProperty<>(BigDecimal.ZERO);

    public String getZtgtype() {
        return ztgtype.get();
    }

    public StringProperty ztgtypeProperty() {
        return ztgtype;
    }

    public void setZtgtype(String ztgtype) {
        this.ztgtype.set(ztgtype);
    }

    public String getZtgspec() {
        return ztgspec.get();
    }

    public StringProperty ztgspecProperty() {
        return ztgspec;
    }

    public void setZtgspec(String ztgspec) {
        this.ztgspec.set(ztgspec);
    }

    public BigDecimal getZtgwght() {
        return ztgwght.get();
    }

    public ObjectProperty<BigDecimal> ztgwghtProperty() {
        return ztgwght;
    }

    public void setZtgwght(BigDecimal ztgwght) {
        this.ztgwght.set(ztgwght);
    }

    @Override
    public String toString() {
        return getZtgtype() + "\t" + getZtgspec();
    }
}
