package DAO;

import entity.Nurselevelitem;

import java.sql.SQLException;
import java.util.List;

public interface NurselevelitemDAO {
    boolean save(Nurselevelitem obj) throws SQLException;
    boolean update(Nurselevelitem obj)  throws SQLException;
    boolean delete(Integer id)  throws SQLException;
    Nurselevelitem findOne(Integer id)  throws SQLException;
    List<Nurselevelitem> findAll()  throws SQLException;
}
