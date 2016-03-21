/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hengyi.japp.print.client;

import com.hengyi.japp.print.client.domain.Md;
import com.hengyi.japp.print.client.exception.AppException;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author Administrator
 */
public class MdValidate {

    public static void checkMdInput(Md md) throws AppException {
        StringBuilder sb = new StringBuilder();
        if (md.getHsdat() == null) {
            sb.append("请输入生产日期！\n");
        }
        if (md.getSapYmmbanci() == null) {
            sb.append("请输入班次！\n");
        }
        if (md.getSapMara() == null) {
            sb.append("请输入物料！\n");
        }
        if (md.getSapT001w() == null) {
            sb.append("请输入工厂！\n");
        }
        if (md.getSapT001l() == null) {
            sb.append("请输入库存地！\n");
        }
        if (md.getSapZpackage() == null) {
            sb.append("请输入打包类型！\n");
        }
        if (md.getSapYmmzhix() == null) {
            sb.append("请输入纸箱类型！\n");
        }
        if (md.getSapYmmtongg() == null) {
            sb.append("请输入筒管类型！\n");
        }
        if (md.getZrolmge() <= 0) {
            sb.append("请输入每箱筒管数！\n");
        }
        if (md.getZcanmge() <= 0) {
            sb.append("请输入箱数！\n");
        }
        if (md.getZdzflg() && (md.getZcnwght() == null || md.getZcnwght().doubleValue() <= 0)) {
            sb.append("定重码单，请输入丝净重！\n");
        }
        String error = sb.toString();
        if (!isBlank(error)) {
            throw new AppException(error);
        }
    }
}
