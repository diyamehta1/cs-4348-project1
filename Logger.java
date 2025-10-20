import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java Logger <logfile>");
            System.exit(1);
        }

        String logFile = args[0];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        try (
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().equalsIgnoreCase("QUIT")) {
                    String timestamp = LocalDateTime.now().format(formatter);
                    writer.write(timestamp + " [QUIT] Logger terminated.");
                    writer.newLine();
                    writer.flush();
                    break;
                }

                if (line.trim().isEmpty()) continue;

                String action;
                String message;
                int spaceIndex = line.indexOf(' ');
                if (spaceIndex == -1) {
                    action = line.trim();
                    message = "";
                } else {
                    action = line.substring(0, spaceIndex).trim();
                    message = line.substring(spaceIndex + 1);
                }

                String timestamp = LocalDateTime.now().format(formatter);
                writer.write(timestamp + " [" + action + "] " + message);
                writer.newLine();
                writer.flush();
            }
        } catch (IOException e) {
            System.err.println("Logger error: " + e.getMessage());
        }
    }
}
