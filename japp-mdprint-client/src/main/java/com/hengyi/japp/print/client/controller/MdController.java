/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hengyi.japp.print.client.controller;

import com.hengyi.japp.print.client.MdValidate;
import com.hengyi.japp.print.client.Util;
import com.hengyi.japp.print.client.domain.*;
import com.hengyi.japp.print.client.exception.AppException;
import javafx.application.Platform;
import javafx.beans.binding.ObjectBinding;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.NumberStringConverter;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.hengyi.japp.print.client.Constant.tareF;
import static com.hengyi.japp.print.client.MainApp.operatorService;
import static javafx.collections.FXCollections.observableArrayList;

/**
 * FXML Controller class
 *
 * @author jzb
 */
public class MdController implements Initializable {
    private Md md;
    @FXML
    private Label chargLabel;
    @FXML
    private Label pmLabel;
    @FXML
    private Label ggLabel;
    @FXML
    private Label phLabel;
    @FXML
    private Label djLabel;
    @FXML
    private Label nxLabel;
    @FXML
    private Label szLabel;
    @FXML
    private DatePicker hsdatField;
    @FXML
    private ComboBox<SapYmmbanci> sapYmmbanciField;
    @FXML
    private TextField matnrField;
    @FXML
    private ComboBox<SapT001w> sapT001wField;
    @FXML
    private Label sapT001wLabel;
    @FXML
    private ComboBox<SapT001l> sapT001lField;
    @FXML
    private Label sapT001lLabel;
    @FXML
    private ComboBox<SapZpackage> sapZpackageField;
    @FXML
    private ComboBox<SapYmmzhix> sapYmmzhixField;
    @FXML
    private Label sapYmmzhixLabel;
    @FXML
    private ComboBox<SapYmmtongg> sapYmmtonggField;
    @FXML
    private Label sapYmmtonggLabel;
    @FXML
    private TextField zrolmgeField;
    @FXML
    private ComboBox<SapYmmmach> sapYmmmachField;
    @FXML
    private Label sapYmmmachLabel;
    @FXML
    private ComboBox<SapYmmche> sapYmmcheField;
    @FXML
    private Label sapYmmcheLabel;
    @FXML
    private TextField zcanmgeField;
    @FXML
    private CheckBox zdzflgField;
    @FXML
    private TextField zcnwghtField;
    @FXML
    private ListView<SapMara> sapMaraListView;
    @FXML
    private Label sumZsgwghtLabel;
    @FXML
    private Label sumZsnwghtLabel;
    @FXML
    private TableView<Xd> xdTable;
    @FXML
    private TableColumn<Xd, Number> zrolmgeColumn;
    @FXML
    private TableColumn<Xd, BigDecimal> zsgwghtColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        zrolmgeColumn.setCellValueFactory(o -> o.getValue().zrolmgeProperty());
        zsgwghtColumn.setCellValueFactory(o -> o.getValue().zsgwghtProperty());
        zsgwghtColumn.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
        zsgwghtColumn.setOnEditCommit(ev -> ev.getTableView().getItems().get(ev.getTablePosition().getRow()).setZsgwght(ev.getNewValue()));

