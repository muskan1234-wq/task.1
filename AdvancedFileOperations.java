import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.stream.Collectors;

public class AdvancedFileOperations {
    
    private static final long MAX_FILE_SIZE = 100 * 1024 * 1024; // 100MB
    
    private static void checkFileSize(String filePath) throws IOException {
        long size = Files.size(Paths.get(filePath));
        if (size > MAX_FILE_SIZE) {
            throw new IOException("File size (" + (size / 1024 / 1024) + "MB) exceeds maximum allowed size (100MB)");
        }
    }
    
    public static List<String> listFilesWithExtension(String directoryPath, String extension) throws IOException {
        try (var files = Files.walk(Paths.get(directoryPath))) {
            return files
                .filter(Files::isRegularFile)
                .map(Path::toString)
                .filter(f -> f.endsWith("." + extension))
                .collect(Collectors.toList());
        }
    }

    public static void copyFile(String sourcePath, String targetPath) throws IOException {
        checkFileSize(sourcePath);
        Files.copy(Paths.get(sourcePath), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);
    }

    public static void moveFile(String sourcePath, String targetPath) throws IOException {
        checkFileSize(sourcePath);
        Files.move(Paths.get(sourcePath), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);
    }

    public static Map<String, Object> getFileInfo(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);

        Map<String, Object> fileInfo = new HashMap<>();
        fileInfo.put("fileName", path.getFileName().toString());
        fileInfo.put("size", attr.size() + " bytes");
        fileInfo.put("creationTime", attr.creationTime());
        fileInfo.put("lastModifiedTime", attr.lastModifiedTime());
        fileInfo.put("lastAccessTime", attr.lastAccessTime());
        fileInfo.put("isDirectory", attr.isDirectory());
        fileInfo.put("isRegularFile", attr.isRegularFile());
        fileInfo.put("isSymbolicLink", attr.isSymbolicLink());

        return fileInfo;
    }

    public static Map<String, List<Integer>> searchInFiles(String directoryPath, String searchText) throws IOException {
        Map<String, List<Integer>> results = new HashMap<>();
        
        try (var files = Files.walk(Paths.get(directoryPath))) {
            List<Path> filePaths = files
                .filter(Files::isRegularFile)
                .collect(Collectors.toList());
            
            for (Path filePath : filePaths) {
                try {
                    checkFileSize(filePath.toString());
                    List<Integer> lineNumbers = new ArrayList<>();
                    
                    try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
                        String line;
                        int lineNum = 1;
                        
                        while ((line = reader.readLine()) != null) {
                            if (line.contains(searchText)) {
                                lineNumbers.add(lineNum);
                            }
                            lineNum++;
                        }
                    }
                    
                    if (!lineNumbers.isEmpty()) {
                        results.put(filePath.toString(), lineNumbers);
                    }
                } catch (IOException e) {
                    System.out.println("Skipping file " + filePath + ": " + e.getMessage());
                }
            }
        }
        
        return results;
    }

    public static void main(String[] args) {
        try {
            String testDir = "test_files";
            FileHandlingUtility.createDirectory(testDir);

            FileHandlingUtility.writeFile(testDir + "/file1.txt", "This is file 1\nWith multiple lines\nFor testing purposes");
            FileHandlingUtility.writeFile(testDir + "/file2.txt", "This is file 2\nWith the word testing here");
            FileHandlingUtility.writeFile(testDir + "/document.doc", "This is a document file");

            System.out.println("Text files in directory:");
            List<String> textFiles = listFilesWithExtension(testDir, "txt");
            textFiles.forEach(System.out::println);

            copyFile(testDir + "/file1.txt", testDir + "/file1_copy.txt");
            System.out.println("\nFile copied successfully.");

            System.out.println("\nFile information:");
            Map<String, Object> fileInfo = getFileInfo(testDir + "/file1.txt");
            fileInfo.forEach((key, value) -> System.out.println(key + ": " + value));

            System.out.println("\nSearching for 'testing' in files:");
            Map<String, List<Integer>> searchResults = searchInFiles(testDir, "testing");
            searchResults.forEach((file, lines) -> {
                System.out.println(file + " - Lines: " + lines);
            });

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
} 