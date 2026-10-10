package j930.util;

import java.sql.*;

public class JdbcUtils {

    // ============ 第 1 步：加载驱动（静态块，类加载时执行一次） ============
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // ============ 第 2 步：建立连接 ============
    public static Connection getConn() throws SQLException {
        // 注意：localhost:3306 冒号不能少，后面要跟上 /数据库名
        // 把 db_yiyang 换成你实际的数据库名
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/elderly_care?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf8",
                "root",
                "123456"
        );
        return conn;
    }

    // ============ 第 6 步：关闭资源（从内到外：ResultSet → Statement → Connection） ============
    public static void close(ResultSet rs, Statement stat, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (stat != null) {
            try {
                stat.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // ============ 重载：只关 Statement 和 Connection（没有 ResultSet 的场景） ============
    public static void close(Statement stat, Connection conn) {
        close(null, stat, conn);
    }
}