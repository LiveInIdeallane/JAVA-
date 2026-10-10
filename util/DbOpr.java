package util;


import java.lang.reflect.Field;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbOpr {

    //对Sql语句进行插入
    public static void CompleSQL(PreparedStatement ps, Object... params){
        for(int i=0; i<params.length;i++){
            try {
                ps.setObject(i+1,params[i]);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    //通用的查询语句

    public static List select(PreparedStatement ps, Object[] object, Class clazz) throws SQLException, InstantiationException, IllegalAccessException {
        List list = new ArrayList();
        CompleSQL(ps,object);
        ResultSet rs = ps.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        Class c=clazz;
        while (rs.next()) {
            Object obj= c.newInstance();
            Field[] fields = c.getDeclaredFields();
            for(int i=0 ; i<columnCount; i++){
                fields[i].setAccessible(true);
                fields[i].set(obj,rs.getObject(i+1));
            }
            list.add(obj);
        }
        
        return list;
    }

}
