package com.warehouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Warehouse {
    private Map<String, Product> inventory = new HashMap<>();
    private List<StockObserver> observers = new ArrayList<>();

    public void addObserver(StockObserver observer) {
        observers.add(observer);
    }

    public void addProduct(Product product) {
        if (inventory.containsKey(product.getId())) {
            System.out.println(" Product with ID " + product.getId() + " already exists.");
            return;
        }
        inventory.put(product.getId(), product);
        System.out.println(" Product added: " + product.getName());
    }

    public void receiveShipment(String productId, int quantity) {
        Product product = inventory.get(productId);
        if (product == null) {
            System.out.println(" Invalid product ID: " + productId);
            return;
        }
        if (quantity <= 0) {
            System.out.println("  Shipment quantity must be positive.");
            return;
        }
        product.setQuantity(product.getQuantity() + quantity);
        System.out.println(" Received shipment: " + quantity +
                " units of " + product.getName() + ". Total = " + product.getQuantity());
    }

    public void fulfillOrder(String productId, int quantity) {
        Product product = inventory.get(productId);
        if (product == null) {
            System.out.println(" Invalid product ID: " + productId);
            return;
        }
        if (quantity <= 0) {
            System.out.println(" Order quantity must be positive.");
            return;
        }
        if (product.getQuantity() < quantity) {
            System.out.println(" Insufficient stock for " + product.getName());
            return;
        }

        product.setQuantity(product.getQuantity() - quantity);
        System.out.println(" Fulfilled order: " + quantity +
                " units of " + product.getName() + ". Remaining = " + product.getQuantity());

        if (product.getQuantity() < product.getReorderThreshold()) {
            notifyObservers(product);
        }
    }

    private void notifyObservers(Product product) {
        for (StockObserver observer : observers) {
            observer.onLowStock(product);
        }
    }

    public void displayInventory() {
        System.out.println("\n Current Warehouse Inventory:");
        for (Product p : inventory.values()) {
            System.out.println(p);
        }
    }
}
