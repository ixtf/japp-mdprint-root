/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hengyi.japp.print.client.domain;

import javafx.beans.property.*;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author jzb
 */
public class Md {
    private final StringProperty charg = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> hsdat = new SimpleObjectProperty<>(LocalDate.now());
    private final BooleanProperty zdzflg = new SimpleBooleanProperty(true);
    private final IntegerProperty zcanmge = new SimpleIntegerProperty(0);
    private final IntegerProperty zrolmge = new SimpleIntegerProperty(0);
    private final ObjectProperty<BigDecimal> zcgwght = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final ObjectProperty<BigDecimal> zcnwght = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final ObjectProperty<BigDecimal> zsgwght = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final ObjectProperty<BigDecimal> zsnwght = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final ListProperty<Xd> xds = new SimpleListProperty<>();
    private final ObjectProperty<SapYmmbanci> sapYmmbanci = new SimpleObjectProperty<>();
    private final ObjectProperty<SapMara> sapMara = new SimpleObjectProperty<>();
    private final ObjectProperty<SapT001> sapT001 = new SimpleObjectProperty<>();
    private final ObjectProperty<SapT001w> sapT001w = new SimpleObjectProperty<>();
    private final ObjectProperty<SapT001l> sapT001l = new SimpleObjectProperty<>();
    private final ObjectProperty<SapZpackage> sapZpackage = new SimpleObjectProperty<>();
    private final ObjectProperty<SapYmmzhix> sapYmmzhix = new SimpleObjectProperty<>();
    private final ObjectProperty<SapYmmtongg> sapYmmtongg = new SimpleObjectProperty<>();
    private final ObjectProperty<SapYmmmach> sapYmmmach = new SimpleObjectProperty<>();
    private final ObjectProperty<SapYmmche> sapYmmche = new SimpleObjectProperty<>();
    private final BooleanProperty uploaded = new SimpleBooleanProperty(false);
    private final BooleanProperty checkBox = new SimpleBooleanProperty(false);

    public String getCharg() {
        return charg.get();
    }

    public StringProperty chargProperty() {
        return charg;
    }

    public void setCharg(String charg) {
        this.charg.set(charg);
    }

    public LocalDate getHsdat() {
        return hsdat.get();
    }

    public ObjectProperty<LocalDate> hsdatProperty() {
        return hsdat;
    }

    public void setHsdat(LocalDate hsdat) {
        this.hsdat.set(hsdat);
    }

    public boolean getZdzflg() {
        return zdzflg.get();
    }

    public BooleanProperty zdzflgProperty() {
        return zdzflg;
    }

    public void setZdzflg(boolean zdzflg) {
        this.zdzflg.set(zdzflg);
    }

    public int getZcanmge() {
        return zcanmge.get();
    }

    public IntegerProperty zcanmgeProperty() {
        return zcanmge;
    }

    public void setZcanmge(int zcanmge) {
        this.zcanmge.set(zcanmge);
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

    public BigDecimal getZcgwght() {
        return zcgwght.get();
    }

    public ObjectProperty<BigDecimal> zcgwghtProperty() {
        return zcgwght;
    }

    public void setZcgwght(BigDecimal zcgwght) {
        this.zcgwght.set(zcgwght);
    }

    public BigDecimal getZcnwght() {
        return zcnwght.get();
    }

    public ObjectProperty<BigDecimal> zcnwghtProperty() {
        return zcnwght;
    }

    public void setZcnwght(BigDecimal zcnwght) {
        this.zcnwght.set(zcnwght);
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

    public ObservableList<Xd> getXds() {
        return xds.get();
    }

    public ListProperty<Xd> xdsProperty() {
        return xds;
    }

    public void setXds(ObservableList<Xd> xds) {
        this.xds.set(xds);
    }

    public SapYmmbanci getSapYmmbanci() {
        return sapYmmbanci.get();
    }

    public ObjectProperty<SapYmmbanci> sapYmmbanciProperty() {
        return sapYmmbanci;
    }

    public void setSapYmmbanci(SapYmmbanci sapYmmbanci) {
        this.sapYmmbanci.set(sapYmmbanci);
    }

    public SapMara getSapMara() {
        return sapMara.get();
    }

    public ObjectProperty<SapMara> sapMaraProperty() {
        return sapMara;
    }

    public void setSapMara(SapMara sapMara) {
        this.sapMara.set(sapMara);
    }

    public SapT001 getSapT001() {
        return sapT001.get();
    }

    public ObjectProperty<SapT001> sapT001Property() {
        return sapT001;
    }

    public void setSapT001(SapT001 sapT001) {
        this.sapT001.set(sapT001);
    }

    public SapT001w getSapT001w() {
        return sapT001w.get();
    }

    public ObjectProperty<SapT001w> sapT001wProperty() {
        return sapT001w;
    }

    public void setSapT001w(SapT001w sapT001w) {
        this.sapT001w.set(sapT001w);
    }

    public SapT001l getSapT001l() {
        return sapT001l.get();
    }

    public ObjectProperty<SapT001l> sapT001lProperty() {
        return sapT001l;
    }

    public void setSapT001l(SapT001l sapT001l) {
        this.sapT001l.set(sapT001l);
    }

    public SapZpackage getSapZpackage() {
        return sapZpackage.get();
    }

    public ObjectProperty<SapZpackage> sapZpackageProperty() {
        return sapZpackage;
    }

    public void setSapZpackage(SapZpackage sapZpackage) {
        this.sapZpackage.set(sapZpackage);
    }

    public SapYmmzhix getSapYmmzhix() {
        return sapYmmzhix.get();
    }

    public ObjectProperty<SapYmmzhix> sapYmmzhixProperty() {
        return sapYmmzhix;
    }

    public void setSapYmmzhix(SapYmmzhix sapYmmzhix) {
        this.sapYmmzhix.set(sapYmmzhix);
    }

    public SapYmmtongg getSapYmmtongg() {
        return sapYmmtongg.get();
    }

    public ObjectProperty<SapYmmtongg> sapYmmtonggProperty() {
        return sapYmmtongg;
    }

    public void setSapYmmtongg(SapYmmtongg sapYmmtongg) {
        this.sapYmmtongg.set(sapYmmtongg);
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

    public SapYmmche getSapYmmche() {
        return sapYmmche.get();
    }

    public ObjectProperty<SapYmmche> sapYmmcheProperty() {
        return sapYmmche;
    }

    public void setSapYmmche(SapYmmche sapYmmche) {
        this.sapYmmche.set(sapYmmche);
    }

    public boolean getUploaded() {
        return uploaded.get();
    }

    public BooleanProperty uploadedProperty() {
        return uploaded;
    }

    public void setUploaded(boolean uploaded) {
        this.uploaded.set(uploaded);
    }

    public boolean getCheckBox() {
        return checkBox.get();
    }

    public BooleanProperty checkBoxProperty() {
        return checkBox;
    }

    public void setCheckBox(boolean checkBox) {
        this.checkBox.set(checkBox);
    }
}
