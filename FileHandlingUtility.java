import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FileHandlingUtility {
    
    private static final String LOG_FILE = "file_operations.log";
    private static final Lock logLock = new ReentrantLock();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    private static void logOperation(String operation) {
        logLock.lock();
        try {
            try (FileWriter fw = new FileWriter(LOG_FILE, true);
                 BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(dateFormat.format(new Date()) + " - " + operation);
                bw.newLine();
            } catch (IOException e) {
                // Silent fail for logging
            }
        } finally {
            logLock.unlock();
        }
    }
    
    public static String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        }
        logOperation("Read file: " + filePath);
        return content.toString();
    }
    
    public static void writeFile(String filePath, String content) throws IOException {
        Files.write(Paths.get(filePath), content.getBytes());
        logOperation("Write to file: " + filePath);
    }
    
    public static void appendToFile(String filePath, String content) throws IOException {
        Files.write(Paths.get(filePath), content.getBytes(), StandardOpenOption.APPEND);
        logOperation("Append to file: " + filePath);
    }
    
    public static void modifyFile(String filePath, String oldText, String newText) throws IOException {
        String content = readFile(filePath);
        String modifiedContent = content.replace(oldText, newText);
        writeFile(filePath, modifiedContent);
        logOperation("Modified file: " + filePath + " (replaced '" + oldText + "' with '" + newText + "')");
    }
    
    public static boolean fileExists(String filePath) {
        return Files.exists(Paths.get(filePath));
    }
    
    public static void createDirectory(String dirPath) throws IOException {
        Files.createDirectories(Paths.get(dirPath));
        logOperation("Created directory: " + dirPath);
    }
}