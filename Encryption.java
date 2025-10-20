import java.io.*;

public class Encryption {
    private static String key = null;

    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out, true);

        try {
            String line;
            while ((line = in.readLine()) != null) {
                line = line.trim();
                if (line.equalsIgnoreCase("QUIT")) break;
                if (line.isEmpty()) continue;

                int space = line.indexOf(' ');
                String command = (space == -1) ? line.toUpperCase() : line.substring(0, space).toUpperCase();
                String argument = (space == -1) ? "" : line.substring(space + 1).trim();

                switch (command) {
                    case "PASS":
                        if (argument.isEmpty() || !argument.chars().allMatch(Character::isLetter)) {
                            out.println("ERROR Invalid password");
                        } else {
                            key = argument.toUpperCase();
                            out.println("RESULT");
                        }
                        break;
                    case "ENCRYPT":
                        if (key == null) {
                            out.println("ERROR Password not set");
                        } else if (!argument.chars().allMatch(Character::isLetter)) {
                            out.println("ERROR Invalid text (letters only)");
                        } else {
                            out.println("RESULT " + encrypt(argument.toUpperCase(), key));
                        }
                        break;
                    case "DECRYPT":
                        if (key == null) {
                            out.println("ERROR Password not set");
                        } else if (!argument.chars().allMatch(Character::isLetter)) {
                            out.println("ERROR Invalid text (letters only)");
                        } else {
                            out.println("RESULT " + decrypt(argument.toUpperCase(), key));
                        }
                        break;
                    default:
                        out.println("ERROR Unknown command");
                        break;
                }
            }
        } catch (IOException e) {
            out.println("ERROR " + e.getMessage());
        }
    }

    private static String encrypt(String text, String key) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int shift = key.charAt(i % key.length()) - 'A';
            sb.append((char) ('A' + (c - 'A' + shift) % 26));
        }
        return sb.toString();
    }

    private static String decrypt(String text, String key) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int shift = key.charAt(i % key.length()) - 'A';
            sb.append((char) ('A' + (c - 'A' - shift + 26) % 26));
        }
        return sb.toString();
    }
}
