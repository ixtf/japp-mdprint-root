/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hengyi.japp.print.client.domain;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.math.BigDecimal;

/**
 * @author jzb
 */
public class Xd implements Comparable<Xd> {
    private final IntegerProperty zboxsnr = new SimpleIntegerProperty(0);
    private final IntegerProperty zrolmge = new SimpleIntegerProperty(0);
    private final ObjectProperty<BigDecimal> zsgwght = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final ObjectProperty<BigDecimal> zsnwght = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final ObjectProperty<SapYmmmach> sapYmmmach = new SimpleObjectProperty<>();
    private final IntegerProperty printCount = new SimpleIntegerProperty(0);

    public int getZboxsnr() {
        return zboxsnr.get();
    }

    public IntegerProperty zboxsnrProperty() {
        return zboxsnr;
    }

    public void setZboxsnr(int zboxsnr) {
        this.zboxsnr.set(zboxsnr);
    }

    public int getZrolmge() {
        return zrolmge.get();
    }

    public IntegerProperty zrolmgeProperty() {
        return zrolmge;
    }

    public void setZrolmge(int zrolmge) {
        this.zrolmge.set(zrolmge);
    }

    public BigDecimal getZsgwght() {
        return zsgwght.get();
    }

    public ObjectProperty<BigDecimal> zsgwghtProperty() {
        return zsgwght;
    }

    public void setZsgwght(BigDecimal zsgwght) {
        this.zsgwght.set(zsgwght);
    }

    public BigDecimal getZsnwght() {
        return zsnwght.get();
    }

    public ObjectProperty<BigDecimal> zsnwghtProperty() {
        return zsnwght;
    }

    public void setZsnwght(BigDecimal zsnwght) {
        this.zsnwght.set(zsnwght);
    }

    public SapYmmmach getSapYmmmach() {
        return sapYmmmach.get();
    }

    public ObjectProperty<SapYmmmach> sapYmmmachProperty() {
        return sapYmmmach;
    }

    public void setSapYmmmach(SapYmmmach sapYmmmach) {
        this.sapYmmmach.set(sapYmmmach);
    }

    public int getPrintCount() {
        return printCount.get();
    }

    public IntegerProperty printCountProperty() {
        return printCount;
    }

    public void setPrintCount(int printCount) {
        this.printCount.set(printCount);
    }

    @Override
    public int compareTo(Xd o) {
        return this.getZboxsnr() > o.getZboxsnr() ? 1 : 0;
    }
}

