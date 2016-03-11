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
public class SapYmmzhix {
    private final StringProperty yzxtype = new SimpleStringProperty();
    private final StringProperty yzxspec = new SimpleStringProperty();
    private final ObjectProperty<BigDecimal> yzxwght = new SimpleObjectProperty<>(BigDecimal.ZERO);

    public String getYzxtype() {
        return yzxtype.get();
    }

    public StringProperty yzxtypeProperty() {
        return yzxtype;
    }

    public void setYzxtype(String yzxtype) {
        this.yzxtype.set(yzxtype);
    }

    public String getYzxspec() {
        return yzxspec.get();
    }

    public StringProperty yzxspecProperty() {
        return yzxspec;
    }

    public void setYzxspec(String yzxspec) {
        this.yzxspec.set(yzxspec);
    }

    public BigDecimal getYzxwght() {
        return yzxwght.get();
    }

    public ObjectProperty<BigDecimal> yzxwghtProperty() {
        return yzxwght;
    }

    public void setYzxwght(BigDecimal yzxwght) {
        this.yzxwght.set(yzxwght);
    }
}
