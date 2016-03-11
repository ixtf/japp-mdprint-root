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
public class SapMara {
    private final StringProperty matnr = new SimpleStringProperty();
    private final StringProperty maktx = new SimpleStringProperty();

    public String getMatnr() {
        return matnr.get();
    }

    public StringProperty matnrProperty() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr.set(matnr);
    }

    public String getMaktx() {
        return maktx.get();
    }

    public StringProperty maktxProperty() {
        return maktx;
    }

    public void setMaktx(String maktx) {
        this.maktx.set(maktx);
        splitMaktx();
    }

    @Override
    public String toString() {
        return getMaktx();
    }


    public String getPm() {
        return pm;
    }


    public String getGg() {
        return gg;
    }


    public String getPh() {
        return ph;
    }


    public String getDj() {
        return dj;
    }


    public String getNx() {
        return nx;
    }


    public String getSz() {
        return sz;
    }

    private String pm;
    private String gg;
    private String ph;
    private String dj;
    private String nx;
    private String sz;

    private void splitMaktx() {
        String[] s = getMaktx().split("-");
        for (int i = 0; i < s.length; i++) {
            switch (i) {
                case 0:
                    pm = s[i];
                    break;
                case 1:
                    gg = s[i];
                    break;
                case 2:
                    ph = s[i];
                    break;
                case 3:
                    dj = s[i];
                    break;
                case 4:
                    nx = s[i];
                    break;
                default:
                    sz = s[i];
                    break;
            }
        }
    }
}