        sapMaraListView.setOnMouseClicked(ev -> {
            if (ev.getClickCount() == 2) {
                selectSapMara(sapMaraListView.getSelectionModel().getSelectedItem());
                sapMaraListView.setVisible(false);
            }
        });
    }

    private void selectSapMara(SapMara sapMara) {
        md.setSapMara(sapMara);
//                List<SapT001w> sapT001ws = md.getSapMara().getSapT001ws();
//                sapT001wField.setItems(observableArrayList(sapT001ws));
//                sapT001wField.setValue(sapT001ws.get(0));
//                List<SapT001l> sapT001ls = sapT001wField.valueProperty().get().getSapT001ls();
//                sapT001lField.setItems(observableArrayList(sapT001ls));
//                sapT001lField.setValue(sapT001ls.get(0));
        pmLabel.setText(md.getSapMara().getPm());
        ggLabel.setText(md.getSapMara().getGg());
        phLabel.setText(md.getSapMara().getPh());
        djLabel.setText(md.getSapMara().getDj());
        nxLabel.setText(md.getSapMara().getNx());
        szLabel.setText(md.getSapMara().getSz());

        matnrField.setText(md.getSapMara().getMatnr());
    }

    @FXML
    private void autoCompleteSapMara(KeyEvent ev) {
        try {
            if (KeyCode.RIGHT.equals(ev.getCode())) {
                List<SapMara> sapMaras = md.getSapT001().autoCompleteSapMara(matnrField.getText());
                sapMaraListView.setItems(observableArrayList(sapMaras));
                sapMaraListView.setVisible(true);
                sapMaraListView.getSelectionModel().selectFirst();
            } else if (KeyCode.UP.equals(ev.getCode()) || KeyCode.DOWN.equals(ev.getCode())) {
                sapMaraListView.fireEvent(new KeyEvent(null, sapMaraListView, KeyEvent.KEY_PRESSED, ev.getCharacter(), ev.getText(), ev.getCode(), false, false, false, false));
            } else if (KeyCode.ENTER.equals(ev.getCode())) {
                selectSapMara(sapMaraListView.getSelectionModel().getSelectedItem());
                sapMaraListView.setVisible(false);
            }
        } catch (Exception ex) {
            Util.alertDialog(ex);
        }
    }

    @FXML
    private void handleSave(ActionEvent e) {
        try {
            operatorService.save(md);
        } catch (Exception ex) {
            Util.exceptionDialog(ex);
        }
    }

    @FXML
    private void handlePrintMd(ActionEvent e) {
        try {
            operatorService.printMd(md);
        } catch (Exception ex) {
            Util.exceptionDialog(ex);
        }
    }

    @FXML
    private void handlePrintXd(ActionEvent e) {
        try {
            operatorService.printXd(md);
        } catch (Exception ex) {
            Util.exceptionDialog(ex);
        }
    }

    public void initMd(Md _md) {
        md = _md;
        chargLabel.textProperty().bind(md.chargProperty());
        hsdatField.valueProperty().bindBidirectional(md.hsdatProperty());

        sapYmmbanciField.valueProperty().bindBidirectional(md.sapYmmbanciProperty());
        sapYmmbanciField.itemsProperty().bind(md.getSapT001().sapYmmbancisProperty());

        sapT001wField.valueProperty().bindBidirectional(md.sapT001wProperty());
        sapT001wField.itemsProperty().bind(md.getSapT001().sapT001wsProperty());
//        sapT001wField.valueProperty().addListener((ov, oldV, newV) -> sapT001wLabel.textProperty().bind(newV.name1Property()));
//        sapT001wField.setCellFactory(SAPT001WCELLFACTORY);

        sapT001lField.valueProperty().bindBidirectional(md.sapT001lProperty());
        sapT001lField.itemsProperty().bind(md.getSapT001w().sapT001lsProperty());
//        sapT001lField.valueProperty().addListener((ov, oldV, newV) -> sapT001lLabel.textProperty().bind(newV.lgobeProperty()));
//        sapT001lField.setCellFactory(SAPT001LCELLFACTORY);

        sapZpackageField.valueProperty().bindBidirectional(md.sapZpackageProperty());
        sapZpackageField.itemsProperty().bind(md.getSapT001().sapZpackagesProperty());

        sapYmmzhixField.valueProperty().addListener((ov, oldV, newV) -> sapYmmzhixLabel.setText(newV == null ? null : newV.getYzxwght() + " KG"));
        sapYmmzhixField.valueProperty().bindBidirectional(md.sapYmmzhixProperty());
        sapYmmzhixField.itemsProperty().bind(md.getSapT001().sapYmmzhixsProperty());
//        sapYmmzhixField.setCellFactory(SAPYMMZHIXCELLFACTORY);

        sapYmmtonggField.valueProperty().addListener((ov, oldV, newV) -> sapYmmtonggLabel.setText(newV == null ? null : newV.getZtgwght() + " KG"));
        sapYmmtonggField.valueProperty().bindBidirectional(md.sapYmmtonggProperty());
        sapYmmtonggField.itemsProperty().bind(md.getSapT001().sapYmmtonggsProperty());
//        sapYmmtonggField.setCellFactory(SAPYMMTONGGCELLFACTORY);
        zrolmgeField.textProperty().bindBidirectional(md.zrolmgeProperty(), new NumberStringConverter());

        sapYmmmachField.valueProperty().bindBidirectional(md.sapYmmmachProperty());
        sapYmmmachField.itemsProperty().bind(md.getSapT001().sapYmmmachsProperty());
//        sapYmmmachField.valueProperty().addListener((ov, oldV, newV) -> sapYmmmachLabel.setText(newV == null ? null : newV.getZplant()));
//        sapYmmmachField.setCellFactory(SAPYMMMACHCELLFACTORY);

//        sapYmmcheField.valueProperty().bindBidirectional(md.sapYmmcheProperty());
//        sapYmmcheField.itemsProperty().bind(md.getSapT001().sapymm());
//        sapYmmcheField.setCellFactory(SAPYMMCHECELLFACTORY);
//        sapYmmcheField.valueProperty().addListener((ov, oldV, newV) -> sapYmmcheLabel.setText(newV == null ? null : newV.getZchwght() + " KG"));

        zcanmgeField.textProperty().bindBidirectional(md.zcanmgeProperty(), new NumberStringConverter());
        zdzflgField.selectedProperty().bindBidirectional(md.zdzflgProperty());
        zcnwghtField.textProperty().bindBidirectional(md.zcnwghtProperty(), new BigDecimalStringConverter());

        xdTable.itemsProperty().bindBidirectional(md.xdsProperty());
        sumZsgwghtLabel.textProperty().bindBidirectional(md.zsgwghtProperty(), new BigDecimalStringConverter());
        sumZsnwghtLabel.textProperty().bindBidirectional(md.zsnwghtProperty(), new BigDecimalStringConverter());

        if (StringUtils.isNotBlank(md.getCharg())) {
            sapMaraListView.setVisible(false);
            Platform.runLater(() -> matnrField.requestFocus());
        }
    }

    @FXML
    private void handleAutoXd() {
        try {
            MdValidate.checkMdInput(md);
            md.xdsProperty().clear();
            md.setXds(FXCollections.observableArrayList(
                    IntStream.range(0, md.getZcanmge())
                            .mapToObj(i -> {
                                Xd xd = new Xd();
                                xd.zboxsnrProperty().set(i + 1);
                                xd.sapYmmmachProperty().bind(md.sapYmmmachProperty());
                                xd.zrolmgeProperty().bind(md.zrolmgeProperty());
                                return xd;
                            }).collect(Collectors.toList())
            ));
            if (md.getZdzflg()) {
                zsgwghtColumn.setEditable(false);
                md.getXds().forEach(xd -> {
                    xd.zsnwghtProperty().bind(md.zcnwghtProperty());
                    xd.zsgwghtProperty().bind(new ObjectBinding<BigDecimal>() {
                        {
                            bind(xd.zsnwghtProperty(), xd.zrolmgeProperty(), md.sapYmmzhixProperty(), md.sapYmmtonggProperty());
                        }

                        @Override
                        protected BigDecimal computeValue() {
                            return xd.getZsnwght().add(tareF.apply(md, xd));
                        }
                    });
                });
            } else {
                zsgwghtColumn.setEditable(true);
                md.getXds().forEach(xd -> {
                    xd.zsnwghtProperty().bind(new ObjectBinding<BigDecimal>() {
                        {
                            bind(xd.zsgwghtProperty(), xd.zrolmgeProperty(), md.sapYmmzhixProperty(), md.sapYmmtonggProperty());
                        }

                        @Override
                        protected BigDecimal computeValue() {
                            return xd.getZsgwght().add(tareF.apply(md, xd).negate());
                        }
                    });
                });
            }
            md.zsgwghtProperty().bind(new ObjectBinding<BigDecimal>() {
                {
                    md.getXds().forEach(xd -> bind(xd.zsgwghtProperty()));
                }

                @Override
                protected BigDecimal computeValue() {
                    BigDecimal result = BigDecimal.ZERO;
                    for (Xd xd : md.getXds()) {
                        result = result.add(xd.getZsgwght());
                    }
                    return result;
                }
            });
            md.zsnwghtProperty().bind(new ObjectBinding<BigDecimal>() {
                {
                    md.getXds().forEach(xd -> bind(xd.zsnwghtProperty()));
                }

                @Override
                protected BigDecimal computeValue() {
                    BigDecimal result = BigDecimal.ZERO;
                    for (Xd xd : md.getXds()) {
                        result = result.add(xd.getZsnwght());
                    }
                    return result;
                }
            });
        } catch (AppException e) {
            Util.alertDialog(e);
        }
    }
}
