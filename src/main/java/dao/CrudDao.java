package dao;

import entity.CustomerEntity;
import javafx.collections.ObservableList;


public interface CrudDao <T,S> extends SuperDao{
    T search (S s);

    ObservableList<CustomerEntity> getAll();

    boolean save(T t);

    boolean update(T t);

    boolean delete(S s);

    T searchByName(S s);

    String getLatestId();
}

