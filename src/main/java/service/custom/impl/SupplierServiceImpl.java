package service.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.DaoFactory;
import dao.custom.CustomerDao;
import dao.custom.SupplierDao;
import entity.SupplierEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Supplier;
import service.custom.SupplierService;
import util.DaoType;

public class SupplierServiceImpl implements SupplierService {
    SupplierDao supplierDao= DaoFactory.getInstance().getDaoType(DaoType.SUPPLIER);
    @Override
    public String generateSupplierId() {

        String lastSupplierId = supplierDao.getLatestId();

        if (lastSupplierId == null || lastSupplierId.isEmpty()) {

            return "S001";
        }

        try {

            String[] parts = lastSupplierId.split("S");

            if (parts.length < 2 || parts[1].isEmpty()) {
                throw new NumberFormatException("Invalid Supplier Id format.");
            }


            int number = Integer.parseInt(parts[1]);
            number++;


            return String.format("S%03d", number);
        } catch (NumberFormatException e) {

            e.printStackTrace();
            throw new RuntimeException("Failed to generate supplier ID from: " + lastSupplierId);
        }
    }

    @Override
    public boolean addSupplier(Supplier supplier) {
      SupplierEntity supplierEntity = new ObjectMapper().convertValue(supplier, SupplierEntity.class);
        return supplierDao.save(supplierEntity);
    }

    @Override
    public ObservableList getAllSupplier() {
        ObservableList<SupplierEntity> list = supplierDao.getAll();
        ObservableList<Supplier> userList = FXCollections.observableArrayList();

        list.forEach(userEntity -> {
            userList.add(new ObjectMapper().convertValue(userEntity,Supplier.class));
        });
        return userList;
    }

    @Override
    public boolean updateSupplier(Supplier supplier) {
        SupplierEntity supplierEntity = new ObjectMapper().convertValue(supplier, SupplierEntity.class);
        return supplierDao.update(supplierEntity);
    }

    @Override
    public boolean deleteSupplierById(String text) {
        return supplierDao.delete(text);
    }

    @Override
    public Supplier searchSupplierByName(String name) {

        SupplierEntity supplierEntity = supplierDao.searchByName(name);
        return new ObjectMapper().convertValue(supplierEntity,Supplier.class);
    }

    @Override
    public Supplier searchSupplierByID(String id) {

        SupplierEntity supplierEntity = supplierDao.search(id);
        return new ObjectMapper().convertValue(supplierEntity,Supplier.class);
    }

    public boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }


        int atIndex = email.indexOf('@');
        if (atIndex == -1 || atIndex != email.lastIndexOf('@')) {
            return false;
        }


        if (atIndex == 0) {
            return false;
        }


        String domain = email.substring(atIndex + 1);


        int dotIndex = domain.indexOf('.');
        if (dotIndex == -1 || dotIndex == 0 || dotIndex == domain.length() - 1) {
            return false;
        }


        if (email.contains(" ")) {
            return false;
        }

        return true;
    }
}
