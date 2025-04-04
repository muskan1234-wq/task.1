// Source code is decompiled from a .class file using FernFlower decompiler.
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AdvancedFileOperations {
   private static final long MAX_FILE_SIZE = 104857600L;

   public AdvancedFileOperations() {
   }

   private static void checkFileSize(String var0) throws IOException {
      long var1 = Files.size(Paths.get(var0));
      if (var1 > 104857600L) {
         throw new IOException("File size (" + var1 / 1024L / 1024L + "MB) exceeds maximum allowed size (100MB)");
      }
   }

   public static List<String> listFilesWithExtension(String var0, String var1) throws IOException {
      Stream var2 = Files.walk(Paths.get(var0));

      List var3;
      try {
         var3 = (List)var2.filter((var0x) -> {
            return Files.isRegularFile(var0x, new LinkOption[0]);
         }).map(Path::toString).filter((var1x) -> {
            return var1x.endsWith("." + var1);
         }).collect(Collectors.toList());
      } catch (Throwable var6) {
         if (var2 != null) {
            try {
               var2.close();
            } catch (Throwable var5) {
               var6.addSuppressed(var5);
            }
         }

         throw var6;
      }

      if (var2 != null) {
         var2.close();
      }

      return var3;
   }

   public static void copyFile(String var0, String var1) throws IOException {
      checkFileSize(var0);
      Files.copy(Paths.get(var0), Paths.get(var1), StandardCopyOption.REPLACE_EXISTING);
   }

   public static void moveFile(String var0, String var1) throws IOException {
      checkFileSize(var0);
      Files.move(Paths.get(var0), Paths.get(var1), StandardCopyOption.REPLACE_EXISTING);
   }

   public static Map<String, Object> getFileInfo(String var0) throws IOException {
      Path var1 = Paths.get(var0);
      BasicFileAttributes var2 = Files.readAttributes(var1, BasicFileAttributes.class);
      HashMap var3 = new HashMap();
      var3.put("fileName", var1.getFileName().toString());
      var3.put("size", var2.size() + " bytes");
      var3.put("creationTime", var2.creationTime());
      var3.put("lastModifiedTime", var2.lastModifiedTime());
      var3.put("lastAccessTime", var2.lastAccessTime());
      var3.put("isDirectory", var2.isDirectory());
      var3.put("isRegularFile", var2.isRegularFile());
      var3.put("isSymbolicLink", var2.isSymbolicLink());
      return var3;
   }

   public static Map<String, List<Integer>> searchInFiles(String var0, String var1) throws IOException {
      HashMap var2 = new HashMap();
      Stream var3 = Files.walk(Paths.get(var0));

      try {
         List var4 = (List)var3.filter((var0x) -> {
            return Files.isRegularFile(var0x, new LinkOption[0]);
         }).collect(Collectors.toList());
         Iterator var5 = var4.iterator();

         while(var5.hasNext()) {
            Path var6 = (Path)var5.next();

            try {
               checkFileSize(var6.toString());
               ArrayList var7 = new ArrayList();
               BufferedReader var8 = new BufferedReader(new FileReader(var6.toFile()));

               String var9;
               try {
                  for(int var10 = 1; (var9 = var8.readLine()) != null; ++var10) {
                     if (var9.contains(var1)) {
                        var7.add(var10);
                     }
                  }
               } catch (Throwable var13) {
                  try {
                     var8.close();
                  } catch (Throwable var12) {
                     var13.addSuppressed(var12);
                  }

                  throw var13;
               }

               var8.close();
               if (!var7.isEmpty()) {
                  var2.put(var6.toString(), var7);
               }
            } catch (IOException var14) {
               System.out.println("Skipping file " + var6 + ": " + var14.getMessage());
            }
         }
      } catch (Throwable var15) {
         if (var3 != null) {
            try {
               var3.close();
            } catch (Throwable var11) {
               var15.addSuppressed(var11);
            }
         }

         throw var15;
      }

      if (var3 != null) {
         var3.close();
      }

      return var2;
   }

   public static void main(String[] var0) {
      try {
         String var1 = "test_files";
         FileHandlingUtility.createDirectory(var1);
         FileHandlingUtility.writeFile(var1 + "/file1.txt", "This is file 1\nWith multiple lines\nFor testing purposes");
         FileHandlingUtility.writeFile(var1 + "/file2.txt", "This is file 2\nWith the word testing here");
         FileHandlingUtility.writeFile(var1 + "/document.doc", "This is a document file");
         System.out.println("Text files in directory:");
         List var2 = listFilesWithExtension(var1, "txt");
         PrintStream var10001 = System.out;
         Objects.requireNonNull(var10001);
         var2.forEach(var10001::println);
         copyFile(var1 + "/file1.txt", var1 + "/file1_copy.txt");
         System.out.println("\nFile copied successfully.");
         System.out.println("\nFile information:");
         Map var3 = getFileInfo(var1 + "/file1.txt");
         var3.forEach((var0x, var1x) -> {
            System.out.println(var0x + ": " + var1x);
         });
         System.out.println("\nSearching for 'testing' in files:");
         Map var4 = searchInFiles(var1, "testing");
         var4.forEach((var0x, var1x) -> {
            System.out.println(var0x + " - Lines: " + var1x);
         });
      } catch (IOException var5) {
         System.out.println("Error: " + var5.getMessage());
      }

   }
}
