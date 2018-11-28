package org.telegram.makarovd.wschat;

import java.net.URI;
import java.net.URISyntaxException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application{
  private WebSocketClient webSocketClient;
  private MessagePrinter messagePrinter;

  public static void main(String[] args) throws URISyntaxException {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    initChatFrame(primaryStage);
    initWebsocketClient();
  }

  private void initChatFrame(Stage primaryStage) {
    BorderPane rootLayout = new BorderPane();

    TextArea chatArea = new TextArea();
    chatArea.setEditable(false);
    rootLayout.setCenter(chatArea);
    messagePrinter = m -> Platform.runLater(() -> chatArea.appendText('\n' + m));

    BorderPane borderPane = new BorderPane();

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

    Scene scene = new Scene(rootLayout);
    primaryStage.setScene(scene);
    primaryStage.setTitle("ws-chat-client");
    primaryStage.setHeight(500);
    primaryStage.setWidth(500);
    primaryStage.show();
    message.requestFocus();
  }

  private void initWebsocketClient() throws URISyntaxException {
    URI uri = new URI("ws://localhost:8080/ws-chat/chat");
    webSocketClient = new WebSocketClientImpl(uri, messagePrinter::print);
    webSocketClient.connect();
  }

  private interface MessagePrinter {
    void print(String message);
  }
}
