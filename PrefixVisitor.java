import java.util.*;

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

  public ArrayList<String> collectByPrefix(String prefix, int limit) {
    buffer = new StringBuilder(prefix);
    this.limit = limit;
    ArrayList<String> results = new ArrayList<>();
    TrieTree root = dictionary.getNext(prefix);
    if (root != null) {
      collectByPrefix(root, results);
    }
    return results;
  }

  private void collectByPrefix(TrieTree root, ArrayList<String> results) {
    if (root.isEnd()) {
      results.add(buffer.toString());
      limit--;
      if (limit == 0) {
        return;
      }
    }

    for (char c : chars.toCharArray()) {
      TrieTree next = root.getNext(c);
      if (next != null) {
        buffer.append(c);
        collectByPrefix(next, results);
        buffer.deleteCharAt(buffer.length() - 1);
        if (limit == 0) {
          break;
        }
      }
    }
  }

  public void showByPrefix(String prefix, int limit) {
    ArrayList<String> results = collectByPrefix(prefix, limit);
    for (String s : results) {
      System.out.println(s);
    }
  }

  public static void main(String[] args) throws Exception {
    TrieTree tree = TrieTree.createTree(args[0]);
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
