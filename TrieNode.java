/**
 * TrieNode
 */
public class TrieNode {
  private TrieNode[] next;

  /**
   * Indicate whether this node mark the end of a word.
   * i.e. this == some.getNext(word.lastChar).
   */
  boolean isWordEnd = false;

  public TrieNode getNext(char ch) {
    return next == null ? null : next[ch];
  }

  public void setEnd() {
    isWordEnd = true;
  }

  public boolean isEnd() {
    return isWordEnd;
  }

  public void addNext(char ch) {
    // Delay the next array construction of the leaf node.
    if (next == null) {
      next = new TrieNode[128];
    }

    if (next[ch] == null) {
      next[ch] = new TrieNode();
    }
  }
}
