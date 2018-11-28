package org.telegram.makarovd.wschat;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    BorderPane rootLayout = new BorderPane();

    TextArea chatArea = new TextArea();
    chatArea.setEditable(false);
    rootLayout.setCenter(chatArea);

    BorderPane borderPane = new BorderPane();

    TextField message = new TextField();
    borderPane.setCenter(message);

    Button btn = new Button("Send");
    borderPane.setRight(btn);
    rootLayout.setBottom(borderPane);

    Scene scene = new Scene(rootLayout);
    primaryStage.setScene(scene);
    primaryStage.setTitle("ws-chat-client");
    primaryStage.setHeight(500);
    primaryStage.setWidth(500);
    primaryStage.show();
    message.requestFocus();
  }
}
