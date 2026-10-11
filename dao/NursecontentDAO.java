package DAO;

import entity.Nursecontent;

import java.sql.SQLException;
import java.util.List;

public interface NursecontentDAO {
    boolean save(Nursecontent obj) throws SQLException;
    boolean update(Nursecontent obj)  throws SQLException;
    boolean delete(Integer id)  throws SQLException;
    Nursecontent findOne(Integer id)  throws SQLException;
    List<Nursecontent> findAll()  throws SQLException;
}
