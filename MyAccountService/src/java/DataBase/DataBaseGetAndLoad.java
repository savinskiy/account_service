package DataBase;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/** Класс для работы с базами данных
 *
 * @author Savin
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Properties;

public class DataBaseGetAndLoad 
{
   // JDBC driver name and database URL
   private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   private static String DB_URL = "jdbc:mysql://localhost:3306/my_database";

   //  Database credentials
   private static String USER = "root";
   private static String PASS = "nbuser";
   
   /* Запросы к базе данных */
   private static String sql_getdata = "SELECT id, value FROM accounts;";
   private static String sql_senddata = "REPLACE INTO accounts VALUES (%d, %d);";

   /* Значения колонок базы данных */
   private static String id_column = "id";
   private static String value_column = "value";
   
   /* Инициализация базы данных значениями из файла со свойствами */
   public static void DataBaseInitialization ()
   {
       /* Имя файла со свойствами */
        String property_file = "DataBase/database_properties.properties";
        Properties props = new Properties();
        try 
        {
            /* Загрузка файла со свойствами */
            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(property_file));
            /* Инициализация значений базы данных */
            JDBC_DRIVER = props.getProperty("JDBC_DRIVER");
            DB_URL = props.getProperty("DB_URL");
            PASS = props.getProperty("PASS");
            USER = props.getProperty("USER");
            sql_getdata = props.getProperty("sql_getdata");
            sql_senddata = props.getProperty("sql_senddata");
            id_column = props.getProperty("id_column");
            value_column = props.getProperty("value_column");
        }
        /* Если не удалось загрузить файл со свойствами, то выводим сообщение
         * об ошибке и пользуемся настройками по умолчанию */
        catch (FileNotFoundException ex)
        {
            System.err.println("File not found: " + property_file);
            System.err.println("Continue with default settings.");
        } 
        catch (IOException ex) 
        {
            System.err.println("Can't get data from: " + property_file);
            System.err.println("Continue with default settings.");
        } 
    }
   
   /* Получение данных из базы */
   public static HashMap <Integer,Long> getDataFromDB ()
   {
       HashMap<Integer, Long> clients = new HashMap<Integer, Long>();
       Connection conn = null;
       Statement stmt = null;
       try 
       {
           // Register JDBC driver
           Class.forName(JDBC_DRIVER);

           // Open a connection
           System.out.println("Connecting to database...");
           conn = DriverManager.getConnection(DB_URL, USER, PASS);

           // Execute a query
           System.out.println("Creating statement...");
           stmt = conn.createStatement();
           ResultSet rs = stmt.executeQuery(sql_getdata);

           // Extract data from result set
           while (rs.next()) 
           {
               /* Получение id и value */
               Integer id = rs.getInt(id_column);
               Long value = rs.getLong(value_column);
               /* Сохранение их в класс для работы с клиентами */
               clients.put(id, value);
           }
           // Clean-up environment
           rs.close();
           stmt.close();
           conn.close();
       } catch (SQLException se) {
           //Handle errors for JDBC
           se.printStackTrace();
       } catch (ClassNotFoundException e) {
           //Handle errors for Class.forName
           System.err.println("Добавьте JDBC-драйвер в приложение.");
       } catch (Exception e) {
           //Handle errors for Class.forName
           e.printStackTrace();
       } 
       finally {
           //finally block used to close resources
           try {
               if (stmt != null) {
                   stmt.close();
               }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
       /* Получаем результат - данные о клиентах */
        return clients;
    }
   
   /* Загрузка данных в базу */
    public static void loadDataToDB(HashMap<Integer, Long> clients) 
    {
        Long value;
        Connection conn = null;
        Statement stmt = null;
        try 
        {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            for (Integer id : clients.keySet()) {
                /* Получаем значение value каждого клиента */
                value = clients.get(id);
                sql = String.format(sql_senddata, id, value);
                /* Отправляем данные в базу */
                stmt.executeUpdate(sql);
            }
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (ClassNotFoundException e) {
           //Handle errors for Class.forName
           System.err.println("Добавьте JDBC-драйвер в приложение.");
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
    }
}
