import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

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

  public static Entry[] getEntriesFromDictionary(String file) {
    ArrayList<Entry> list = new ArrayList<>();
    try (Scanner dictionary = new Scanner(new File(file))) {
      dictionary.nextLine();  // Consume the header.
      long t = System.nanoTime();
      while (dictionary.hasNext()) {
        list.add(new Entry(dictionary.nextLine()));
      }
      System.out.println("Retrieve entries: " + (System.nanoTime() - t) / 1000_000.0 + "ms.");
    } catch (FileNotFoundException e) {
      System.out.println(file + " not found");
    }
    return list.toArray(new Entry[list.size()]);
  }
}
