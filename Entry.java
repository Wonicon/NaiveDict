/**
 * The Entry class describes one single entry in the dictionary,
 * which contains the full word string and its description, as well as
 * its pronunciation if given.
 */
public class Entry {
  private String word;

  private String pronunciation;

  private String description;

  Entry(String entry) {
    // ID | Entry | [Pron] | Description
    String[] items = entry.split("\t");
    word = items[1].trim();
    if (word.indexOf(' ') == -1) {  // It is not a phrase
      pronunciation = items[2];
      description = items[3];
    } else {
      pronunciation = null;
      description = items[items.length -1];
    }
  }

  public String getWord() {
    return word;
  }

  public String getPronunciation() {
    return pronunciation;
  }

  public String getDescription() {
    return description;
  }
}
