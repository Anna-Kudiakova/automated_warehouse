import java.awt.*;
import java.util.*;

public class Warehouse {

    private String name;
    private ArrayList<Group> groups = new ArrayList<Group>();
    private FileMaker fm;

    Warehouse(){
        fm = new FileMaker();
        fm.makeFileOfGroups("There isn't any group yet");
    }

    public String addGroup(String name, String description){
        String nameLowerCase = name.toLowerCase(Locale.ROOT);
        for(Group gr: groups){
            if(gr.getGroupName().toLowerCase(Locale.ROOT).equals(nameLowerCase)){
                return "Group with such name already exists";
            }
        }
        groups.add(new Group(name, description));
        //після додавання нової групи сортуємо групи за алфавітом
        Collections.sort(groups, new Comparator<Group>() {
            @Override
            public int compare(Group first, Group second) {
                return first.getGroupName().compareTo(second.getGroupName());
            }
        });
        //кожного разу після редагуання перезаписуємо файл
        editFileGroups();
        editFileItems(name);
        return "New group '"+name+"' was added";
    }

    public String deleteGroup(String name){
        String nameLowerCase = name.toLowerCase(Locale.ROOT);
        for(Iterator<Group> it = groups.iterator(); it.hasNext(); ){
            Group gr = it.next();
            if(gr.getGroupName().toLowerCase(Locale.ROOT).equals(nameLowerCase)){
                it.remove();
                //кожного разу після редагуання перезаписуємо файл
                editFileGroups();
                fm.deleteFileOfGroups(name);
                if(groups.size()==0)
                    fm.makeFileOfGroups("There isn't any group");
                return "Group with name '"+name+"' was deleted\n";
            }
        }
        return "\nThere isn't  group of items with name '"+name+"' in the warehouse\n";
    }


    public String editGroupName(String name, String newName){
        boolean groupExist = false;
        String newNameLowerCase = newName.toLowerCase(Locale.ROOT);
        //перевіряє чи існує група з іменем на яке ми хочемо змінити
        for(Group gr : groups){
            if(gr.getGroupName().toLowerCase(Locale.ROOT).equals(newNameLowerCase)){
                return "Group with title '"+newName+"' already exists";
            }
        }
        //шукає групу яку ми хочемо змінити
        String nameLowerCase = name.toLowerCase(Locale.ROOT);
        for(int i=0; i< groups.size(); i++) {
            if (groups.get(i).getGroupName().toLowerCase(Locale.ROOT).equals(nameLowerCase)) {
                groups.get(i).editName(newName);
                //сортуємо за алфавітом щоразу як змінюємо назву товару
                Collections.sort(groups, new Comparator<Group>() {
                    @Override
                    public int compare(Group first, Group second) {
                        return first.getGroupName().compareTo(second.getGroupName());
                    }
                });
                //кожного разу після редагуання перезаписуємо файл
                editFileGroups();
                editFileItems(name);
                return "\nGroup name '" + name + "' was changed on '" + newName + "'\n";
            }
        }
        return "\nThere isn't group of items with name '"+name+"' in the warehouse\n";
    }


    public String editGroupDescription(String group, String newDescription){
        String groupLowerCase = group.toLowerCase(Locale.ROOT);
        for(int i=0; i< groups.size(); i++) {
            if (groups.get(i).getGroupName().toLowerCase(Locale.ROOT).equals(groupLowerCase)) {
                groups.get(i).editGroupDescription(newDescription);
                //кожного разу після редагуання перезаписуємо файл
                editFileGroups();
                editFileItems(group);
                return "Description of the '"+group+"' was edited";
            }
        }
        return "\nThere isn't group of items with name '"+name+"' in the warehouse\n";
    }

    //внутрішній метод для перезапису файлу груп
    private void editFileGroups(){
        String res = "List of the groups of the items\n";
        for(Group gr: groups){
            res += "\n"+gr.toString()+"\n";
        }
        fm.makeFileOfGroups(res);
    }

