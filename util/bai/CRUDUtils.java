package JdbcUtils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CRUDUtils {
    public static boolean update(String sql,Object[] objects) throws SQLException {
        Connection conn = Jdbc.getConnection();
        PreparedStatement stat = conn.prepareStatement(sql);
        for (int i = 0; i <objects.length ; i++) {
            stat.setObject(i+1,objects[i]);
        }
        int row = stat.executeUpdate();
        return row>0?true:false;
    }
    public static List select(String sql, Object[] objects, Class clazz) throws SQLException {
        List list = new ArrayList();
        Connection conn = Jdbc.getConnection();
        PreparedStatement stat = conn.prepareStatement(sql);
        for (int i = 0; i <objects.length ; i++) {
            stat.setObject(i+1,objects[i]);
        }
        //获得结果集
        ResultSet rs = stat.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        //获得结果集有多少列
        System.out.println(rsmd.getColumnCount());
        //根据索引位置获得列的名称
        for (int i = 1; i <=rsmd.getColumnCount() ; i++) {
            System.out.println(rsmd.getColumnName(i));
        }
        //获取Food类的模板
        Class c = clazz;
        //循环一行
        try {
            while(rs.next()){
                //通过模板手动生成对象
                Object obj = c.newInstance();
                Field[] fields = c.getDeclaredFields();
                //将当前行的数据输出
                for (int i = 1; i <=rsmd.getColumnCount() ; i++) {
                    fields[i-1].setAccessible(true);
                    fields[i-1].set(obj,rs.getObject(rsmd.getColumnName(i)));
                }
                list.add(obj);
            }
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}

