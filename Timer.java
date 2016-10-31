public class Timer {
  private long s = System.nanoTime();

  public void report(String header) {
    long c = System.nanoTime();
    System.out.println(header + ": " + (c - s) / 1000_000.0 + "ms.");
    s = c;
  }
}