package dao;

import entity.Food;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.JdbcUtils;

public class FoodDAOImpl implements FoodDAO {
    @Override
    public boolean save(Food obj) throws SQLException {
        String sql = "insert into food(food_name,food_type,price,is_halal,food_img)values(?,?,?,?,?)";
        Connection conn = JdbcUtils.getConnection();
        PreparedStatement stat = conn.prepareStatement(sql);
        stat.setString(1,obj.getFoodName());
        stat.setString(2,obj.getFoodType());
        stat.setDouble(3,obj.getPrice());
        stat.setInt(4,obj.getIsHalal());
        stat.setString(5,obj.getFoodImg());
        int row = stat.executeUpdate();
        return row>0?true:false;
    }

    @Override
    public boolean update(Food obj) throws SQLException {
        String sql = "update food set food_name=?, food_type=?, price=?, is_halal=?, food_img=? where id=?";
        Connection conn = JdbcUtils.getConnection();
        PreparedStatement stat = conn.prepareStatement(sql);
        stat.setString(1,obj.getFoodName());
        stat.setString(2,obj.getFoodType());
        stat.setDouble(3,obj.getPrice());
        stat.setInt(4,obj.getIsHalal());
        stat.setString(5,obj.getFoodImg());
        stat.setInt(6,obj.getId());
        int row = stat.executeUpdate();
        return row>0?true:false;
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        String sql = "delete from food where id=?";
        Connection conn = JdbcUtils.getConnection();
        PreparedStatement stat = conn.prepareStatement(sql);
        stat.setInt(1,id);
        int row = stat.executeUpdate();
        return row>0?true:false;
    }

    @Override
    public Food findOne(Integer id) throws SQLException {
        String sql = "select * from food where id=?";
        Connection conn = JdbcUtils.getConnection();
        PreparedStatement stat = conn.prepareStatement(sql);
        stat.setInt(1,id);
        ResultSet rs = stat.executeQuery();
        if(rs.next()){
            Food food = new Food();
            food.setId(rs.getInt("id"));
            food.setFoodName(rs.getString("food_name"));
            food.setFoodType(rs.getString("food_type"));
            food.setPrice(rs.getDouble("price"));
            food.setIsHalal(rs.getInt("is_halal"));
            food.setFoodImg(rs.getString("food_img"));
            return food;
        }
        return null;
    }

    @Override
    public List<Food> findAll() throws SQLException {
        String sql = "select * from food";
        List<Food> foodList = new ArrayList<>();
        Connection conn = JdbcUtils.getConnection();
        PreparedStatement stat = conn.prepareStatement(sql);
        ResultSet rs = stat.executeQuery();
        while (rs.next()) {
            Food food = new Food();
            food.setId(rs.getInt("id"));
            food.setFoodName(rs.getString("food_name"));
            food.setFoodType(rs.getString("food_type"));
            food.setPrice(rs.getDouble("price"));
            food.setIsHalal(rs.getInt("is_halal"));
            food.setFoodImg(rs.getString("food_img"));
            foodList.add(food);
        }
        return foodList;
    }
}
