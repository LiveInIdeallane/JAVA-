package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtils {

    //创建线程变量
    private static ThreadLocal<Connection> tl = new ThreadLocal<>();

    //加载驱动
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //创立连接
    public static Connection getConnection() throws SQLException {
        Connection conn = tl.get();
        if(conn==null){
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_service","root","123456");
            tl.set(conn);
        }
        return conn;
    }

    //关闭连接
    public static void close(){
        Connection conn = tl.get();
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally{
                tl.remove();
            }
        }
    }

}
