package service;

import entity.Outward;

import java.sql.SQLException;
import java.util.List;

public interface OutwardService {

    //基础的增删改查事务
    boolean CreateOutward(Outward outward);
    boolean UpdateOutward(Outward outward);
    boolean DeleteOutward(Outward outward);

    Outward GetOutwardById(Integer id);
    List<Outward> GetOutwards();

}
