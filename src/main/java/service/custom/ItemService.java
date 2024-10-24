package service.custom;

import javafx.collections.ObservableList;
import model.Item;
import service.SuperService;



public interface ItemService extends SuperService {
    String generateItemId();
    boolean addItem(Item item);
    ObservableList getAllItem();
    boolean updateItem(Item item);
    boolean deleteItemById(String text);
    Item searchItemByName(String name);
    Item searchItemByID(String id);
}
