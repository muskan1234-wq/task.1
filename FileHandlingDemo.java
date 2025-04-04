import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Scanner;

public class FileHandlingDemo {
    
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("======= File Handling Utility Demo =======");
        
        boolean exit = false;
        while (!exit) {
            displayMenu();
            int choice = getUserChoice();
            
            try {
                switch (choice) {
                    case 1:
                        createSampleFile();
                        break;
                    case 2:
                        readFileDemo();
                        break;
                    case 3:
                        writeFileDemo();
                        break;
                    case 4:
                        appendFileDemo();
                        break;
                    case 5:
                        modifyFileDemo();
                        break;
                    case 6:
                        exit = true;
                        System.out.println("Thank you for using the File Handling Utility Demo!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NoSuchFileException e) {
                System.out.println("Error: File not found - " + e.getFile());
            } catch (SecurityException e) {
                System.out.println("Error: No permission to access file - " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
                e.printStackTrace();
            }
            
            if (!exit) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    private static void displayMenu() {
        System.out.println("\nPlease select an operation:");
        System.out.println("1. Create a sample file");
        System.out.println("2. Read a file");
        System.out.println("3. Write to a file");
        System.out.println("4. Append to a file");
        System.out.println("5. Modify a file");
        System.out.println("6. Exit");
        System.out.print("Enter your choice (1-6): ");
    }
    
    private static int getUserChoice() {
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a number: ");
            scanner.next();
        }
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline
        return choice;
    }
    
    private static void createSampleFile() throws IOException {
        System.out.print("Enter file name for the sample file: ");
        String fileName = scanner.nextLine();
        
        String content = "This is a sample file created by File Handling Utility.\n";
        content += "You can read, write, append, and modify this file.\n";
        content += "Feel free to experiment with different operations.\n";
        
        FileHandlingUtility.writeFile(fileName, content);
        System.out.println("Sample file '" + fileName + "' created successfully!");
    }
    
    private static void readFileDemo() throws IOException {
        System.out.print("Enter the name of the file to read: ");
        String fileName = scanner.nextLine();
        
        if (!FileHandlingUtility.fileExists(fileName)) {
            System.out.println("File '" + fileName + "' does not exist.");
            return;
        }
        
        String content = FileHandlingUtility.readFile(fileName);
        System.out.println("\n--- File Content ---");
        System.out.println(content);
        System.out.println("-------------------");
    }
    
    private static void writeFileDemo() throws IOException {
        System.out.print("Enter the name of the file to write to: ");
        String fileName = scanner.nextLine();
        
        if (FileHandlingUtility.fileExists(fileName)) {
            System.out.print("File already exists. Overwrite? (y/n): ");
            String confirm = scanner.nextLine().toLowerCase();
            if (!confirm.equals("y")) {
                System.out.println("Operation cancelled.");
                return;
            }
        }
        
        System.out.println("Enter the content to write (Enter a blank line to finish):");
        StringBuilder content = new StringBuilder();
        String line;
        while (!(line = scanner.nextLine()).isEmpty()) {
            content.append(line).append("\n");
        }
        
        FileHandlingUtility.writeFile(fileName, content.toString());
        System.out.println("Content written to file '" + fileName + "' successfully!");
    }
    
    private static void appendFileDemo() throws IOException {
        System.out.print("Enter the name of the file to append to: ");
        String fileName = scanner.nextLine();
        
        if (!FileHandlingUtility.fileExists(fileName)) {
            System.out.println("File '" + fileName + "' does not exist.");
            return;
        }
        
        System.out.println("Enter the content to append (Enter a blank line to finish):");
        StringBuilder content = new StringBuilder();
        String line;
        while (!(line = scanner.nextLine()).isEmpty()) {
            content.append(line).append("\n");
        }
        
        FileHandlingUtility.appendToFile(fileName, content.toString());
        System.out.println("Content appended to file '" + fileName + "' successfully!");
    }
    
    private static void modifyFileDemo() throws IOException {
        System.out.print("Enter the name of the file to modify: ");
        String fileName = scanner.nextLine();
        
        if (!FileHandlingUtility.fileExists(fileName)) {
            System.out.println("File '" + fileName + "' does not exist.");
            return;
        }
        
        System.out.print("Enter the text to find: ");
        String oldText = scanner.nextLine();
        
        System.out.print("Enter the replacement text: ");
        String newText = scanner.nextLine();
        
        FileHandlingUtility.modifyFile(fileName, oldText, newText);
        System.out.println("File '" + fileName + "' modified successfully!");
    }
} 
