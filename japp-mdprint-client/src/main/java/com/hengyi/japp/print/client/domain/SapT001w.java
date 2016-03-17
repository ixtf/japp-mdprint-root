/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hengyi.japp.print.client.domain;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * @author Administrator
 */
public class SapT001w {
    private final StringProperty werks = new SimpleStringProperty();
    private final StringProperty name1 = new SimpleStringProperty();
    private final ListProperty<SapT001l> sapT001ls = new SimpleListProperty<>();

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

    public ObservableList<SapT001l> getSapT001ls() {
        return sapT001ls.get();
    }

    public ListProperty<SapT001l> sapT001lsProperty() {
        return sapT001ls;
    }

    public void setSapT001ls(List<SapT001l> sapT001ls) {
        this.sapT001ls.set(FXCollections.observableArrayList(sapT001ls));
    }

    @Override
    public String toString() {
        return getWerks() + "\t" + getName1();
    }
}
