public class Timer {
  private static long s = System.nanoTime();

  public static void start() { s = System.nanoTime(); }

  public static void report(String header) {
    long c = System.nanoTime();
    System.out.println(header + ": " + (c - s) / 1000_000.0 + "ms.");
    s = c;
  }
}