    //внутрішній метод для перезапису файлу певної групи
    private void editFileItems(String group){
        for(Group gr: groups){
            if(gr.getGroupName().equals(group)){
                fm.makeFileOfGroupItems(gr.getGroupName(), gr.displayTheGroupList());
            }
        }
    }




    public String addItem(String group, String name, String description, String manufacturer, int quantity, int price){
        boolean groupExist = false;
        int index = 0;
        //перевіряє чи існує товар з такою назвою
        for(Group gr: groups){
            for(Item it: gr.getItems()){
                if(it.getName().equals(name)){
                    return "Item with name"+name+" already exists in the group "+gr.getGroupName();
                }
            }
        }
        //додаємо товар в масив відповідної групи
        String groupLowerCase = group.toLowerCase(Locale.ROOT);
        for(int i=0; i< groups.size(); i++) {
            if (groups.get(i).getGroupName().toLowerCase(Locale.ROOT).equals(groupLowerCase)) {
                groups.get(i).getItems().add(new Item(group,name,description,manufacturer,quantity,price));
                groups.get(i).sortByAlphabet(); //сортуємо після додавання за алфавітом
                //кожного разу після редагуання перезаписуємо файл
                editFileItems(group);
                return name+" was added to the "+group+" group";
            }
        }
        return "Group with title "+group+" doesn't exist";
    }

    public String editItemName(String group, String name, String newName) {
        String groupLowerCase = group.toLowerCase(Locale.ROOT);
        String nameLowerCase = name.toLowerCase(Locale.ROOT);
        String newNameLowerCase = newName.toLowerCase(Locale.ROOT);
        boolean groupExist = false;
        //перевіряє чи існує товар з такою назвою
        for (Group gr : groups) {
            for (Item it : gr.getItems()) {
                if (it.getName().toLowerCase(Locale.ROOT).equals(newNameLowerCase)) {
                    return "Item with name " + newName + " already exists in the group " + gr.getGroupName();
                }
            }
        }
        for(Group gr: groups) {
            if (gr.getGroupName().toLowerCase(Locale.ROOT).equals(groupLowerCase)) {
                groupExist = true;
                for(int g = 0; g<gr.getItems().size(); g++){
                    if(gr.getItems().get(g).getName().toLowerCase(Locale.ROOT).equals(nameLowerCase)){
                        gr.getItems().get(g).editName(newName);
                        gr.sortByAlphabet(); //сортуємо після редагування за алфавітом
                        //кожного разу після редагуання перезаписуємо файл
                        editFileItems(group);
                        return "Item with name " + name + " was changed on " + newName;
                    }
                }
            }
        }
        if(!groupExist){
            return "Group with name '"+group+"' doesn't exist";
        }
        return "Item with name '"+name+"' doesn't exist in the group "+group;
    }

    //метод редагування ціни товару з певної групи
    public String deleteItem(String group, String name){
        String groupLowerCase = group.toLowerCase(Locale.ROOT);
        String nameLowerCase = name.toLowerCase(Locale.ROOT);
        boolean groupExist = false;
        for(Group gr: groups) {
            if (gr.getGroupName().toLowerCase(Locale.ROOT).equals(groupLowerCase)) {
                groupExist = true;
                for(Iterator<Item> it = gr.getItems().iterator(); it.hasNext(); ){
                    Item item = it.next();
                    if(item.getName().toLowerCase(Locale.ROOT).equals(nameLowerCase)){
                        gr.getItems().remove(item);
                        //кожного разу після редагуання перезаписуємо файл
                        editFileItems(group);
                        return "Item with name '"+name+"' was deleted from the group '"+group+"'";
                    }
                }
            }
        }
        if(!groupExist){
            return "Group with name '"+group+"' doesn't exist";
        }
        return "Item with name '"+name+"' doesn't exist in the group "+group;
    }

