package dao;

import entity.Outward;
import util.DbOpr;
import util.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OutwardDAOImpl implements OutwardDAO {


    @Override
    public Boolean saveOutward(Outward outward) throws SQLException {
        String sql = "INSERT into outward values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Connection conn = JdbcUtils.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        Object[] params = {outward.getId(),outward.getRemarks(),outward.getIs_delete(),outward.getCustomer_id(),outward.getOutgoingreason(),outward.getOutgoingtime(),outward.getExpectedreturntime(),outward.getActualreturntime(),outward.getEscortedtel(),outward.getAuditstatus(),outward.getAuditperson(),outward.getAudittime()};
        DbOpr.CompleSQL(ps,params);

        int row = ps.executeUpdate();
        return row>0?true:false;
    }

    @Override
    public Boolean deleteOutward(Integer id) throws SQLException {
        String sql = "DELETE from outward where id=?";
        Connection conn = JdbcUtils.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        Object[] params = {id};
        DbOpr.CompleSQL(ps,params);

        int row = ps.executeUpdate();
        return row>0?true:false;
    }

    @Override
    public Boolean updateOutward(Outward outward) throws SQLException {
        String sql = "UPDATE outward SET remarks = ?, is_deleted = ?, customer_id = ?, outgoingreason = ?, outgoingtime = ?, epectedreturntime = ?, actualreturntime = ?, escorted = ?, relation = ?, escortedtel = ?, auditstatus = ?, auditperson = ?, audittime = ? where id = ?";
        Connection conn = JdbcUtils.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        Object[] params = {outward.getRemarks(),outward.getIs_delete(),outward.getCustomer_id(),outward.getOutgoingreason(),outward.getOutgoingtime(),outward.getExpectedreturntime(),outward.getActualreturntime(),outward.getEscortedtel(),outward.getAuditstatus(),outward.getAuditperson(),outward.getAudittime(),outward.getId()};
        DbOpr.CompleSQL(ps,params);

        int row = ps.executeUpdate();
        return row>0?true:false;
    }

    @Override
    public Outward findOutwardById(Integer ID) throws SQLException {
        String sql = "select * from outward where id=?";
        Connection conn = JdbcUtils.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        Object[] params = {ID};
        DbOpr.CompleSQL(ps,params);
        ResultSet rs = ps.executeQuery();
        Integer id = rs.getInt("id");
        String remarks = rs.getString("remarks");
        Integer is_deleted = rs.getInt("is_deleted");
        Integer customer_id = rs.getInt("customer_id");
        String outgoingreason = rs.getString("outgoingreason");
        Date outgoingtime = rs.getDate("outgoingtime");
        Date expectedreturntime = rs.getDate("epectedreturntime");
        Date actualreturntime = rs.getDate("actualreturntime");
        String escorted = rs.getString("escorted");
        String  relation = rs.getString("relation");
        String escortedtel = rs.getString("escortedtel");
        Integer auditstatus = rs.getInt("auditstatus");
        String auditperson = rs.getString("auditperson");
        Date audittime = rs.getDate("audittime");


        return new Outward(id,remarks,is_deleted,customer_id,outgoingreason,outgoingtime,expectedreturntime,actualreturntime,escorted,relation,escortedtel,auditstatus,auditperson,audittime);
    }

    @Override
    public List<Outward> findAllOutward() throws SQLException {
        String sql = "select * from outward";
        Connection conn = JdbcUtils.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Outward> outwards = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String remarks = rs.getString("remarks");
            Integer is_deleted = rs.getInt("is_deleted");
            Integer customer_id = rs.getInt("customer_id");
            String outgoingreason = rs.getString("outgoingreason");
            Date outgoingtime = rs.getDate("outgoingtime");
            Date expectedreturntime = rs.getDate("epectedreturntime");
            Date actualreturntime = rs.getDate("actualreturntime");
            String escorted = rs.getString("escorted");
            String  relation = rs.getString("relation");
            String escortedtel = rs.getString("escortedtel");
            Integer auditstatus = rs.getInt("auditstatus");
            String auditperson = rs.getString("auditperson");
            Date audittime = rs.getDate("audittime");
            outwards.add(new Outward(id,remarks,is_deleted,customer_id,outgoingreason,outgoingtime,expectedreturntime,actualreturntime,escorted,relation,escortedtel,auditstatus,auditperson,audittime));
        }

        return List.of();
    }
}
