import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.util.*;

public class Index {
    public static void main(String[] args){

        String commandSplit[] = args;

        String action = commandSplit[0].substring(1);
        String fileName = commandSplit[1];
        String iFileName = commandSplit[2];
        int keyLength = Integer.parseInt(commandSplit[3]);

        Map<String, String> index = new HashMap<>();

        if (action.equals("c")){
            System.out.println("Create index file. ");
            try {
                //Map<String, Long> index = new HashMap<>();
                RandomAccessFile txtFile = new RandomAccessFile(fileName, "rw");
                String s;
                do{
                    long start = txtFile.getFilePointer();
                    String IBinary = Long.toBinaryString(start);
                    s = txtFile.readLine();
                    if(s!=null){
                        String key = s.substring(0,keyLength);
                        index.put(key, IBinary);
                    }
                }while(s!=null);

                File indexFile = new File(iFileName);
                BufferedWriter bf = null;
                bf = new BufferedWriter(new FileWriter(indexFile));

                Map<String, String> treeMap = new TreeMap<String, String>(index);
                for(String key: treeMap.keySet()){
                    //System.out.println( key+" "+ treeMap.get(key));
                    bf.write(key+" "+ treeMap.get(key));
                    bf.newLine();
                }
                bf.flush();
                txtFile.close();
            }
            catch (Exception ex)
            {
                System.out.println("Error: " + ex.getMessage());
            }
        }
        else if (action.equals("l")){
            System.out.println("List data in order base on index file:");
            try{
                RandomAccessFile txtFile = new RandomAccessFile(fileName, "rw");
                RandomAccessFile indexFile = new RandomAccessFile(iFileName, "rw");
                String s;
                do{

                    s = indexFile.readLine();
                    if(s!=null){
                        int keyPosition = Integer.parseInt(s.substring(keyLength+1),2);
                        txtFile.seek(keyPosition);
                        System.out.println(txtFile.readLine());
                    }
                }while(s!=null);
            }
            catch (Exception ex)
            {
                System.out.println("Error: " + ex.getMessage());
            }
        }
        else {
            System.out.println("Invalid command. ");
        }
    }
}
