/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hengyi.japp.print.client.controller;

import com.hengyi.japp.print.client.Util;
import com.hengyi.japp.print.client.domain.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.NumberStringConverter;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.hengyi.japp.print.client.Constant.*;
import static com.hengyi.japp.print.client.MainApp.operatorService;
import static javafx.collections.FXCollections.observableArrayList;

/**
 * FXML Controller class
 *
 * @author jzb
 */
public class MdController implements Initializable {
    private Md md;
    private final ObjectProperty<SapMara> sapMara = new SimpleObjectProperty<>();
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
    @FXML
    private TableColumn<Xd, BigDecimal> zsnwghtColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        zrolmgeColumn.setCellValueFactory(o -> o.getValue().zrolmgeProperty());
        zsgwghtColumn.setCellValueFactory(o -> o.getValue().zsgwghtProperty());
        zsnwghtColumn.setCellValueFactory(o -> o.getValue().zsnwghtProperty());
        initMd();

        sapMaraListView.setOnMouseClicked(ev -> {
            if (ev.getClickCount() == 2) {
                sapMara.set(sapMaraListView.getSelectionModel().getSelectedItem());
                md.sapMaraProperty().set(sapMara.get());
//                List<SapT001w> sapT001ws = md.getSapMara().getSapT001ws();
//                sapT001wField.setItems(observableArrayList(sapT001ws));
//                sapT001wField.setValue(sapT001ws.get(0));
//                List<SapT001l> sapT001ls = sapT001wField.valueProperty().get().getSapT001ls();
//                sapT001lField.setItems(observableArrayList(sapT001ls));
//                sapT001lField.setValue(sapT001ls.get(0));

                matnrField.setText(md.getSapMara().toString());
                pmLabel.setText(sapMara.get().getPm());
                ggLabel.setText(sapMara.get().getGg());
                phLabel.setText(sapMara.get().getPh());
                djLabel.setText(sapMara.get().getDj());
                nxLabel.setText(sapMara.get().getNx());
                szLabel.setText(sapMara.get().getSz());
                sapMaraListView.setVisible(false);
            }
        });
        configureXdTable();
    }

    @FXML
    private void autoCompleteSapMara(KeyEvent ev) {
        try {
            if (KeyCode.RIGHT.equals(ev.getCode())) {
                List<SapMara> sapMaras = operatorService.autoCompleteSapMara(matnrField.getText());
                sapMaraListView.setItems(observableArrayList(sapMaras));
                sapMaraListView.setVisible(true);
                sapMaraListView.getSelectionModel().selectFirst();
            } else if (KeyCode.UP.equals(ev.getCode()) || KeyCode.DOWN.equals(ev.getCode())) {
                sapMaraListView.fireEvent(new KeyEvent(null, sapMaraListView, KeyEvent.KEY_PRESSED, ev.getCharacter(), ev.getText(), ev.getCode(), false, false, false, false));
            } else if (KeyCode.ENTER.equals(ev.getCode())) {
                sapMara.set(sapMaraListView.getSelectionModel().getSelectedItem());
            }
        } catch (Exception ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("错误");
            alert.setContentText(ex.getLocalizedMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void handleAutoXd() {
        try {
            operatorService.autoXd(md);
        } catch (Exception ex) {
            Util.exceptionDialog(ex);
        }
    }

    @FXML
    private void handleSave(ActionEvent e) {
        try {
//            operatorService.save(md);
            chargLabel.setText(md.getCharg());
        } catch (Exception ex) {
            Util.exceptionDialog(ex);
        }
    }

    @FXML
    private void handlePrintMd(ActionEvent e) {
        try {
            handleSave(e);
//            operatorService.printMd(Lists.newArrayList(md));
        } catch (Exception ex) {
            Util.exceptionDialog(ex);
        }
    }

    @FXML
    private void handlePrintXd(ActionEvent e) {
        try {
            handleSave(e);
//            operatorService.printXd(md.getXds());
        } catch (Exception ex) {
            Util.exceptionDialog(ex);
        }
    }

    private void initMd() {
        md = new Md();
        xdTable.itemsProperty().bindBidirectional(md.xdsProperty());
        hsdatField.valueProperty().bindBidirectional(md.hsdatProperty());
        zrolmgeField.textProperty().bindBidirectional(md.zrolmgeProperty(), new NumberStringConverter());
        zcanmgeField.textProperty().bindBidirectional(md.zcanmgeProperty(), new NumberStringConverter());
        zdzflgField.selectedProperty().bindBidirectional(md.zdzflgProperty());
        zcnwghtField.textProperty().bindBidirectional(md.zcnwghtProperty(), new BigDecimalStringConverter());
        sumZsgwghtLabel.textProperty().bindBidirectional(md.zsgwghtProperty(), new BigDecimalStringConverter());
        sumZsnwghtLabel.textProperty().bindBidirectional(md.zsnwghtProperty(), new BigDecimalStringConverter());
        sapYmmbanciField.valueProperty().bindBidirectional(md.sapYmmbanciProperty());
        List<SapYmmbanci> sapYmmbancis = md.getSapT001().getSapYmmbancis();
        sapYmmbanciField.setItems(observableArrayList(sapYmmbancis));
        sapYmmbanciField.setValue(sapYmmbancis.get(0));
        sapZpackageField.valueProperty().bindBidirectional(md.sapZpackageProperty());
        List<SapZpackage> sapZpackages = md.getSapT001().getSapZpackages();
        sapZpackageField.setValue(sapZpackages.get(0));
        sapZpackageField.setItems(observableArrayList(sapZpackages));
        sapT001wField.setCellFactory(SAPT001WCELLFACTORY);
        sapT001wField.valueProperty().addListener((ov, oldV, newV) -> sapT001wLabel.setText(newV == null ? null : newV.getName1()));
        sapT001wField.valueProperty().bindBidirectional(md.sapT001wProperty());
        sapT001lField.setCellFactory(SAPT001LCELLFACTORY);
        sapT001lField.valueProperty().addListener((ov, oldV, newV) -> sapT001lLabel.setText(newV == null ? null : newV.getLgobe()));
        sapT001lField.valueProperty().bindBidirectional(md.sapT001lProperty());
        sapYmmzhixField.setCellFactory(SAPYMMZHIXCELLFACTORY);
        sapYmmzhixField.valueProperty().addListener((ov, oldV, newV) -> sapYmmzhixLabel.setText(newV == null ? null : newV.getYzxwght() + " KG"));
        sapYmmzhixField.valueProperty().bindBidirectional(md.sapYmmzhixProperty());
        List<SapYmmzhix> sapYmmzhixs = md.getSapT001().getSapYmmzhixs();
        sapYmmzhixField.setItems(observableArrayList(sapYmmzhixs));
        sapYmmzhixField.setValue(sapYmmzhixs.get(0));
        sapYmmtonggField.setCellFactory(SAPYMMTONGGCELLFACTORY);
        sapYmmtonggField.valueProperty().addListener((ov, oldV, newV) -> sapYmmtonggLabel.setText(newV == null ? null : newV.getZtgwght() + " KG"));
        sapYmmtonggField.valueProperty().bindBidirectional(md.sapYmmtonggProperty());
        List<SapYmmtongg> sapYmmtonggs = md.getSapT001().getSapYmmtonggs();
        sapYmmtonggField.setItems(observableArrayList(sapYmmtonggs));
        sapYmmtonggField.setValue(sapYmmtonggs.get(0));
        sapYmmmachField.setCellFactory(SAPYMMMACHCELLFACTORY);
        sapYmmmachField.valueProperty().addListener((ov, oldV, newV) -> sapYmmmachLabel.setText(newV == null ? null : newV.getZplant()));
        sapYmmmachField.valueProperty().bindBidirectional(md.sapYmmmachProperty());
        List<SapYmmmach> sapYmmmachs = md.getSapT001().getSapYmmmachs();
        sapYmmmachField.setItems(observableArrayList(sapYmmmachs));
        sapYmmmachField.setValue(sapYmmmachs.get(0));
        sapYmmcheField.setCellFactory(SAPYMMCHECELLFACTORY);
        sapYmmcheField.valueProperty().addListener((ov, oldV, newV) -> sapYmmcheLabel.setText(newV == null ? null : newV.getZchwght() + " KG"));
        sapYmmcheField.valueProperty().bindBidirectional(md.sapYmmcheProperty());
//        List<SapYmmche> sapYmmches = queryService.getSapYmmche(md.getSapT001());
//        sapYmmcheField.setItems(observableArrayList(sapYmmches));
//    	sapYmmcheField.setValue(sapYmmches.get(0));
    }

    private void configureXdTable() {
//        zrolmgeColumn.setCellValueFactory(new PropertyValueFactory<>("zrolmge"));
    }
}
