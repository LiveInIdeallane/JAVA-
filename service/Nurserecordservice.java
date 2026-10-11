package service;

import entity.Nurserecord;

import java.sql.SQLException;
import java.util.List;

public interface Nurserecordservice {
boolean addNurserecord(Nurserecord nurserecord);
boolean updateNurserecord(Nurserecord nurserecord);
boolean deleteNurserecord(Integer id);
Nurserecord findnurserecordById(Integer id) throws SQLException;
List<Nurserecord> findAllNurserecord() throws SQLException;
}
