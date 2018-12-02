package org.telegram.makarovd.wschat.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ChatWindow extends AbstractWindow {
  private final TextArea chatArea;
  private MessageSender messageSender;

  public ChatWindow(Stage primaryStage) {
    BorderPane rootLayout = new BorderPane();
    rootLayout.setPadding(new Insets(5, 5, 5, 5));

    chatArea = new TextArea();
    chatArea.setEditable(false);
    rootLayout.setCenter(chatArea);

    BorderPane borderPane = new BorderPane();
    borderPane.setPadding(new Insets(5, 0, 0, 0));

    TextField message = new TextField();
    borderPane.setCenter(message);
    EventHandler<ActionEvent> sendMessage = event -> {
      String text = message.getCharacters().toString();
      if (!text.isEmpty()){
        messageSender.send(message.getText());
        message.setText("");
      }
    };
    message.setOnAction(sendMessage);

    Button btn = new Button("Send");
    btn.setOnAction(sendMessage);
    borderPane.setRight(btn);
    rootLayout.setBottom(borderPane);

    setStage(primaryStage);
    setScene(new Scene(rootLayout));

    message.requestFocus();
  }

  @Override
  protected void setStageParameters() {
    stage.setHeight(500);
    stage.setWidth(500);
    stage.setResizable(true);
  }

  public void setMessageSender(MessageSender messageSender) {
    this.messageSender = messageSender;
  }

  public void printMessage(String message) {
    Platform.runLater(() -> chatArea.appendText('\n' + message));
  }

  public interface MessageSender {
    void send(String message);
  }
}
