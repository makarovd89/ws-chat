package org.telegram.makarovd.wschat;

import java.net.URI;
import javafx.application.Application;
import javafx.stage.Stage;
import org.telegram.makarovd.wschat.view.ChatWindow;
import org.telegram.makarovd.wschat.view.LoginWindow;

public class MainApp extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    ChatWindow  chatWindow = new ChatWindow(primaryStage);
    LoginWindow loginWindow = new LoginWindow(primaryStage);

    URI uri = new URI("ws://localhost:8080/ws-chat/chat");
    WebSocketClient webSocketClient = new WebSocketClientImpl(uri, chatWindow::printMessage);

    chatWindow.setMessageSender(webSocketClient::sendMessage);

    loginWindow.setConnector( (login, password) -> {
      try {
        webSocketClient.connect(login, password);
        chatWindow.show();
      } catch (Exception e) {
        loginWindow.printException(e.getCause().getMessage());
      }
    });

    loginWindow.show();
  }
}
