package org.telegram.makarovd.wschat.view;

import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class AbstractWindow {
  Scene scene;
  Stage stage;

  void setStage(Stage stage) {
    this.stage = stage;
  }

  void setScene(Scene scene) {
    this.scene = scene;
  }

  public void show() {
    stage.hide();
    setStageParameters();
    stage.setScene(scene);
    stage.show();
  }

  protected abstract void setStageParameters();
}
