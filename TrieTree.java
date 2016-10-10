import java.io.*;
import java.util.Scanner;

public class TrieTree {
  private TrieNode root = new TrieNode();

  /**
   * Determine whether string s exists in the tree.
   * @param s The input string.
   * @return True if s exists, false otherwise.
   */
  public boolean find(String s) {
    TrieNode t = root;
    for (char c : s.toCharArray()) {
      t = t.getNext(c);
      if (t == null) {
        return false;
      }
    }
    return t.isEnd();
  }

  public void add(String s) {
    TrieNode t = root;
    for (char c : s.toCharArray()) {
      t.addNext(c);
      t = t.getNext(c);
    }
    t.setEnd();
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
      System.out.println(dictionary.next());  // Consume the sequence number.
      tree.add(dictionary.next());  // Record the word.
      dictionary.nextLine();  // Ignore other information.
    }

    System.out.println("Input word to query, EOF to exit.");
    Scanner scanner = new Scanner(System.in);
    while (scanner.hasNext()) {
      System.out.println(tree.find(scanner.next()));
    }
  }
}
