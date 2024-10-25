package service.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.DaoFactory;
import dao.custom.ItemDao;

import entity.ItemEntity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Item;

import service.custom.ItemService;
import util.DaoType;

public class ItemServiceImpl implements ItemService {
   ItemDao itemDao= DaoFactory.getInstance().getDaoType(DaoType.ITEM);
    @Override
    public String generateItemId() {

        String lastSupplierId = itemDao.getLatestId();

        if (lastSupplierId == null || lastSupplierId.isEmpty()) {

            return "I001";
        }

        try {

            String[] parts = lastSupplierId.split("I");

            if (parts.length < 2 || parts[1].isEmpty()) {
                throw new NumberFormatException("Invalid Item Id format.");
            }


            int number = Integer.parseInt(parts[1]);
            number++;


            return String.format("I%03d", number);
        } catch (NumberFormatException e) {

            e.printStackTrace();
            throw new RuntimeException("Failed to generate item ID from: " + lastSupplierId);
        }
    }

    @Override
    public boolean addItem(Item item) {
       ItemEntity itemEntity = new ObjectMapper().convertValue(item, ItemEntity.class);
        return itemDao.save(itemEntity);
    }

    @Override
    public ObservableList getAllItem() {
        ObservableList<ItemEntity> allItem = itemDao.getAll();
        ObservableList<Item> itemList = FXCollections.observableArrayList();
        allItem.forEach(itemEntity->{
            itemList.add(new ObjectMapper().convertValue(itemEntity,Item.class));
        });
        return  itemList;
    }

    @Override
    public boolean updateItem(Item item) {
        ItemEntity itemEntity = new ObjectMapper().convertValue(item, ItemEntity.class);
        return itemDao.update(itemEntity);
    }

    @Override
    public boolean deleteItemById(String text) {
        return itemDao.delete(text);
    }

    @Override
    public Item searchItemByName(String name) {
       ItemEntity itemEntity = itemDao.searchByName(name);
        return new ObjectMapper().convertValue(itemEntity,Item.class);
    }

    @Override
    public Item searchItemByID(String id) {
        ItemEntity itemEntity = itemDao.search(id);
        return new ObjectMapper().convertValue(itemEntity,Item.class);
    }
}
