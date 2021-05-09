import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FReader {

    public static final List<File> files = new ArrayList<>();
    public static StringBuilder result = new StringBuilder();

    static void RecursivePrint(File[] arr, int level)
    {
        // for-each loop for main directory files
        for (File f : arr)
        {

            if(f.isFile() && (!f.getName().equals("FileReader-1.0-jar-with-dependencies.jar"))) {
                files.add(f);
            }

            else if(f.isDirectory())
            {
                // recursion for sub-directories
                RecursivePrint(f.listFiles(), level + 1);
            }
        }
    }

    public static void readContent(File file) throws IOException {
        try(BufferedReader br  = new BufferedReader(new FileReader(file))){
            String strLine;
            // Read lines from the file, returns null when end of stream is reached
            while((strLine = br.readLine()) != null){
                result.append(strLine);
            }
        }
    }

    public static class NameSorter implements Comparator<File>
    {
        @Override
        public int compare(File o1, File o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }


    // Driver Method
    public static void main(String[] args) throws IOException {
        String mainDirPath =
                args.length == 0 ? System.getProperty("user.dir") : args[0];

        // File object
        File mainDir = new File(mainDirPath);

        if(mainDir.exists() && mainDir.isDirectory())
        {
            // array for files and sub-directories
            File[] arr = mainDir.listFiles();
            // Calling recursive method
            RecursivePrint(arr, 0);
            //Sort files
            files.sort(new NameSorter());
            for(File f : files) {
                System.out.println(f.getName());
                readContent(f);
            }
            FileWriter writer = new FileWriter("out.txt");
            writer.write(result.toString());
            writer.close();
        }
    }
}