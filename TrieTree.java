import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TrieTree {
  private TrieTree[] next;

  /**
   * Indicate whether this node mark the end of a word.
   * i.e. this == some.getNext(word.lastChar).
   */
  private boolean isWordEnd = false;

  public static void main(String[] args) {
    TrieTree tree = new TrieTree();

    File file = new File(args[0]);
    Scanner dictionary;
    try {
      dictionary = new Scanner(file);
    } catch(FileNotFoundException e) {
      System.out.println("dictionary " + args[0] + " not found.");
      return;
    }

    dictionary.nextLine();  // Consume the header.
    while (dictionary.hasNext()) {
      dictionary.next();  // Consume the sequence number.
      tree.add(dictionary.next());  // Record the word.
      dictionary.nextLine();  // Ignore other information.
    }

    System.out.println("Input word to query, EOF to exit.");
    Scanner scanner = new Scanner(System.in);
    while (scanner.hasNext()) {
      System.out.println(tree.find(scanner.next()));
    }
  }

  public TrieTree getNext(char ch) {
    return next == null ? null : next[ch];
  }

  private void setEnd() {
    isWordEnd = true;
  }

  private boolean isEnd() {
    return isWordEnd;
  }

  private void addNext(char ch) {
    // Delay the next array construction of the leaf node.
    if (next == null) {
      next = new TrieTree[128];
    }

    if (next[ch] == null) {
      next[ch] = new TrieTree();
    }
  }

  /**
   * Determine whether string s exists in the tree.
   *
   * @param s The input string.
   * @return True if s exists, false otherwise.
   */
  public boolean find(String s) {
    TrieTree t = this;
    for (char c : s.toCharArray()) {
      t = t.getNext(c);
      if (t == null) {
        return false;
      }
    }
    return t.isEnd();
  }

  public void add(String s) {
    TrieTree t = this;
    for (char c : s.toCharArray()) {
      t.addNext(c);
      t = t.getNext(c);
    }
    t.setEnd();
  }
}
