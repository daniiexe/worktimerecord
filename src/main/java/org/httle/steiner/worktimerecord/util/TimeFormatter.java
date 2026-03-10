package org.httle.steiner.worktimerecord.util;

/**
 * TimeFormatter to format time

 * Behavior summary:
 * - create a one and only instance of the formatter object
 * - getting the formatter instance
 * - method for formatting a double into a String
 * - method for formatting a String into a double
 */

public class TimeFormatter {
    public static TimeFormatter instance;

    private TimeFormatter() {}

    public static TimeFormatter getInstance() {
        if (instance == null) {
            instance = new TimeFormatter();
        }
        return instance;
    }

    // Formatting a double into a String (0.0 to hh:mm)
    public String formatDoubleToTime(double time) {
        int hours = (int) time;
        int minutes = (int) ((time - hours) * 60);

        // %02d: % -> start placeholder, 0 -> fill with zeros, 2 -> minimum of 2 decimal, d -> decimal integer
        return hours + ":" + String.format("%02d", minutes);
    }

    // Formatting a String into a double (hh:mm to 0.0)
    public double formatTimeToDouble(String time) {
        // Checks if the String is in the right format (hh:mm)
        if (!time.contains(":")) return 0;

        String[] parts = time.split(":");

        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        return hours + (minutes / 60.0);
    }

    // Formating a String minute into a double (mm to 0.0)
    public double formatStringToDouble(String time) {return Double.parseDouble(time) / 60;}

    // Formatting a double into a String minute (0.0 to mm)
    public String formatDoubleToString(double time) {return String.valueOf((int) (time * 60));}
}
