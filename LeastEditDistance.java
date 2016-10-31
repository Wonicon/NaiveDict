import java.util.Scanner;

/**
 * This class calculates the least edit distance between the two strings,
 * and will store the strings' references and the least edit distance for future use.
 */
public class LeastEditDistance {
  public static int leastEditDistance(String first, String second) {
    int[][] m = new int[second.length() + 1][first.length() + 1];
    for (int i = 0; i <= first.length(); i++)
      m[0][i] = i;
    for (int i = 0; i <= second.length(); i++)
      m[i][0] = i;

    for (int i = 1; i <= second.length(); i++) {
      for (int j = 1; j <= first.length(); j++) {
        m[i][j] = Math.min(m[i - 1][j], Math.min(m[i][j - 1], m[i - 1][j - 1]))
                  + (first.charAt(j - 1) == second.charAt(i - 1) ? 0 : 1);
      }
    }

    return m[second.length()][first.length()];
  }

  private String original = null;

  public LeastEditDistance(String original_) {
    original = original_;
  }

  /**
   * This is a class to facilitate the sort of the second string based on edit distance.
   */
  public class Pair implements Comparable {
    private String candidate = null;
    private int editDistance = Integer.MAX_VALUE;

    public Pair(String candidate_) {
      candidate = candidate_;
      editDistance = leastEditDistance(original, candidate);
    }

    @Override
    public int compareTo(Object o) {
      Pair led = (Pair) o;
      if (this.editDistance < led.editDistance) return -1;
      else if (this.editDistance == led.editDistance) return 0;
      else return 1;
    }
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Input the original string:");
    String original = scanner.next();

    System.out.println("Input the number of candidate strings:");
    final int n = scanner.nextInt();
    String[] candidates = new String[n];

    System.out.println("Input the " + n + " candidate strings:");
    for (int i = 0; i < n; i++) {
      candidates[i] = scanner.next();
    }

    //==--

    LeastEditDistance led = new LeastEditDistance(original);
    Pair[] pairs = new Pair[n];
    for (int i = 0; i < n; i++) {
      pairs[i] = led.new Pair(candidates[i]);
    }

    java.util.Arrays.sort(pairs);

    for (int i = 0; i < n; i++) {
      System.out.println(pairs[i].candidate + " " + pairs[i].editDistance);
    }
  }
}
