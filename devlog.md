# 2025-10-10 21:45
Today, started thinking about the project. Three main components of the project are the driver, logger, and encryption. Right now, thinking about implementing logger so that it can reliably record messages w/ timestamps, implementing encryption w basic functionality and implementing driver to launch both of them. Then I need to make sure that all the edge cases are handled and that everything works together properly. 

## 2025-10-11 21:00

I started working on Logger.java, The goals today were:
- Read input from standard input.  
- Write timestamped logs to a file.  
- Handle empty lines and the `QUIT` command.  
- Split input into action and optional message.
I wrote a very simple version and tested it. Lines like `START Testing` appeared correctly in the log file with timestamps. This gives me confidence that Logger will work as expected. 

---

## 2025-10-12 20:45

I began working on Encryption.java, Today I implemented:
- Password setting (letters only)  
- Encrypting and decrypting text using the Vigenère-style cipher  
- Input validation to reject non-letter characters 
- Basic error reporting to standard output

Tested a few strings manually. 

---

## 2025-10-13 21:10

Started designing the Driver CLI, The commands planned as stated in the instructions of the project: 

- `password` – set encryption key.  
- `encrypt` – encrypt a string.  
- `decrypt` – decrypt a string.  
- `history` – show previous inputs/results.  
- `quit` – exit program.

I sketched out the plan for communicating with Logger and Encryption processes via `ProcessBuilder` and streams. My goal for the next day is to implement the menu system.

---

## 2025-10-14 20:50

Implemented the **Driver menu system**. Key points today:

- Prompts the user for commands and validates input.  
- Sends commands to Encryption and Logger processes.  
- Maintains a history list
- Handles flushing streams to ensure proper inter-process communication.

Tested and it works properly. 

---

## 2025-10-15 21:05

Worked on **history and error handling** in Driver:
- Users can reuse previous entries from history  
- Invalid commands or inputs are handled gracefully.  
- Errors are logged by Logger and displayed to the user.  
- Encryption responds correctly if password is not set.

Tested multiple scenarios. 

---

## 2025-10-16 20:40

Focused on **cleanup**:

-Added some comments, had failed to do so previously. 
- Tested everything end-to-end: set password, encrypt/decrypt, view history, quit.

Everything works as expected. Ready for final testing.

---

## 2025-10-17 21:20

Conducted **final testing**:

- Verified that all components work properly. 

Everything is stable.

---

## 2025-10-18 20:55

Final touches:
- Updated `README.md` with usage instructions and examples.  
- Minor formatting adjustments in CLI outputs.  
- Confirmed that Logger, Driver, and Encryption integration is smooth.

All components are ready for the commit. 

---

## 2025-10-19 21:15

**First Commit**

- Made first commit of complete project code to local Git repository.  
- Successfully pushed all files to GitHub repository `cs4348-project1`.  
