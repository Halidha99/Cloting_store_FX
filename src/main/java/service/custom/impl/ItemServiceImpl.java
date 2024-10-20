package service.custom.impl;

import javafx.collections.ObservableList;
import model.Item;
import service.custom.ItemService;

public class ItemServiceImpl implements ItemService {
    @Override
    public String generateItemId() {
        return "";
    }

    @Override
    public boolean addItem(Item item) {
        return false;
    }

    @Override
    public ObservableList getAllItem() {
        return null;
    }

    @Override
    public boolean updateItem(Item item) {
        return false;
    }

    @Override
    public boolean deleteItemById(String text) {
        return false;
    }

    @Override
    public Item searchItemByName(String name) {
        return null;
    }

    @Override
    public Item searchItemByID(String id) {
        return null;
    }
}
