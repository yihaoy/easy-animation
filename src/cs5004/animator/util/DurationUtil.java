package cs5004.animator.util;

import java.time.Duration;

/** Util for managing Durations. */
public class DurationUtil {
  /**
   * Convert a Duration into a display-able String.
   *
   * @param d Duration to transform.
   * @return String representation of Duration.
   */
  public static String toString(Duration d) {
    if (d.toHours() > 0) {
      return String.format(
          "%s:%s:%s",
          padTime(d.toHours()), padTime(d.toMinutes() % 60), padTime(d.toSeconds() % 60));
    }

    return String.format("%s:%s", padTime(d.toMinutes() % 60), padTime(d.toSeconds() % 60));
  }

  private static String padTime(long time) {
    if (time > 9) {
      return String.format("%d", time);
    }
    return String.format("0%d", time);
  }
}
