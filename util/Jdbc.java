package JdbcUtils;

import java.sql.*;
    public class Jdbc {

        private static ThreadLocal<Connection> tl = new ThreadLocal<>();

        static{
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        public static Connection getConnection() throws SQLException {
            Connection conn = tl.get();
            if(conn==null){
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo? serverTimezone=UTC","root","123456");
                        tl.set(conn);
            }
            return conn;
        }
        public static void close(){
            Connection conn = tl.get();
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } finally {
                    tl.remove();
                }
            }
        }
    }

