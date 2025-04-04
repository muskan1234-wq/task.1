# File Handling Utility

A Java application for performing basic text file operations such as reading, writing, and modifying files.

## Overview

This project demonstrates file handling capabilities in Java, allowing users to:
- Create and read text files
- Write content to files
- Append content to existing files
- Modify file content by replacing text
- Perform advanced file operations like searching, copying, and moving files

## Components

The project consists of three main Java classes:

1. **FileHandlingUtility.java**: A utility class that provides methods for basic file operations
2. **FileHandlingDemo.java**: A demonstration class with a menu-driven interface to showcase the utility's capabilities
3. **AdvancedFileOperations.java**: A class demonstrating advanced file handling features

## Features

### FileHandlingUtility Class

- `readFile(String filePath)`: Reads content from a file
- `writeFile(String filePath, String content)`: Writes content to a file (overwrites existing content)
- `appendToFile(String filePath, String content)`: Appends content to an existing file
- `modifyFile(String filePath, String oldText, String newText)`: Replaces text in a file
- `fileExists(String filePath)`: Checks if a file exists
- `createDirectory(String dirPath)`: Creates a directory if it doesn't exist

### FileHandlingDemo Class

Provides an interactive menu allowing users to:
1. Create a sample file
2. Read file content
3. Write to a file
4. Append to a file
5. Modify file content

### AdvancedFileOperations Class

Demonstrates more advanced file handling capabilities:
- `listFilesWithExtension(String directoryPath, String extension)`: Lists all files with a specific extension
- `copyFile(String sourcePath, String targetPath)`: Copies a file to a new location
- `moveFile(String sourcePath, String targetPath)`: Moves a file to a new location
- `getFileInfo(String filePath)`: Gets detailed information about a file
- `searchInFiles(String directoryPath, String searchText)`: Searches for text in all files in a directory

## How to Run

### Basic Demo

1. Compile the Java files:
   ```
   javac FileHandlingUtility.java FileHandlingDemo.java
   ```

2. Run the demonstration:
   ```
   java FileHandlingDemo
   ```

3. Follow the menu prompts to test different file operations

### Advanced Demo

1. Compile all Java files:
   ```
   javac *.java
   ```

2. Run the advanced demonstration:
   ```
   java AdvancedFileOperations
   ```

Alternatively, you can use the provided batch file on Windows:
```
run.bat
```

## Usage Examples

### Creating a Sample File
- Select option 1 from the menu
- Enter a file name
- A sample file will be created with pre-defined content

### Reading a File
- Select option 2 from the menu
- Enter the name of the file to read
- The content will be displayed on the console

### Writing to a File
- Select option 3 from the menu
- Enter the file name
- Enter content (terminate with a blank line)
- The content will be written to the file

### Appending to a File
- Select option 4 from the menu
- Enter the file name
- Enter content to append (terminate with a blank line)
- The content will be appended to the file

### Modifying a File
- Select option 5 from the menu
- Enter the file name
- Enter the text to find
- Enter the replacement text
- The file will be modified by replacing occurrences of the specified text

### Advanced Operations
The AdvancedFileOperations class demonstrates:
- Listing files with specific extensions
- Copying files
- Getting detailed file information
- Searching for text across multiple files 