package dao;

import entity.Customer;
import util.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public boolean saveCustomer(Customer customer) throws SQLException {
        String sql = "insert into customer(id,is_deleted,customer_name,customer_age,customer_sex,idcard,room_no,building_no,checkin_date,expiration_date,contact_tel,bed_id,psychosomatic_state,attention,birthday,height,weight,blood_type,filepath,user_id,level_id,family_number) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        Connection conn = JdbcUtils.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, customer.getId());
        ps.setInt(2,customer.getIs_deleted());
        ps.setString(3,customer.getCustomer_name());
        ps.setInt(4,customer.getCustomer_age());
        ps.setInt(5,customer.getCustomer_sex());
        ps.setString(6,customer.getIdcard());
        ps.setString(7,customer.getRoom_no());
        ps.setString(8,customer.getBuilding_no());
        ps.setDate(9, (Date) customer.getCheckin_date());
        ps.setDate(10,(Date) customer.getExpiration_date());
        ps.setString(11,customer.getContact_tel());
        ps.setInt(12,customer.getBed_id());
        ps.setString(13,customer.getPsychosomatic_state());
        ps.setString(14,customer.getAttention());
        ps.setDate(15,(Date) customer.getBirthday());
        ps.setString(16,customer.getHeight());
        ps.setString(17,customer.getWeight());
        ps.setString(18,customer.getBlood_type());
        ps.setString(19,customer.getFilepath());
        ps.setInt(20,customer.getUser_id());
        ps.setInt(21,customer.getLevel_id());
        ps.setString(22,customer.getFamily_number());

        int row = ps.executeUpdate();
        return row>0?true:false;

    }

    @Override
    public boolean updateCustomer(Customer customer) throws SQLException {
        String sql = "UPDATE customer SET is_deleted = ?, customer_name = ?, customer_age = ?, customer_sex = ?, idcard = ?, room_no = ?, building_no = ?, checkin_date = ?, expiration_date = ?, contact_tel = ?, bed_id = ?, psychosomatic_state = ?, attention = ?, birthday = ?, height = ?, weight = ?, blood_type = ?, filepath = ?, user_id = ?, level_id = ?, family_member = ? where id = ?";
        Connection conn = JdbcUtils.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1,customer.getIs_deleted());
        ps.setString(2,customer.getCustomer_name());
        ps.setInt(3,customer.getCustomer_age());
        ps.setInt(4,customer.getCustomer_sex());
        ps.setString(5,customer.getIdcard());
        ps.setString(6,customer.getRoom_no());
        ps.setString(7,customer.getBuilding_no());
        ps.setDate(8, (Date) customer.getCheckin_date());
        ps.setDate(9,(Date) customer.getExpiration_date());
        ps.setString(10,customer.getContact_tel());
        ps.setInt(11,customer.getBed_id());
        ps.setString(12,customer.getPsychosomatic_state());
        ps.setString(13,customer.getAttention());
        ps.setDate(14,(Date) customer.getBirthday());
        ps.setString(15,customer.getHeight());
        ps.setString(16,customer.getWeight());
        ps.setString(17,customer.getBlood_type());
        ps.setString(18,customer.getFilepath());
        ps.setInt(19,customer.getUser_id());
        ps.setInt(20,customer.getLevel_id());
        ps.setString(21,customer.getFamily_number());
        ps.setInt(22, customer.getId());

        int row = ps.executeUpdate();
        return row>0?true:false;
    }



    @Override
    public boolean deleteCustomer(Integer id) throws SQLException {
        String sql = "DELETE FROM customer WHERE id = ?";
        Connection conn = JdbcUtils.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1,id);

        int row = ps.executeUpdate();
        return row>0?true:false;
    }

    @Override
    public Customer findCustomer(Integer id) throws SQLException {
        String sql = "SELECT * FROM customer WHERE id = ?";
        Connection conn = JdbcUtils.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1,id);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            Customer customer = new Customer();
            customer.setId(rs.getInt(1));
            customer.setIs_deleted(rs.getInt(2));
            customer.setCustomer_name(rs.getString(3));
            customer.setCustomer_age(rs.getInt(4));
            customer.setCustomer_sex(rs.getInt(5));
            customer.setIdcard(rs.getString(6));
            customer.setRoom_no(rs.getString(7));
            customer.setBuilding_no(rs.getString(8));
            customer.setCheckin_date(rs.getDate(9));
            customer.setExpiration_date(rs.getDate(10));
            customer.setContact_tel(rs.getString(11));
            customer.setBed_id(rs.getInt(12));
            customer.setPsychosomatic_state(rs.getString(13));
            customer.setAttention(rs.getString(14));
            customer.setBirthday(rs.getDate(15));
            customer.setHeight(rs.getString(16));
            customer.setWeight(rs.getString(17));
            customer.setBlood_type(rs.getString(18));
            customer.setFilepath(rs.getString(19));
            customer.setUser_id(rs.getInt(20));
            customer.setLevel_id(rs.getInt(21));
            customer.setFamily_number(rs.getString(22));
            return customer;
        }
        return null;
    }

    @Override
    public List<Customer> findAllCustomers() throws SQLException {
        String sql = "SELECT * FROM customer";
        Connection conn = JdbcUtils.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Customer> customers = new ArrayList<>();
        while (rs.next()) {
            Customer customer = new Customer();
            customer.setId(rs.getInt(1));
            customer.setIs_deleted(rs.getInt(2));
            customer.setCustomer_name(rs.getString(3));
            customer.setCustomer_age(rs.getInt(4));
            customer.setCustomer_sex(rs.getInt(5));
            customer.setIdcard(rs.getString(6));
            customer.setRoom_no(rs.getString(7));
            customer.setBuilding_no(rs.getString(8));
            customer.setCheckin_date(rs.getDate(9));
            customer.setExpiration_date(rs.getDate(10));
            customer.setContact_tel(rs.getString(11));
            customer.setBed_id(rs.getInt(12));
            customer.setPsychosomatic_state(rs.getString(13));
            customer.setAttention(rs.getString(14));
            customer.setBirthday(rs.getDate(15));
            customer.setHeight(rs.getString(16));
            customer.setWeight(rs.getString(17));
            customer.setBlood_type(rs.getString(18));
            customer.setFilepath(rs.getString(19));
            customer.setUser_id(rs.getInt(20));
            customer.setLevel_id(rs.getInt(21));
            customer.setFamily_number(rs.getString(22));
            customers.add(customer);

        }
        return customers;
    }

    @Override
    public List<Customer> search(String customerName, String contact_tel, String idcard, String building_no) throws SQLException, InstantiationException, IllegalAccessException {
        String sql = "SELECT * FROM customer WHERE 1=1 ";
        Connection conn = JdbcUtils.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        List<Object> list = new ArrayList<>();


        //sql语句拼接
        if(customerName!=null&&!"".equals(customerName)){
            sql+=" AND customer_name like ? ";
            list.add("%"+customerName+"%");
        }if(contact_tel!=null&&!"".equals(contact_tel)){
            sql+=" AND contact_tel like ? ";
            list.add("%"+contact_tel+"%");
        }if(idcard!=null&&!"".equals(idcard)){
            sql+=" AND idcard like ? ";
            list.add("%"+idcard+"%");
        }if(building_no!=null&&!"".equals(building_no)){
            sql+=" AND building_no like ? ";
            list.add("%"+building_no+"%");
        }
        Object[] params = list.toArray();

        return util.DbOpr.select(ps,params,Customer.class);
    }
}
