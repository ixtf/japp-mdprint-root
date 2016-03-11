package com.hengyi.japp.print.server.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by Administrator on 2016/2/28.
 */
public class MdUpdateDTO {
    @NotBlank
    private String bukrs;
    @NotNull
    @Size(min = 1)
    private List<Xd> xds;

    public static class Xd {
    }

    public String getBukrs() {
        return bukrs;
    }

    public void setBukrs(String bukrs) {
        this.bukrs = bukrs;
    }

    public List<Xd> getXds() {
        return xds;
    }

    public void setXds(List<Xd> xds) {
        this.xds = xds;
    }
}