    //метод редагування опису товару з певної групи
    public String editItemDescription(String group, String name, String newDescription){
        String groupLowerCase = group.toLowerCase(Locale.ROOT);
        String nameLowerCase = name.toLowerCase(Locale.ROOT);
        boolean groupExist = false;
        for(Group gr: groups) {
            if (gr.getGroupName().toLowerCase(Locale.ROOT).equals(groupLowerCase)) {
                groupExist = true;
                for(int g = 0; g<gr.getItems().size(); g++){
                    if(gr.getItems().get(g).getName().toLowerCase(Locale.ROOT).equals(nameLowerCase)){
                        gr.getItems().get(g).editDescription(newDescription);
                        //кожного разу після редагуання перезаписуємо файл
                        editFileItems(group);
                        return "Description of the '"+name+"' was edited";
                    }
                }
            }
        }
        if(!groupExist){
            return "Group with name '"+group+"' doesn't exist";
        }
        return "Item with name '"+name+"' doesn't exist in the group "+group;
    }

    //метод редагування виробника товару з певної групи
    public String editItemManufacturer(String group, String name, String newManufacturer){
        String groupLowerCase = group.toLowerCase(Locale.ROOT);
        String nameLowerCase = name.toLowerCase(Locale.ROOT);
        boolean groupExist = false;
        for(Group gr: groups) {
            if (gr.getGroupName().toLowerCase(Locale.ROOT).equals(groupLowerCase)) {
                groupExist = true;
                for(int g = 0; g<gr.getItems().size(); g++){
                    if(gr.getItems().get(g).getName().toLowerCase(Locale.ROOT).equals(nameLowerCase)){
                        gr.getItems().get(g).editManufacturer(newManufacturer);
                        //кожного разу після редагуання перезаписуємо файл
                        editFileItems(group);
                        return "'"+name+"' "+"manufacturer  was changed on '"+newManufacturer+"'";

                    }
                }
            }
        }
        if(!groupExist){
            return "Group with name '"+group+"' doesn't exist";
        }
        return "Item with name '"+name+"' doesn't exist in the group "+group;
    }

    //метод списування товару з певної групи
    public String reduceItemQuantity(String group, String name, int n){
        String groupLowerCase = group.toLowerCase(Locale.ROOT);
        String nameLowerCase = name.toLowerCase(Locale.ROOT);
        if(n<=0){
            return "Incorrect input value of quantity";
        }
        boolean groupExist = false;
        for(Group gr: groups) {
            if (gr.getGroupName().toLowerCase(Locale.ROOT).equals(groupLowerCase)) {
                groupExist = true;
                for(int g = 0; g<gr.getItems().size(); g++){
                    if(gr.getItems().get(g).getName().toLowerCase(Locale.ROOT).equals(nameLowerCase)){
                        gr.getItems().get(g).reduceQuantity(n);
                        if(n<=gr.getItems().get(g).getQuantity()) {
                            //кожного разу після редагуання перезаписуємо файл
                            editFileItems(group);
                            return n + " unit(s) of the "+name+" was(were) sold";
                        }
                        else{
                            return "There aren't enough items in the warehouse to perform the operation\n" +
                                    "Actual quantity of '"+name+"' - "+gr.getItems().get(g).getQuantity()+" units";
                        }
                    }
                }
            }
        }
        if(!groupExist){
            return "Group with name '"+group+"' doesn't exist";
        }
        return "Item with name '"+name+"' doesn't exist in the group "+group;
    }

