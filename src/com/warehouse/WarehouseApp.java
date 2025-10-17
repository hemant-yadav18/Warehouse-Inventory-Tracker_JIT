package com.warehouse;

public class WarehouseApp {
    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();
        AlertService alertService = new AlertService();

        warehouse.addObserver(alertService);

        warehouse.addProduct(new Product("P101", "Laptop", 0, 5));
        warehouse.addProduct(new Product("P102", "Mouse", 0, 10));

        warehouse.receiveShipment("P101", 10);
        warehouse.fulfillOrder("P101", 6); 

        warehouse.receiveShipment("P102", 15);
        warehouse.fulfillOrder("P102", 5);
        warehouse.fulfillOrder("P102", 8); 

        warehouse.fulfillOrder("P103", 5);
        warehouse.receiveShipment("P101", -2);

        warehouse.displayInventory();
    }
}
