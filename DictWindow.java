import javafx.application.*;
import javafx.scene.control.TextField;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.*;

public class DictWindow extends Application {
  public static final ObservableList<String> entries = FXCollections.observableArrayList();

  @Override
  public void start(Stage primaryStage) throws Exception {
    TextField textField = new TextField("Type Me");

    ListView<String> listView = new ListView<>();
    listView.setItems(entries);

    listView.getSelectionModel().selectedItemProperty().addListener((o, old_, new_) -> {
      textField.setText(new_);
    });

    // http://stackoverflow.com/a/31370556
    textField.textProperty().addListener((obs_, old_, new_) -> {
      entries.add(old_);
    });

    VBox stackPane = new VBox();
    stackPane.getChildren().add(textField);
    stackPane.getChildren().add(listView);
    stackPane.getPadding();
    Group root = new Group(stackPane);
    Scene scene = new Scene(root, 400, 300);
    primaryStage.setTitle("Hello World");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
