import sun.text.normalizer.Trie;

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

  public TrieTree getNext(char ch) {
    return next == null ? null : next[ch];
  }

  public TrieTree getNext(String s) {
    TrieTree root = this;
    for (char c : s.toCharArray()) {
      root = root.getNext(c);
      if (root == null) {
        return null;
      }
    }
    return root;
  }

  private void setEnd() {
    isWordEnd = true;
  }

  public boolean isEnd() {
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

  public static TrieTree createTree(String file) {
    TrieTree tree = new TrieTree();

    try (Scanner dictionary = new Scanner(new File(file))) {
      dictionary.nextLine();              // Consume the header.
      while (dictionary.hasNext()) {
        // ID | Entry | [Pron] | Description
        String[] items = dictionary.nextLine().split("\t");
        tree.add(items[1]);
      }
    } catch (FileNotFoundException e) {
      System.out.println(file + " not found");
    }

    return tree;
  }

  public static void main(String[] args) throws Exception {
    TrieTree tree = createTree(args[0]);

    System.out.println("Input word to query, EOF to exit.");
    Scanner scanner = new Scanner(System.in);
    while (scanner.hasNext()) {
      System.out.println(tree.find(scanner.next()));
    }
  }
}