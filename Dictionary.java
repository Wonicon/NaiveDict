import javafx.application.*;
import javafx.scene.control.TextField;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.*;

public class Dictionary extends Application {
  public static final ObservableList<String> entries = FXCollections.observableArrayList();

  private TrieTree index;

  private PrefixVisitor prefixAssociation;

  private String selectedEntry = null;

  @Override
  public void start(Stage primaryStage) throws Exception {
    // Init resources
    index = TrieTree.createTree("dictionary.txt");
    prefixAssociation = new PrefixVisitor(index);


    // Init UI elements
    TextField textField = new TextField("Type Me");

    ListView<String> listView = new ListView<>();
    listView.setItems(entries);

    listView.getSelectionModel().selectedItemProperty().addListener((o, old_, new_) -> {
      if (new_ != null) {
        selectedEntry = new_;
        textField.setText(new_);
      }  // Otherwise the selection is missing.
    });

    // see http://stackoverflow.com/a/31370556
    textField.textProperty().addListener((obs_, old_, new_) -> {
      if (!new_.equals(selectedEntry)) {  // Avoid nonsense updating and concurrency hazard.
        entries.clear();
        entries.addAll(prefixAssociation.collectByPrefix(new_, -1));
      }
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
