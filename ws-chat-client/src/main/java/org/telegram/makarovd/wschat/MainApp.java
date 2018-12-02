package org.telegram.makarovd.wschat;

import java.net.URI;
import javafx.application.Application;
import javafx.stage.Stage;
import org.telegram.makarovd.wschat.view.ChatStage;
import org.telegram.makarovd.wschat.view.LoginStage;

public class MainApp extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {
    ChatStage chatStage = new ChatStage();
    LoginStage loginStage = new LoginStage();

    URI uri = new URI("ws://localhost:8080/ws-chat/chat");
    WebSocketClient webSocketClient = new WebSocketClientImpl(uri, chatStage::printMessage);

    chatStage.setMessageSender(webSocketClient::sendMessage);
    loginStage.setConnector((login, password) -> {
      try {
        webSocketClient.connect(login, password);
        chatStage.show();
        loginStage.hide();
      } catch (Exception e) {
        loginStage.printException(e.getCause().getMessage());
      }
    });

    loginStage.show();
  }
}
