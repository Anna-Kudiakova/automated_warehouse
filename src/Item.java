public class Item {

    private String group;
    private String name;
    private String description;
    private String manufacturer;
    private int quantity;
    private int price;

    Item(String group, String name, String description, String manufacturer, int quantity, int price){
        this.group = group;
        this.name = name;
        this.description = description;
        this.manufacturer = manufacturer;
        this.quantity = quantity;
        this.price = price;
    }

    public String getGroup(){
        return group;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public String getManufacturer(){
        return  manufacturer;
    }

    public int getQuantity(){
        return quantity;
    }

    public int getPrice(){
        return price;
    }

    public void editGroup(String newGroup){
        group = newGroup;
    }

    public void editName(String newName){
        String res = "Item name '"+name+"' was changed on "+newName;
        name = newName;
    }

    public void editDescription(String newDescription){
        description = newDescription;
    }

    public void editManufacturer(String newManufacturer){
        String res = "Manufacturer '"+name+"' was changed on "+newManufacturer;
        manufacturer = newManufacturer;
    }

    public void increaseQuantity(int n){
        quantity = quantity + n;
    }

    public void reduceQuantity(int n){
        if(n<=quantity)
            quantity = quantity - n;
    }

    public void changePrice(int newPrice){
        price = newPrice;
    }

    public String toString(){
        return name+"\nDescription: "+description+";\nManufacturer: "+manufacturer
                +";\nQuantity: "+quantity+" units;\nPrice: "+price+" UAH";
    }

    public String toStringWithGroup(){
        return name+"'\nGroup: '"+group+"';\nDescription: "+description+";\nManufacturer: "+manufacturer
                +";\nQuantity: "+quantity+" units;\nPrice: "+price+" UAH";
    }



}
