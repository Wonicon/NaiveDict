import javafx.application.*;
import javafx.scene.control.TextField;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.*;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Dictionary extends Application {
  public static final ObservableList<String> entries = FXCollections.observableArrayList();

  Entry[] words;

  private TrieTree index;

  private PrefixVisitor prefixAssociation;

  private String selectedEntry = null;

  @Override
  public void start(Stage primaryStage) throws Exception {
    // Init resources
    words = Entry.getEntriesFromDictionary("dictionary.txt");
    index = TrieTree.createTree(words);
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

    // onAction is called when typed ENTER
    textField.setOnAction(e -> {
      TrieTree root = index.getNext(textField.getText());
      Entry ent = root == null ? null : root.getEntry();
      if (ent != null) {
        System.out.println(ent.getDescription());
      } else {
        LeastEditDistance led = new LeastEditDistance(textField.getText());
        LeastEditDistance.Pair[] queue = new LeastEditDistance.Pair[words.length];
        for (int i = queue.length - 1; i >= 0; i--) {
          queue[i] = led.new Pair(words[i].getWord());
        }
        Arrays.sort(queue);
        for (int i = 0; i < 10; i++) {
          System.out.println(queue[i].getCandidate() + ": " + queue[i].getEditDistance());
        }
      }
    });

    VBox stackPane = new VBox();
    stackPane.getChildren().add(textField);
    stackPane.getChildren().add(listView);
    stackPane.getPadding();
    Group root = new Group(stackPane);
    Scene scene = new Scene(root, 400, 300);
    primaryStage.setTitle("Dictionary");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
