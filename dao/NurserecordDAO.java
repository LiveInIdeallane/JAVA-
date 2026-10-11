package DAO;

import entity.Nurserecord;

import java.sql.SQLException;
import java.util.List;

public interface NurserecordDAO {
        boolean save(Nurserecord obj) throws SQLException;
        boolean update(Nurserecord obj)  throws SQLException;
        boolean delete(Integer id)  throws SQLException;
        Nurserecord findOne(Integer id)  throws SQLException;
        List<Nurserecord> findAll()  throws SQLException;
}
