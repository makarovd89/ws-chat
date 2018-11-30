package org.telegram.makarovd.wschat;

import java.net.URI;
import java.net.URISyntaxException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainApp extends Application{
  private Stage primaryStage;
  private Scene chatScene;
  private Scene loginScene;
  private MessagePrinter messagePrinter;
  private WebSocketClient webSocketClient;

  @Override
  public void start(Stage primaryStage) throws Exception {
    this.primaryStage = primaryStage;
    initLoginScene();
    initChatScene();
    initWebsocketClient();
    showLoginScene();
  }

  private void initLoginScene() {
    primaryStage.setTitle("ws-chat-client");

    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(25, 25, 25, 25));

    Label userName = new Label("Login");
    grid.add(userName, 0, 0);
    TextField userTextField = new TextField();
    grid.add(userTextField, 1, 0, 2, 1);

    Label pw = new Label("Password");
    grid.add(pw, 0, 1);
    PasswordField pwBox = new PasswordField();
    grid.add(pwBox, 1, 1, 2, 1);
    userTextField.setOnAction(event -> pwBox.requestFocus());

    Button btn = new Button("Sign in");
    HBox hbBtn = new HBox(10);
    hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
    hbBtn.getChildren().add(btn);
    grid.add(hbBtn, 2, 3);
    Text exceptionCause = new Text();
    grid.add(exceptionCause, 0, 3, 2, 1);
    exceptionCause.setFill(Color.FIREBRICK);
    EventHandler<ActionEvent> signIn = e -> {
      try {
        webSocketClient.connect(userTextField.getText(), pwBox.getText());
        showChatScene();
      } catch (Exception ex) {
        exceptionCause.setText(ex.getCause().getMessage());
        pwBox.setText("");
      }
    };
    pwBox.setOnAction(signIn);
    btn.setOnAction(signIn);
    loginScene = new Scene(grid);
  }

  private void initChatScene() {
    BorderPane rootLayout = new BorderPane();
    rootLayout.setPadding(new Insets(5, 5, 5, 5));

    TextArea chatArea = new TextArea();
    chatArea.setEditable(false);
    messagePrinter = m -> Platform.runLater(() -> chatArea.appendText('\n' + m));
    rootLayout.setCenter(chatArea);

    BorderPane borderPane = new BorderPane();
    borderPane.setPadding(new Insets(5, 0, 0, 0));

    TextField message = new TextField();
    borderPane.setCenter(message);
    EventHandler<ActionEvent> sendMessage = event -> {
      String text = message.getCharacters().toString();
      if (!text.isEmpty()){
        webSocketClient.sendMessage(message.getText());
        message.setText("");
      }
    };
    message.setOnAction(sendMessage);

    Button btn = new Button("Send");
    btn.setOnAction(sendMessage);
    borderPane.setRight(btn);
    rootLayout.setBottom(borderPane);

    chatScene = new Scene(rootLayout);
    message.requestFocus();
  }

  private void showChatScene() {
    primaryStage.hide();
    primaryStage.setScene(chatScene);
    primaryStage.setTitle("ws-chat-client");
    primaryStage.setHeight(500);
    primaryStage.setWidth(500);
    primaryStage.setResizable(true);
    primaryStage.show();
  }

  private void showLoginScene() {
    primaryStage.setScene(loginScene);
    primaryStage.setHeight(180);
    primaryStage.setWidth(300);
    primaryStage.setResizable(false);
    primaryStage.show();
  }

  private void initWebsocketClient() throws URISyntaxException {
    URI uri = new URI("ws://localhost:8080/ws-chat/chat");
    webSocketClient = new WebSocketClientImpl(uri, messagePrinter::print);
  }

  private interface MessagePrinter {
    void print(String message);
  }
}
