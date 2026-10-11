package service;

import DAO.NurselevelitemDAO;
import DAO.NurselevelitemDAOIMPL;
import JdbcUtils.Jdbc;
import entity.Nurselevelitem;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class NurselevelitemserviceIMPL implements  Nurselevelitemservice {
    private NurselevelitemDAO nurselevelitemdao =new NurselevelitemDAOIMPL();

    @Override
    public boolean addNurselevelitem(Nurselevelitem nurselevelitem) {
        if (nurselevelitem == null) {
            throw new RuntimeException("参数对象不能为空!");
        }
        if(nurselevelitem.getLevel_id()==null){
            throw new RuntimeException("关联护理级别不能为空");
        }
        if(nurselevelitem.getItem_id()==null){
            throw new RuntimeException("关联护理项目不能为空");
        }
        Connection conn = null;
        try {
            conn = Jdbc.getConnection();
            conn.setAutoCommit(false); //关闭自动提交，开启事务
            boolean res = nurselevelitemdao.save(nurselevelitem);
            conn.commit(); //正常提交
            return res;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); //出错回滚
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new RuntimeException(e);
        } finally {
            Jdbc.close();
        }
    }
    public boolean updateNurselevelitem(Nurselevelitem nurselevelitem) {
        if (nurselevelitem == null) {
            throw new RuntimeException("参数对象不能为空!");
        }
        if(nurselevelitem.getLevel_id()==null){
            throw new RuntimeException("关联护理级别不能为空");
        }
        if(nurselevelitem.getItem_id()==null){
            throw new RuntimeException("关联护理项目不能为空");
        }
        Connection conn = null;
        try {
            conn = Jdbc.getConnection();
            conn.setAutoCommit(false); //关闭自动提交，开启事务
            boolean res = nurselevelitemdao.update(nurselevelitem);
            conn.commit(); //正常提交
            return res;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); //出错回滚
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new RuntimeException(e);
        } finally {
            Jdbc.close();
        }
}

    @Override
    public boolean deleteNurselevelitem(Integer id) {
        if (id == null) {
            throw new RuntimeException("id不能为空");
        }
        Connection conn = null;
        try {
            conn = Jdbc.getConnection();
            conn.setAutoCommit(false); //关闭自动提交，开启事务
            boolean res = nurselevelitemdao.delete(id);
            conn.commit(); //正常提交
            return res;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); //出错回滚
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new RuntimeException(e);
        } finally {
            Jdbc.close();
        }
    }

    @Override
    public Nurselevelitem findNurselevelitemById(Integer id) throws SQLException {
        if (id == null) {
            throw new RuntimeException("id不能为空");
        }
        return nurselevelitemdao.findOne(id);
    }

    @Override
    public List<Nurselevelitem> findAllNurselevelitem() throws SQLException {
        return nurselevelitemdao.findAll();
    }
}
