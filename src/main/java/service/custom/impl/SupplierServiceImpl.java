package service.custom.impl;

import javafx.collections.ObservableList;
import model.Supplier;
import service.custom.SupplierService;

public class SupplierServiceImpl implements SupplierService {
    @Override
    public String generateSupplierId() {
        return "";
    }

    @Override
    public boolean addSupplier(Supplier supplier) {
        return false;
    }

    @Override
    public ObservableList getAllSupplier() {
        return null;
    }

    @Override
    public boolean updateSupplier(Supplier supplier) {
        return false;
    }

    @Override
    public boolean deleteSupplierById(String text) {
        return false;
    }

    @Override
    public Supplier searchSupplierByName(String name) {
        return null;
    }

    @Override
    public Supplier searchSupplierByID(String id) {
        return null;
    }
}
