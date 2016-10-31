import java.util.*;

public class PrefixVisitor {
  private TrieTree dictionary;

  private ArrayList<String> results;

  PrefixVisitor(TrieTree dictionary) {
    this.dictionary = dictionary;
  }

  public ArrayList<String> collectByPrefix(String prefix) {
    this.results = new ArrayList<>();
    TrieTree root = dictionary.getNext(prefix);
    if (root != null) {
      collectByPrefix(root);
    }
    return results;
  }

  private void collectByPrefix(TrieTree root) {
    if (root.isEnd()) {
      results.add(root.getEntry().getWord());
    }

    final String chars = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ ";
    for (char c : chars.toCharArray()) {
      TrieTree next = root.getNext(c);
      if (next != null) {
        collectByPrefix(next);
      }
    }
  }

  public static void main(String[] args) throws Exception {
    TrieTree tree = TrieTree.createTree(args[0]);
    PrefixVisitor prefixVisitor = new PrefixVisitor(tree);

    System.out.println("Input prefix, EOF to exit.");
    Scanner scanner = new Scanner(System.in);
    while (scanner.hasNext()) {
      ArrayList<String> strings = prefixVisitor.collectByPrefix(scanner.next());
      strings.forEach(System.out::println);
    }
  }
}
