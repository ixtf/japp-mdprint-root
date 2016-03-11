package com.hengyi.japp.print.client;

import com.google.common.eventbus.Subscribe;
import com.hengyi.japp.common.event.DestroyEvent;
import com.hengyi.japp.common.event.LogoutEvent;
import com.hengyi.japp.print.client.Event.SapT001LoginEvent;
import com.hengyi.japp.print.client.domain.Md;
import com.hengyi.japp.print.client.domain.SapT001;
import com.hengyi.japp.print.client.service.OperatorService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import static com.hengyi.japp.print.client.Constant.MSG;
import static com.hengyi.japp.print.client.Constant.eventBus;
import static com.hengyi.japp.print.client.Constant.fxml.*;

public class MainApp extends Application {
    public static final OperatorService operatorService = new OperatorService();
    public static SapT001 sapT001;

    private static Stage primaryStage;
    private static BorderPane rootLayout;

    @Subscribe
    public void onEvent(SapT001LoginEvent ev) throws Exception {
        sapT001 = ev.getSapT001();
        primaryStage.setTitle(new StringBuilder(primaryStage.getTitle())
                .append("-").append(ev.getSapT001().getBukrs())
                .append("[").append(ev.getSapT001().getButxt()).append("]").toString());
        gotoRoot();
    }

    @Subscribe
    public void onEvent(LogoutEvent ev) throws Exception {
        primaryStage.setTitle(MSG.getString("project.title"));
        gotoLogin();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();
        eventBus.register(this);
    }

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setTitle(MSG.getString("project.title"));
        primaryStage.centerOnScreen();
        gotoLogin();
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> {
            eventBus.post(new DestroyEvent());
            Platform.exit();
        });
    }

    private static void gotoLogin() throws Exception {
        Scene scene = new Scene(FXMLLoader.load(MainApp.class.getResource(LOGIN), MSG));
        primaryStage.setScene(scene);
    }

    private static void gotoRoot() throws Exception {
        rootLayout = FXMLLoader.load(MainApp.class.getResource(ROOT), MSG);
        primaryStage.setScene(new Scene(rootLayout));
        primaryStage.setMaximized(true);
    }

    public static void gotoMd(Md md) throws Exception {
        rootLayout.setCenter(FXMLLoader.load(MainApp.class.getResource(MD), MSG));
    }

    public static void gotoMds() throws Exception {
        rootLayout.setCenter(FXMLLoader.load(MainApp.class.getResource(MDS), MSG));
    }

//    public static void gotoMdSearch(MdQueryCommand command) throws Exception {
//        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(MD_SEARCH), MSG);
//        MdSearchController controller = loader.getController();
//        Stage stage = new Stage();
//        stage.initOwner(primaryStage);
//        stage.initStyle(StageStyle.UTILITY);
//        stage.initModality(Modality.WINDOW_MODAL);
//        controller.setStage(stage);
//        Scene scene = new Scene((Parent) loader.load());
//        stage.setScene(scene);
//        stage.showAndWait();
//    }

}
