import javafx.application.*;
import javafx.scene.control.TextField;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.*;

import java.util.Arrays;

public class Dictionary extends Application {
  private static ObservableList<String> entries = FXCollections.observableArrayList();

  private Entry[] words;

  private TrieTree index;

  private PrefixVisitor prefixAssociation;

  private String selectedEntry = null;

  TextArea textArea;

  private void updateInfo(String word) {
    Entry entry = index.getNext(word).getEntry();
    textArea.setText(
      entry.getWord() + "\n"
      + entry.getPronunciation() + "\n"
      + entry.getDescription()
    );
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    // Init resources

    words = Entry.getEntriesFromDictionary("dictionary.txt");
    index = TrieTree.createTree(words);
    prefixAssociation = new PrefixVisitor(index);

    // Init UI elements

    TextField textField = new TextField("Type Me");
    ListView<String> listView = new ListView<>();
    textArea = new TextArea("Hello");

    // Bind list items to entry list.
    listView.setItems(entries);

    // When we select an item in the side bar, update the text in the text field.
    /// @todo update detail info panel when selected.
    listView.getSelectionModel().selectedItemProperty().addListener((o, old_, new_) -> {
      if (new_ != null) {
        selectedEntry = new_;
        textField.setText(new_);
        updateInfo(new_);
      }  // Otherwise the selection is missing.
    });

    // see http://stackoverflow.com/a/31370556
    textField.textProperty().addListener((obs_, old_, new_) -> {
      if (!new_.equals(selectedEntry)) {  // Avoid nonsense updating and concurrency hazard.
        entries.clear();
        entries.addAll(prefixAssociation.collectByPrefix(new_));
      }
    });

    // onAction is called when typed ENTER
    textField.setOnAction(e -> {
      TrieTree root = index.getNext(textField.getText());
      Entry ent = root == null ? null : root.getEntry();
      if (ent != null) {
        System.out.println(ent.getDescription());
        updateInfo(ent.getWord());
      } else {
        Timer t = new Timer();

        LeastEditDistance led = new LeastEditDistance(textField.getText());
        LeastEditDistance.Pair[] queue = new LeastEditDistance.Pair[words.length];
        for (int i = queue.length - 1; i >= 0; i--) {
          queue[i] = led.new Pair(words[i].getWord());
        }
        Arrays.sort(queue);
        for (int i = 0; i < 10; i++) {
          System.out.println(queue[i].getCandidate() + ": " + queue[i].getEditDistance());
        }

        t.report("L.E.D.");
      }
    });

    // Init layouts

    VBox vbox = new VBox();
    vbox.getChildren().addAll(textField, listView);
    vbox.getPadding();

    BorderPane borderPane = new BorderPane();
    borderPane.setLeft(vbox);
    borderPane.setRight(textArea);

    Group root = new Group(borderPane);
    Scene scene = new Scene(root, 400, 400);
    primaryStage.setTitle("Dictionary");
    primaryStage.setScene(scene);
    primaryStage.show();
    primaryStage.setResizable(false);
  }

  public static void main(String[] args) {
    launch(args);
  }
}
