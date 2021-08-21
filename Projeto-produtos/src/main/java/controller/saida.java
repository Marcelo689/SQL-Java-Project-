/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import static model.ConnectionManager.closeConnection;
import static model.ConnectionManager.getConnection;

/**
 *
 * @author Aluno
 */
public class saida {
    
    static DateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
    
    public static void Saida(String codigo,int quant,String data) throws ParseException
    {
        try{
            
            Connection con = getConnection();
            PreparedStatement stmt = null;
            stmt = con.prepareStatement("INSERT INTO historico(codigo,quantidade, data_hst,operacao ) VALUES(?,?,?,2)");
            
            
            stmt.setString(1,codigo);
            stmt.setInt(2,quant);
            stmt.setDate(3,new java.sql.Date(formatDate.parse(data).getTime()));
            stmt.execute();
            
            closeConnection(con,stmt);
        }catch(SQLException e){
            throw new RuntimeException("Error",e);
        }
        
    }
    
    public static ArrayList ListarCodigosEstoque()
    {
        try{
            Connection con = getConnection();
            PreparedStatement stmt = null;
            stmt = con.prepareStatement("Select codigo from historico WHERE quantidade > 0 AND operacao = 1");
            
            ArrayList codigos = new ArrayList();
            ResultSet result =stmt.executeQuery();

            while(result.next())
            {
                codigos.add(result.getString(1));
            }
            closeConnection(con,stmt);
            
            return codigos;
        }catch(SQLException e){
            throw new RuntimeException("Error",e);
        }
        
    }
}
