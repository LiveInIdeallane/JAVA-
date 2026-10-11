package DAO;

import entity.Nurselevel;

import java.sql.SQLException;
import java.util.List;

public interface NurselevelDAO {
    boolean save(Nurselevel obj) throws SQLException;
    boolean update(Nurselevel obj)  throws SQLException;
    boolean delete(Integer id)  throws SQLException;
    Nurselevel findOne(Integer id)  throws SQLException;
    List<Nurselevel> findAll()  throws SQLException;
}
