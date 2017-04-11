package filemanager;

import java.io.File;
import java.util.Scanner;
import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.nio.file.attribute.BasicFileAttributes;

public class FileManager {

    static Scanner sc = new Scanner(System.in);

    static String path;
    static String command = null;

    static boolean running = true;

    public static void DELETE() {
        System.out.println("Introduceti path-ul catre fisierul de sters: ");
        String name = sc.next();
        Path path = Paths.get(name);
      
      
            try {
                Files.delete(path);
            } catch (Exception e) {
                System.out.println(e);
            }
        
        
    }

    public static void COPY() {

        try {
            String path = "";
            String destination = "";
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter file path: ");
            path = bufferRead.readLine();

            System.out.println("Enter destination folder: ");
            destination = bufferRead.readLine();
           

            Path source = Paths.get(path);
            Path dest = Paths.get(destination);
            File f = new File(path);
            File g = new File(destination);
            if(f.isFile()){
            Files.copy(source, dest,REPLACE_EXISTING);}
            else if (f.isDirectory())
                copyFolder(f,g);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
  private static void copyFolder(File sourceFolder, File destinationFolder) throws IOException
    {
       
        if (sourceFolder.isDirectory()) 
        {
            if (!destinationFolder.exists()) 
            {
                destinationFolder.mkdir();
                System.out.println("Directory created :: " + destinationFolder);
            }
             
            String files[] = sourceFolder.list();
             
            for (String file : files) 
            {
                File srcFile = new File(sourceFolder, file);
                File destFile = new File(destinationFolder, file);
                 
                copyFolder(srcFile, destFile);
            }
        }
        else
        {
            Files.copy(sourceFolder.toPath(), destinationFolder.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copied :: " + destinationFolder);
        }
    }
    public static void RENAME() {
        String path = "";
        String new_name = "";
        try (BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));) {
            System.out.println("Enter old file path: ");
            path = bufferRead.readLine();

            System.out.println("Enter new name: ");

            new_name = bufferRead.readLine();
            File oldFile = new File(path);
            File newFile = new File(new_name);
            boolean is = oldFile.renameTo(newFile);

            if (is) {
                System.out.println("File has been renamed");
            } else {
                System.out.println("Error renaming the file");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void CREATE() {
        System.out.println("Introduceti path-ul:");
        String path = sc.next();
        System.out.println("Introduceti nume folder+sub-foldere:");
        String nume = sc.next();
        String final_path = path + "\\" + nume;

        new File(final_path).mkdir();
    }

    public static void MOVE() {
        String path = "";
        String destination = "";

        try (BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));) {

            System.out.println("Enter file path: ");
            path = bufferRead.readLine();

            System.out.println("Enter destination folder: ");
            destination = bufferRead.readLine();

        } catch (Exception e) {
            System.out.println(e);

        }

        File afile = new File(path);
        File bfile = new File(destination + "\\" + afile.getName());

        try (FileInputStream inStream = new FileInputStream(afile);
                FileOutputStream outStream = new FileOutputStream(bfile);) {

            byte[] buffer = new byte[1024];

            int length;

            while ((length = inStream.read(buffer)) > 0) {

                outStream.write(buffer, 0, length);
            }

            System.out.println("File is moved successfuly!");
//inStream.close();
//outStream.close();
            afile.delete();

        } catch (Exception exc) {
            System.out.println(exc);
        }
    }

    //////////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) {

        while (running) {

            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Enter file path: ");
            try {
                path = bufferRead.readLine();
            } catch (Exception ex) {
                System.out.println();
            }

            File file = new File(path);
            Path source = Paths.get(path);
            if (file.exists() && file.isDirectory()) {
                File[] elements = file.listFiles();

                for (int i = 0; i < elements.length; i++) {
                    try {
                        BasicFileAttributes attr = Files.readAttributes(source, BasicFileAttributes.class);
                        System.out.println("____________________________________________________________________________________");

                        System.out.println("\n" + elements[i].getName() + "\t|" + elements[i].getPath() + "\t|" + elements[i].length()
                                + "\t|" + attr.creationTime() + "\t|" + elements[i].lastModified() + "\t\n");

                    } catch (Exception ex) {
                    }

                }
            } else {
                System.out.println("Path does not exist");
            }

            System.out.println("Use one of the following commands: MOVE , DELETE , RENAME , COPY , CREATE, EXIT");

            try {
                command = sc.nextLine();
                System.out.println("command:" + command);
            } catch (Exception e) {
                System.out.println(e);

            }

            switch (command) {
                case "MOVE": {
                    System.out.println("Comanda acceptata!");
                    MOVE();
                    break;
                }
                 case "COPY": {
                    System.out.println("Comanda acceptata!");
                    COPY();
                    break;
                }

                case "DELETE": {
                    System.out.println("Comanda acceptata!");
                    DELETE();
                    break;
                }

                case "RENAME": {
                    System.out.println("Comanda acceptata!");
                    RENAME();
                    break;
                }
                case "CREATE": {
                    System.out.println("Comanda acceptata!");
                    CREATE();
                    break;
                }
                case "EXIT": {
                    System.out.println("Comanda acceptata!Inchidere sistem!");
                    System.exit(0);
                    break;
                }
                default:
                    System.out.println("Nu ati introdus o comanda corecta");
            }

            path = null;
        }

    }

}
