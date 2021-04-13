import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class Group {

    private ArrayList<Item> items = new ArrayList<>();
    private String groupName;
    private String groupDescription;

    Group(String name, String description){
        groupName = name;
        groupDescription = description;
    }


    public ArrayList<Item> getItems(){
        return items;
    }



    public String getGroupName(){
        return groupName;
    }

    public String editName(String newName){
        String res = "Group name '"+groupName+"' was changed on "+newName;
        groupName = newName;
        for(Item n : items){
            n.editGroup(newName);
        }
        return res;
    }

    public void editGroupDescription(String newDescription){
        groupDescription = newDescription;
    }

    public String getGroupDescription(){
        return groupDescription;
    }



    public void sortByAlphabet(){
        Collections.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item first, Item second) {
                return first.getName().compareTo(second.getName());
            }
        });
    }


    public String displayTheGroupList(){
        if(items.size()!=0) {
            String result = "The list of the items of the '"+groupName+"' group\n";
            for (Item st: items) {
                result += "\n"+st.toString()+"\n";
            }
            return result;
        }
        return "There is no item in the group '"+groupName+"' \n";
    }

    public int countTotalCost(){
        int cost = 0;
        for(Item it : items){
            cost += it.getPrice()*it.getQuantity();
        }
        return cost;
    }

    public String toString(){
        return "Group "+groupName+"\nDescription: "+groupDescription+"\n";
    }



}