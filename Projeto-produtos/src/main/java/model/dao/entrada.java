/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import static model.ConnectionManager.closeConnection;
import static model.ConnectionManager.getConnection;
import model.Historico;

/**
 *
 * @author Aluno
 */
public class entrada {
    static DateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
    
    
     public static void Entrada(Historico hst) throws ParseException
    {
        try{
            
            Connection con = getConnection();
            PreparedStatement stmt = null;
            stmt = con.prepareStatement("INSERT INTO historico(codigo,quantidade,data_hst,operacao) VALUES(?,?,?,1)");
            
            stmt.setString(1,hst.getCodigo());
            stmt.setInt(2,hst.getQuantidade());
            stmt.setDate(3,new java.sql.Date (formatDate.parse(hst.getData_hst()).getTime()));
            stmt.execute();
            
            closeConnection(con,stmt);
        }catch(SQLException e){
            throw new RuntimeException("Error",e);
        }
        
    }
}
