public class Tester {

    public static void main(String[] args) {
        UI ourUI = new UI();
        Warehouse warehouse = new Warehouse();

        System.out.println(warehouse.addGroup("Eclairs","with a pretty decoration and sot cream"));
        System.out.println(warehouse.addGroup("Cupcakes","attractive outside, divinely delicious inside"));
        System.out.println(warehouse.addGroup("Croissants","fresh crispy and aromatic"));
        System.out.println(warehouse.addGroup("Macarons", "confectionery that will steal your heart"));
        System.out.println(warehouse.addItem("Macarons","Lemon macaron","with lemon jam, marzipan filling and white chocolate","Bakery house",10,30));
        System.out.println(warehouse.addItem("Macarons","Caramel macaron","with solt caramel and vanilla cream","Bakery house",10,30));
        System.out.println(warehouse.addItem("Macarons","Pistachio macaron","with pistachio cream","Bakery house",10,35));
        System.out.println(warehouse.addItem("Macarons","Blackcurrants macaron","with blackcurrants kuli and vanilla cream","Bakery house",10,30));
        System.out.println(warehouse.addItem("Croissants","Almond croissant","with custard, almond creme and  blanched almond kernels","Sweet bite",8,45));
        System.out.println(warehouse.addItem("Croissants","Pistachio croissant","with extra-pistachio cream","Sweet bite",8,55));
        System.out.println(warehouse.addItem("Croissants","Butter croissant","classic French croissant","Sweet bite",10,30));
        System.out.println(warehouse.addItem("Croissants","Matcha croissant","with matcha cream, covered in matcha-chocolate","Sweet bite",7,55));
        System.out.println(warehouse.editItemPrice("croissants","almond Croissant",40));
        System.out.println(warehouse.increaseItemQuantity("Croissants","Pistachio croissant",15));
        System.out.println(warehouse.editItemManufacturer("Macarons", "Caramel macaron","Dreamy France"));
        System.out.println(warehouse.deleteItem("Croissants","Matcha croissant"));

    }
}

