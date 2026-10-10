package dao;

import entity.Backdown;
import util.DbOpr;
import util.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BackdownDAOImpl implements BackdownDAO {

    @Override
    public boolean saveBackdown(Backdown backdown) throws SQLException {
        String sql = "insert into backdown(id,remarks,id_deleted,customer_id,retreattime,retreattype,retreatreason,auditstatus,auditperson,audittime) values(?,?,?,?,?,?,?,?,?,?)";
        Connection conn = JdbcUtils.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        Object[] params = {backdown.getId(),backdown.getRemarks(),backdown.getIs_delete(),backdown.getCustomer_id(),backdown.getRetreattime(),backdown.getRetreattype(),backdown.getRetreatreason(),backdown.getAuditstatus(),backdown.getAuditperson(),backdown.getAudittime()};
        DbOpr.CompleSQL(ps, params);
        int row = ps.executeUpdate();
        return row>0?true:false;
    }

    @Override
    public boolean updateBackdown(Backdown backdown) throws SQLException {
        String sql = "  backdown(id,remarks,id_deleted,customer_id,retreattime,retreattype,retreatreason,auditstatus,auditperson,audittime) values(?,?,?,?,?,?,?,?,?,?)";
        Connection conn = JdbcUtils.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        Object[] params = {backdown.getId(),backdown.getRemarks(),backdown.getIs_delete(),backdown.getCustomer_id(),backdown.getRetreattime(),backdown.getRetreattype(),backdown.getRetreatreason(),backdown.getAuditstatus(),backdown.getAuditperson(),backdown.getAudittime()};
        DbOpr.CompleSQL(ps, params);

        int row = ps.executeUpdate();
        return row>0?true:false;
    }

    @Override
    public boolean deleteBackdown(Integer id) throws SQLException {
        String sql = "delete from backdown where id=?";
        Connection conn = JdbcUtils.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        Object[] params = {id};
        DbOpr.CompleSQL(ps, params);
        int row = ps.executeUpdate();

        return row>0?true:false;
    }

    @Override
    public Backdown findBackdownById(Integer id) throws SQLException, InstantiationException, IllegalAccessException {
        String sql = "select * from backdown where id=?";
        Connection conn = JdbcUtils.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        Object[] params = {id};
        List<Backdown> backdowns = DbOpr.select(ps,params,Backdown.class);

        return  backdowns.size()>0?backdowns.get(0):null;
    }

    @Override
    public List<Backdown> getAllBackdowns() throws SQLException, InstantiationException, IllegalAccessException {
        String sql = "select * from backdown";
        Connection conn = JdbcUtils.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        Object[] params = {};
        List<Backdown> backdowns = DbOpr.select(ps,params,Backdown.class);

        return backdowns;
    }
}
