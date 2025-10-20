CS 4348 Project 1 

This project allows users to
-Set password for encryption 
-Encrypt/decrypt text using a Vigenere style cipher
-Maintains history of prev inputs and results 
-Log actions w/ timestamps to a file

Application consists of: 
-Driver
-Logger
-Encryption 

Commands:
- password : Set or update the encryption key (letters only).
- encrypt  : Encrypt a string using the current key.
- decrypt  : Decrypt a string using the current key.
- history  : View a list of previous inputs and results.
- quit     : Exit the program.

Setup and Usage:
1. Clone the repository:
   git clone https://github.com/diyamehta/cs4348-project1.git
   cd cs4348-project1

2. Compile the Java files:
   javac Driver.java Logger.java Encryption.java

3. Run the application:
   java Driver <logfile>
   Replace <logfile> with the path to desired log file. 
   Example: java Driver activity.log

4. Use the CLI:
   Follow the menu prompts to set passwords, encrypt/decrypt messages, view history, and quit.
   Logger automatically records all commands, results, and errors to the log file.


   Author: Diya Mehta 
