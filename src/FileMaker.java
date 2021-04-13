import java.io.*;

public class FileMaker {

    public void makeFileOfGroups(String listOfGroups){
        File newFile = new File("C://Warehouse//List of the groups.txt");
        try (BufferedWriter writter = new BufferedWriter(new FileWriter(newFile))) {
            writter.write(listOfGroups);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void makeFileOfGroupItems(String groupName, String listOfItems){
        File newFile = new File("C://Warehouse//Group '"+groupName+"'.txt");
        try (BufferedWriter writter = new BufferedWriter(new FileWriter(newFile))) {
            writter.write(listOfItems);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteFileOfGroups(String groupName){
        File file = new File("C://Warehouse//Group '"+groupName+"'.txt");
        file.delete();
    }


}
