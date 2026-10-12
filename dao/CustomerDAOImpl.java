package j930.dao.impl;

import j930.entity.Customer;
import j930.util.JdbcUtils;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public int save(Customer customer) {
        String sql = "insert into customer(is_deleted, customer_name, customer_age, customer_sex, "
                + "idcard, room_no, building_no, checkin_date, expiration_date, contact_tel, bed_id, "
                + "psychosomatic_state, attention, birthday, height, weight, blood_type, filepath, "
                + "user_id, level_id, family_number) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConn();
            stat = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stat.setObject(1, customer.getIsDeleted() == null ? 0 : customer.getIsDeleted());
            stat.setString(2, customer.getCustomerName());
            stat.setObject(3, customer.getCustomerAge());
            stat.setObject(4, customer.getCustomerSex());
            stat.setString(5, customer.getIdcard());
            stat.setString(6, customer.getRoomNo());
            stat.setString(7, customer.getBuildingNo());
            stat.setObject(8, customer.getCheckinDate());
            stat.setObject(9, customer.getExpirationDate());
            stat.setString(10, customer.getContactTel());
            stat.setObject(11, customer.getBedId());
            stat.setString(12, customer.getPsychosomaticState());
            stat.setString(13, customer.getAttention());
            stat.setObject(14, customer.getBirthday());
            stat.setString(15, customer.getHeight());
            stat.setString(16, customer.getWeight());
            stat.setString(17, customer.getBloodType());
            stat.setString(18, customer.getFilepath());
            stat.setObject(19, customer.getUserId());
            stat.setObject(20, customer.getLevelId());
            stat.setString(21, customer.getFamilyNumber());
            int rows = stat.executeUpdate();
            if (rows > 0) {
                rs = stat.getGeneratedKeys();
                if (rs.next()) customer.setId(rs.getInt(1));
            }
            return rows;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.close(rs, stat, conn);
        }
    }

    @Override
    public int update(Customer customer) {
        String sql = "update customer set customer_name=?, customer_age=?, customer_sex=?, idcard=?, "
                + "room_no=?, building_no=?, checkin_date=?, expiration_date=?, contact_tel=?, bed_id=?, "
                + "psychosomatic_state=?, attention=?, birthday=?, height=?, weight=?, blood_type=?, "
                + "filepath=?, user_id=?, level_id=?, family_number=? where id=? and is_deleted=0";
        Connection conn = null;
        PreparedStatement stat = null;
        try {
            conn = JdbcUtils.getConn();
            stat = conn.prepareStatement(sql);
            stat.setString(1, customer.getCustomerName());
            stat.setObject(2, customer.getCustomerAge());
            stat.setObject(3, customer.getCustomerSex());
            stat.setString(4, customer.getIdcard());
            stat.setString(5, customer.getRoomNo());
            stat.setString(6, customer.getBuildingNo());
            stat.setObject(7, customer.getCheckinDate());
            stat.setObject(8, customer.getExpirationDate());
            stat.setString(9, customer.getContactTel());
            stat.setObject(10, customer.getBedId());
            stat.setString(11, customer.getPsychosomaticState());
            stat.setString(12, customer.getAttention());
            stat.setObject(13, customer.getBirthday());
            stat.setString(14, customer.getHeight());
            stat.setString(15, customer.getWeight());
            stat.setString(16, customer.getBloodType());
            stat.setString(17, customer.getFilepath());
            stat.setObject(18, customer.getUserId());
            stat.setObject(19, customer.getLevelId());
            stat.setString(20, customer.getFamilyNumber());
            stat.setInt(21, customer.getId());
            return stat.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.close(null, stat, conn);
        }
    }

    @Override
    public int delete(Integer id) {
        String sql = "update customer set is_deleted=1 where id=?";
        Connection conn = null;
        PreparedStatement stat = null;
        try {
            conn = JdbcUtils.getConn();
            stat = conn.prepareStatement(sql);
            stat.setInt(1, id);
            return stat.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.close(null, stat, conn);
        }
    }

    @Override
    public Customer findOne(Integer id) {
        return queryOne("select * from customer where id=? and is_deleted=0", id);
    }

    @Override
    public Customer findByPhone(String phone) {
        return queryOne("select * from customer where contact_tel=? and is_deleted=0", phone);
    }

    @Override
    public Customer findByIdCard(String idcard) {
        return queryOne("select * from customer where idcard=? and is_deleted=0", idcard);
    }

    @Override
    public Customer findByBedId(Integer bedId) {
        return queryOne("select * from customer where bed_id=? and is_deleted=0", bedId);
    }

    @Override
    public List<Customer> findByName(String name) {
        return queryList("select * from customer where customer_name like concat('%',?,'%') and is_deleted=0 order by id desc", name);
    }

    @Override
    public List<Customer> findByRoomNo(String roomNo) {
        return queryList("select * from customer where room_no=? and is_deleted=0 order by id desc", roomNo);
    }

    @Override
    public List<Customer> findByBuildingNo(String buildingNo) {
        return queryList("select * from customer where building_no=? and is_deleted=0 order by id desc", buildingNo);
    }

    @Override
    public List<Customer> findByLevelId(Integer levelId) {
        return queryList("select * from customer where level_id=? and is_deleted=0 order by id desc", levelId);
    }

    @Override
    public List<Customer> findByUserId(Integer userId) {
        return queryList("select * from customer where user_id=? and is_deleted=0 order by id desc", userId);
    }

    @Override
    public List<Customer> findAll() {
        return queryList("select * from customer where is_deleted=0 order by id desc");
    }

    @Override
    public List<Customer> findPage(int offset, int limit) {
        return queryList("select * from customer where is_deleted=0 order by id desc limit ?,?", offset, limit);
    }

    @Override
    public int count() {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConn();
            stat = conn.prepareStatement("select count(*) from customer where is_deleted=0");
            rs = stat.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.close(rs, stat, conn);
        }
        return 0;
    }

    @Override
    public List<Customer> findExpiringBefore(LocalDate date) {
        return queryList("select * from customer where expiration_date < ? and is_deleted=0 order by expiration_date asc", date);
    }

    // ========== 私有辅助：查单条 ==========

    private Customer queryOne(String sql, Object... params) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConn();
            stat = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                stat.setObject(i + 1, params[i]);
            }
            rs = stat.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.close(rs, stat, conn);
        }
        return null;
    }

    // ========== 私有辅助：查多条 ==========

    private List<Customer> queryList(String sql, Object... params) {
        List<Customer> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConn();
            stat = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                stat.setObject(i + 1, params[i]);
            }
            rs = stat.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.close(rs, stat, conn);
        }
        return list;
    }

    // ========== 私有辅助：结果集 → Customer ==========

    private Customer mapRow(ResultSet rs) throws SQLException {
        Customer c = new Customer();
        c.setId(rs.getInt("id"));
        c.setIsDeleted(rs.getObject("is_deleted", Integer.class));
        c.setCustomerName(rs.getString("customer_name"));
        c.setCustomerAge(rs.getObject("customer_age", Integer.class));
        c.setCustomerSex(rs.getObject("customer_sex", Integer.class));
        c.setIdcard(rs.getString("idcard"));
        c.setRoomNo(rs.getString("room_no"));
        c.setBuildingNo(rs.getString("building_no"));
        c.setCheckinDate(rs.getObject("checkin_date", LocalDate.class));
        c.setExpirationDate(rs.getObject("expiration_date", LocalDate.class));
        c.setContactTel(rs.getString("contact_tel"));
        c.setBedId(rs.getObject("bed_id", Integer.class));
        c.setPsychosomaticState(rs.getString("psychosomatic_state"));
        c.setAttention(rs.getString("attention"));
        c.setBirthday(rs.getObject("birthday", LocalDate.class));
        c.setHeight(rs.getString("height"));
        c.setWeight(rs.getString("weight"));
        c.setBloodType(rs.getString("blood_type"));
        c.setFilepath(rs.getString("filepath"));
        c.setUserId(rs.getObject("user_id", Integer.class));
        c.setLevelId(rs.getObject("level_id", Integer.class));
        c.setFamilyNumber(rs.getString("family_number"));
        return c;
    }
}