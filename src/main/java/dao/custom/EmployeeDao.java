package dao.custom;

import dao.CrudDao;
import entity.EmployeeEntity;

public interface EmployeeDao extends CrudDao<EmployeeEntity,String> {
    EmployeeEntity searchByEmail(String email);
}
