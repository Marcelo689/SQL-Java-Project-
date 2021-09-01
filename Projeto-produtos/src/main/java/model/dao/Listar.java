/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import static model.ConnectionManager.closeConnection;
import static model.ConnectionManager.getConnection;
import model.Produto;

/**
 *
 * @author Aluno
 */
public class Listar {
    public static int ListarSaldo(String codigo)
    {
        try{
            Connection con = getConnection();
            PreparedStatement stmt = null;
            int saldo = ExistOp2(codigo);
            if( saldo == 0){
                stmt = con.prepareStatement("SELECT(SELECT SUM(quantidade) from historico WHERE operacao = 1 AND codigo = ?)");
                stmt.setString(1,codigo);
               
            }else{
            stmt = con.prepareStatement("SELECT((SELECT SUM(quantidade) from historico WHERE operacao = 1 AND codigo = ? ) - (SELECT SUM(quantidade) from historico WHERE operacao = 2 AND codigo = ? ))");
            stmt.setString(1, codigo);
            stmt.setString(2, codigo);
            }
            ResultSet result =stmt.executeQuery();
            result.next();
            int tudo = result.getInt(1);
            
            closeConnection(con,stmt);
            return tudo;
        }catch(SQLException e )
        {
            throw new RuntimeException("Erro ",e );
        }
    
    }
    
    static DateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
    
    public static ArrayList ListarCodigos()
    {
        try{
            Connection con = getConnection();
            PreparedStatement stmt = null;
            stmt = con.prepareStatement("Select codigo from cadastro");
            
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
    
    public static ArrayList<Produto> ListarAll()
    {
        try{
            Connection con = getConnection();
            PreparedStatement stmt = null;
            stmt = con.prepareStatement("Select * from cadastro");
            
            ArrayList<Produto> codigos = new ArrayList<>();
            ResultSet result =stmt.executeQuery();
            
            while(result.next())
            {
                Produto p = new Produto();
                p.setId(result.getInt(1));
                p.setCodigo(result.getString(2));
                p.setDescricao(result.getString(3));
                p.setMarca(result.getInt(4));
                p.setPreco(result.getDouble(5));
                p.setCor(result.getInt(6));
                codigos.add(p);
            }
            closeConnection(con,stmt);
            
            return codigos;
        }catch(SQLException e){
            throw new RuntimeException("Error",e);
        }
        
    }
     public static ArrayList ListarDescricao(String codigo)
    {
        try{
            Connection con = getConnection();
            PreparedStatement stmt = null;
            stmt = con.prepareStatement("Select descricao from cadastro WHERE codigo = ?");
            stmt.setString(1, codigo);
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
     
    
    
    public static ArrayList ListarTudo()
    {
        try{
            Connection con = getConnection();
            PreparedStatement stmt = null;
            
            stmt = con.prepareStatement("Select codigo from historico ");
            ResultSet result =stmt.executeQuery();
            ArrayList tudo = new ArrayList();
            
            while(result.next()){
                tudo.add(result.getString(2));
                //tudo.add(result.getInt(3));
                //tudo.add(result.getDate(4));
                //tudo.add(result.getInt(5));
            }
            closeConnection(con,stmt);
            return tudo;
        }catch(SQLException e )
        {
            throw new RuntimeException("Erro ",e );
        }
    
    }
  
     public static int ExistOp2(String codigo)
     {
         try{
         Connection con = getConnection();
         PreparedStatement stmt = null;
         stmt= con.prepareStatement("SELECT codigo from historico WHERE operacao = 2 AND codigo = ?");
         stmt.setString(1, codigo);
         ResultSet result= stmt.executeQuery();
         
         if(result.next()){
             return 1;
         }else{
             return 0;
         }
         }catch(SQLException e)
         {
             throw new RuntimeException("Erro ao encontrar operacao" ,e);
         }
     }
}
