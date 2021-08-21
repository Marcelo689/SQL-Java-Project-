/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static java.util.logging.Level.SEVERE;
import java.util.logging.Logger;

/**
 *
 * @author Aluno
 */
public class ConnectionManager {
    
    final static String DRIVER = "com.mysql.cj.jdbc.Driver";
    final static String URL    = "jdbc:mysql://localhost:3306/JavaProdutos";
    final static String USER   = "root";
    final static String PASS   = "";
    
         
    public static Connection getConnection(){
        try{
            
            Class.forName(DRIVER);
          return DriverManager.getConnection(URL,USER,PASS);
        }catch(ClassNotFoundException | SQLException e){
            throw new RuntimeException("Error na conexão",e);
        }
        
    }
    
    public static void closeConnection(Connection con,PreparedStatement stmt)
    {
        try{
            if(stmt != null)
            {
                con.close();
            }
        }catch(SQLException e){
            throw new RuntimeException("Error ",e);
        }
    }
}
