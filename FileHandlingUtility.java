// Source code is decompiled from a .class file using FernFlower decompiler.
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FileHandlingUtility {
   private static final String LOG_FILE = "file_operations.log";
   private static final Lock logLock = new ReentrantLock();
   private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

   public FileHandlingUtility() {
   }

   private static void logOperation(String var0) {
      logLock.lock();

      try {
         FileWriter var1 = new FileWriter("file_operations.log", true);

         try {
            BufferedWriter var2 = new BufferedWriter(var1);

            try {
               String var10001 = dateFormat.format(new Date());
               var2.write(var10001 + " - " + var0);
               var2.newLine();
            } catch (Throwable var14) {
               try {
                  var2.close();
               } catch (Throwable var13) {
                  var14.addSuppressed(var13);
               }

               throw var14;
            }

            var2.close();
         } catch (Throwable var15) {
            try {
               var1.close();
            } catch (Throwable var12) {
               var15.addSuppressed(var12);
            }

            throw var15;
         }

         var1.close();
      } catch (IOException var16) {
      } finally {
         logLock.unlock();
      }

   }

   public static String readFile(String var0) throws IOException {
      StringBuilder var1 = new StringBuilder();
      BufferedReader var2 = new BufferedReader(new FileReader(var0));

      String var3;
      try {
         while((var3 = var2.readLine()) != null) {
            var1.append(var3).append(System.lineSeparator());
         }
      } catch (Throwable var6) {
         try {
            var2.close();
         } catch (Throwable var5) {
            var6.addSuppressed(var5);
         }

         throw var6;
      }

      var2.close();
      logOperation("Read file: " + var0);
      return var1.toString();
   }

   public static void writeFile(String var0, String var1) throws IOException {
      Files.write(Paths.get(var0), var1.getBytes(), new OpenOption[0]);
      logOperation("Write to file: " + var0);
   }

   public static void appendToFile(String var0, String var1) throws IOException {
      Files.write(Paths.get(var0), var1.getBytes(), new OpenOption[]{StandardOpenOption.APPEND});
      logOperation("Append to file: " + var0);
   }

   public static void modifyFile(String var0, String var1, String var2) throws IOException {
      String var3 = readFile(var0);
      String var4 = var3.replace(var1, var2);
      writeFile(var0, var4);
      logOperation("Modified file: " + var0 + " (replaced '" + var1 + "' with '" + var2 + "')");
   }

   public static boolean fileExists(String var0) {
      return Files.exists(Paths.get(var0), new LinkOption[0]);
   }

   public static void createDirectory(String var0) throws IOException {
      Files.createDirectories(Paths.get(var0));
      logOperation("Created directory: " + var0);
   }
}
