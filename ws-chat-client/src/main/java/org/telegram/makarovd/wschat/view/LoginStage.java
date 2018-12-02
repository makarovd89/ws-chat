package org.telegram.makarovd.wschat.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class LoginStage extends WsChatStage {
  private Connector connector;
  private Text exceptionText;
  private TextField pwBox;

  public LoginStage() {
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(25, 25, 25, 25));

    grid.add(new Label("Login"), 0, 0);
    TextField loginField = new TextField();
    grid.add(loginField, 1, 0, 2, 1);

    grid.add(new Label("Password"), 0, 1);
    pwBox = new PasswordField();
    grid.add(pwBox, 1, 1, 2, 1);
    loginField.setOnAction(event -> pwBox.requestFocus());

    Button btn = new Button("Sign in");
    HBox hbBtn = new HBox(10);
    hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
    hbBtn.getChildren().add(btn);
    grid.add(hbBtn, 2, 3);

    exceptionText = new Text();
    grid.add(exceptionText, 0, 3, 2, 1);
    exceptionText.setFill(Color.FIREBRICK);
    EventHandler<ActionEvent> signIn;
    signIn = e ->  connector.connect(loginField.getText(), pwBox.getText());
    pwBox.setOnAction(signIn);
    btn.setOnAction(signIn);

    setHeight(180);
    setWidth(300);
    setResizable(false);
    setScene(new Scene(grid));
  }

  public void printException(String text) {
    exceptionText.setText(text);
  }

  public void setConnector(Connector connector) {
    this.connector = connector;
  }

  public interface Connector {
    void connect(String login, String password);
  }
}
