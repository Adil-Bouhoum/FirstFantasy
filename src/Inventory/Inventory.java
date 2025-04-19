package Inventory;
import Inventory.Items.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Inventory<T extends Item> {
    private final List<T> items;
    private final int capacity;

    public Inventory(int capacity) {
        this.items = new ArrayList<>();
        this.capacity = capacity;
    }

    public boolean addItem(T item) {
        if (items.size() < capacity) {
            items.add(item);
            return true;
        }
        return false; // Inventory full
    }

    public void removeItem(T item) {
        items.remove(item);
    }

    public List<T> getItems() {
        return new ArrayList<>(items); // Return copy to prevent external modification
    }

    public <U extends T> List<U> getItemsOfType(Class<U> type) {
        return items.stream()
                .filter(type::isInstance)
                .map(type::cast)
                .collect(Collectors.toList());
    }

    public boolean contains(T item) {
        return items.contains(item);
    }

    public int getSize() {
        return items.size();
    }

    public int getCapacity() {
        return capacity;
    }
}