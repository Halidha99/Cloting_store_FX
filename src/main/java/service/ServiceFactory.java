package service;

import service.custom.impl.*;
import util.ServiceType;

public class ServiceFactory {
    private static ServiceFactory instance;

    private ServiceFactory(){}

    public static  ServiceFactory getInstance(){
        return  instance==null?instance=new ServiceFactory():instance;
    }
 public <T extends SuperService> T getServiceType(ServiceType type){
     switch (type){
         case EMPLOYEE:return (T)new EmployeeServiceImpl();
         case CUSTOMER:return (T)new CustomerServiceImpl();
         case SUPPLIER:return (T)new SupplierServiceImpl();
         case ITEM:return (T)new ItemServiceImpl();
         case ORDER:return (T)new OrderServiceImpl();
     }
     return null;
 }
}