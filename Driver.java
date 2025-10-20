import java.io.*;
import java.util.*;

public class Driver {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java Driver <logfile>");
            System.exit(1);
        }

        String logFile = args[0];
        Scanner scanner = new Scanner(System.in);
        List<String> history = new ArrayList<>();

        try {
            // Start Logger process
            Process logger = new ProcessBuilder("java", "Logger", logFile)
                    .redirectError(ProcessBuilder.Redirect.INHERIT)
                    .start();
            BufferedWriter logIn = new BufferedWriter(new OutputStreamWriter(logger.getOutputStream()));

            // Start Encryption process
            Process encryptor = new ProcessBuilder("java", "Encryption")
                    .redirectError(ProcessBuilder.Redirect.INHERIT)
                    .start();
            BufferedWriter encIn = new BufferedWriter(new OutputStreamWriter(encryptor.getOutputStream()));
            BufferedReader encOut = new BufferedReader(new InputStreamReader(encryptor.getInputStream()));

            log(logIn, "START Driver started.");

            boolean running = true;
            while (running) {
                printMenu();
                System.out.print("> ");
                String cmd = scanner.nextLine().trim().toLowerCase();

                switch (cmd) {
                    case "password":
                        log(logIn, "CMD password");
                        String pass = getUserString(scanner, history, "Enter new password (letters only): ");
                        if (!pass.matches("[a-zA-Z]+")) {
                            System.out.println("Invalid password (letters only).");
                            log(logIn, "ERROR Invalid password");
                            break;
                        }
                        encIn.write("PASS " + pass.toUpperCase() + "\n");
                        encIn.flush();
                        handleResponse(encOut, logIn);
                        break;

                    case "encrypt":
                        log(logIn, "CMD encrypt");
                        String plaintext = getUserString(scanner, history, "Enter text to encrypt: ");
                        if (!plaintext.matches("[a-zA-Z]+")) {
                            System.out.println("Invalid input (letters only).");
                            log(logIn, "ERROR Invalid text to encrypt");
                            break;
                        }
                        encIn.write("ENCRYPT " + plaintext.toUpperCase() + "\n");
                        encIn.flush();
                        String resultEnc = handleResponse(encOut, logIn);
                        if (resultEnc != null && resultEnc.startsWith("RESULT ")) {
                            String res = resultEnc.substring(7);
                            history.add(plaintext.toUpperCase());
                            history.add(res);
                        }
                        break;

                    case "decrypt":
                        log(logIn, "CMD decrypt");
                        String ciphertext = getUserString(scanner, history, "Enter text to decrypt: ");
                        if (!ciphertext.matches("[a-zA-Z]+")) {
                            System.out.println("Invalid input (letters only).");
                            log(logIn, "ERROR Invalid text to decrypt");
                            break;
                        }
                        encIn.write("DECRYPT " + ciphertext.toUpperCase() + "\n");
                        encIn.flush();
                        String resultDec = handleResponse(encOut, logIn);
                        if (resultDec != null && resultDec.startsWith("RESULT ")) {
                            String res = resultDec.substring(7);
                            history.add(ciphertext.toUpperCase());
                            history.add(res);
                        }
                        break;

                    case "history":
                        System.out.println("History:");
                        for (int i = 0; i < history.size(); i++)
                            System.out.println((i + 1) + ": " + history.get(i));
                        break;

                    case "quit":
                        log(logIn, "QUIT Driver quitting.");
                        encIn.write("QUIT\n");
                        encIn.flush();
                        logIn.write("QUIT\n");
                        logIn.flush();
                        running = false;
                        break;

                    default:
                        System.out.println("Unknown command.");
                }
            }

            scanner.close();
            encIn.close();
            encOut.close();
            logIn.close();
            encryptor.destroy();
            logger.destroy();

        } catch (IOException e) {
            System.err.println("Error running driver: " + e.getMessage());
        }
    }

    private static void printMenu() {
        System.out.println("\nCommands:");
        System.out.println("  password - set encryption key");
        System.out.println("  encrypt  - encrypt a string");
        System.out.println("  decrypt  - decrypt a string");
        System.out.println("  history  - view string history");
        System.out.println("  quit     - exit program");
    }

    private static String getUserString(Scanner sc, List<String> history, String prompt) {
        if (history.isEmpty()) {
            System.out.print(prompt);
            return sc.nextLine();
        }
        System.out.println("Use history or enter new text?");
        for (int i = 0; i < history.size(); i++)
            System.out.println((i + 1) + ": " + history.get(i));
        System.out.print("Enter number or N for new: ");
        String choice = sc.nextLine().trim();
        if (choice.equalsIgnoreCase("N")) {
            System.out.print(prompt);
            return sc.nextLine();
        }
        try {
            int idx = Integer.parseInt(choice);
            if (idx >= 1 && idx <= history.size()) return history.get(idx - 1);
        } catch (NumberFormatException ignored) {}
        System.out.println("Invalid choice. Entering new value:");
        System.out.print(prompt);
        return sc.nextLine();
    }

    private static void log(BufferedWriter writer, String msg) throws IOException {
        writer.write(msg + "\n");
        writer.flush();
    }

    private static String handleResponse(BufferedReader encOut, BufferedWriter logIn) throws IOException {
        String response = encOut.readLine();
        if (response != null) {
            System.out.println(response);
            log(logIn, "RESULT " + response);
        }
        return response;
    }
}
