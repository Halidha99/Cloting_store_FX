package service.custom;

import javafx.collections.ObservableList;
import model.Supplier;
import service.SuperService;

public interface SupplierService  extends SuperService {
    String generateSupplierId();
    boolean addSupplier(Supplier supplier);
    ObservableList getAllSupplier();
    boolean updateSupplier(Supplier supplier);
    boolean deleteSupplierById(String text);
    Supplier searchSupplierByName(String name);
    Supplier searchSupplierByID(String id);
}