    //метод збільшення кількості товару з певної групи
    public String increaseItemQuantity(String group, String name, int n){
        String groupLowerCase = group.toLowerCase(Locale.ROOT);
        String nameLowerCase = name.toLowerCase(Locale.ROOT);
        if(n<=0){
            return "Incorrect input value of quantity";
        }
        boolean groupExist = false;
        for(Group gr: groups) {
            if (gr.getGroupName().toLowerCase(Locale.ROOT).equals(groupLowerCase)) {
                groupExist = true;
                for(int g = 0; g<gr.getItems().size(); g++){
                    if(gr.getItems().get(g).getName().toLowerCase(Locale.ROOT).equals(nameLowerCase)){
                        gr.getItems().get(g).increaseQuantity(n);
                        //кожного разу після редагуання перезаписуємо файл
                        editFileItems(group);
                        return n+" units of the '"+name+"' arrived to the warehouse";
                    }
                }
            }
        }
        if(!groupExist){
            return "Group with name '"+group+"' doesn't exist";
        }
        return "Item with name '"+name+"' doesn't exist in the group "+group;
    }

    //метод редагування ціни товару з певної групи
    public String editItemPrice(String group, String name, int newPrice){
        String groupLowerCase = group.toLowerCase(Locale.ROOT);
        String nameLowerCase = name.toLowerCase(Locale.ROOT);
        if(newPrice<=0){
            return "Incorrect input value of the new price";
        }
        boolean groupExist = false;
        for(Group gr: groups) {
            if (gr.getGroupName().toLowerCase(Locale.ROOT).equals(groupLowerCase)) {
                groupExist = true;
                for(int g = 0; g<gr.getItems().size(); g++){
                    if(gr.getItems().get(g).getName().toLowerCase(Locale.ROOT).equals(nameLowerCase)){
                        gr.getItems().get(g).changePrice(newPrice);
                        //кожного разу після редагуання перезаписуємо файл
                        editFileItems(group);
                        return "'"+name+"' "+"price  was changed on "+newPrice+" UAH";

                    }
                }
            }
        }
        if(!groupExist){
            return "Group with name '"+group+"' doesn't exist";
        }
        return "Item with name '"+name+"' doesn't exist in the group "+group;
    }

    //метод що обраховує вартість товару у певній групі
    public String groupTotalCost(String group){
        String groupLowerCase = group.toLowerCase(Locale.ROOT);
        for(Group gr: groups) {
            if (gr.getGroupName().toLowerCase(Locale.ROOT).equals(groupLowerCase)) {
                return "Total cost of the items in the group '"+group+"' - "+gr.countTotalCost()+" UAH";
            }
        }
        return "Group with name '"+group+"' doesn't exist";
    }

    //метод що обраховує вартість усього товару на складі
    public String totalCost(){
        int totalCost = 0;
       for(Group gr: groups) {
           totalCost += gr.countTotalCost();
       }
       return "Total cost of all items in the warehouse - "+totalCost+" UAH";
    }

    //метод пошуку товару по імені
    public String findItem(String name){
        String nameLowerCase = name.toLowerCase(Locale.ROOT);
        for(Group gr: groups){
            for(Item it: gr.getItems()){
                if(it.getName().toLowerCase(Locale.ROOT).equals(nameLowerCase)){
                    return "Information about  '"+it.toStringWithGroup();
                }
            }
        }
        return "In the warehouse there isn't item with name '"+name+"' in any group";
    }

    //вивід усіх товарів по групі товарів з інформацією
    public String displayGroupList(String group){
        String groupLowerCase = group.toLowerCase(Locale.ROOT);
        for(Group gr: groups) {
            if (gr.getGroupName().toLowerCase(Locale.ROOT).equals(groupLowerCase)) {
                return gr.displayTheGroupList();
            }
        }
        return "Group with name '"+group+"' doesn't exist";
    }



    //вивід всіх товарів з інформацією по складу
    public String displayInfoList(){
        String res = "The list of the items with all information\n";
        for (Group gr : groups) {
            res += "\nGroup "+gr.getGroupName()+"\nDescription: "+gr.getGroupDescription()+"\n";
            for (Item it : gr.getItems()) {
                res += "\n"+it.toString()+"\n";
            }
        }
        return res+"\n";
    }









}
