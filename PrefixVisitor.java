import java.io.*;
import java.util.Scanner;

public class PrefixVisitor {
  /**
   * A mutable buffer recording all chars met in traversal.
   */
  private StringBuilder buffer = null;

  private TrieTree dictionary;

  private int limit = 0;

  private final String chars = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ";

  PrefixVisitor(TrieTree dictionary) {
    this.dictionary = dictionary;
  }

  public void showByPrefix(String prefix, int limit) {
    buffer = new StringBuilder(prefix);
    this.limit = limit;
    TrieTree root = dictionary.getNext(prefix);
    showByPrefix(root);
  }

  private void showByPrefix(TrieTree root) {
    if (root.isEnd()) {
      System.out.println(buffer.toString());
      limit--;
      if (limit == 0) {
        return;
      }
    }

    for (char c : chars.toCharArray()) {
      TrieTree next = root.getNext(c);
      if (next != null) {
        buffer.append(c);
        showByPrefix(next);
        buffer.deleteCharAt(buffer.length() - 1);
        if (limit == 0) {
          break;
        }
      }
    }
  }

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

    PrefixVisitor prefixVisitor = new PrefixVisitor(tree);
    System.out.println("Input prefix and limit, EOF to exit.");
    Scanner scanner = new Scanner(System.in);
    while (scanner.hasNext()) {
      String prefix = scanner.next();
      int limit = scanner.nextInt();
      prefixVisitor.showByPrefix(prefix, limit);
    }
  }
}
