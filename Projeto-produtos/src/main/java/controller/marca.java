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
import java.util.ArrayList;
import static model.ConnectionManager.getConnection;

/**
 *
 * @author Aluno
 */
public class marca {
    public static ArrayList ListarMarca()
     {
         
         try{
         Connection con = getConnection();
         PreparedStatement stmt = null;
         stmt = con.prepareStatement("SELECT marca from marca");
         ResultSet result = stmt.executeQuery();
         ArrayList conteudo = new ArrayList();
         int contador =0;
         while(result.next())
         {
         conteudo.add(result.getString(1));
         }
         return conteudo;
         }catch(SQLException e)
         {
             throw new RuntimeException("Erro no listar marca",e);
         }
     }
     
     public static int ListarIdMarca(String marca)
     {
         
         try{
         Connection con = getConnection();
         PreparedStatement stmt = null;
         stmt = con.prepareStatement("SELECT id from marca WHERE marca = ?");
         stmt.setString(1, marca);
         ResultSet result = stmt.executeQuery();
         result.next();
         return result.getInt(1);
         }catch(SQLException e)
         {
             throw new RuntimeException("Erro no listar marca",e);
         }
     }
}
