/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.List;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import static model.ConnectionManager.closeConnection;
import static model.ConnectionManager.getConnection;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
/**
 *
 * @author Marcelo
 */
public class controller {
    static DateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
            
    
    public static void Cadastrar(String codigo,String descricao,int marca,double preco,int cor){
        
        try{
        Connection con = getConnection();
        
        PreparedStatement stmt = null;
        
        stmt = con.prepareStatement("INSERT INTO cadastro(codigo,descricao,marca,preco,cor) VALUES(?,?,?,?,?)");
        
        stmt.setString(1,codigo);
        stmt.setString(2,descricao);
        stmt.setInt(3,marca);
        stmt.setDouble(4,preco);
        stmt.setInt(5,cor);
        
        stmt.execute();
        closeConnection(con,stmt);
        }catch(SQLException e)
        {
            throw new RuntimeException("Erro",e);
        }finally{
            
        }
    }
    
    public static void Entrada(String codigo,int quant,String data) throws ParseException
    {
        try{
            
            Connection con = getConnection();
            PreparedStatement stmt = null;
            stmt = con.prepareStatement("INSERT INTO historico(codigo,quantidade,data_hst,operacao) VALUES(?,?,?,1)");
            
            stmt.setString(1,codigo);
            stmt.setInt(2,quant);
            stmt.setDate(3,new java.sql.Date (formatDate.parse(data).getTime()));
            stmt.execute();
            
            closeConnection(con,stmt);
        }catch(SQLException e){
            throw new RuntimeException("Error",e);
        }
        
    }
    
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
         stmt= con.prepareStatement("SELECT codigo from historico WHERE operacao = 2");
         ResultSet result= stmt.executeQuery();
         result.next();
         if(result.getString(1).equals(codigo)){
             return 1;
         }else{
             return 0;
         }
         }catch(SQLException e)
         {
             throw new RuntimeException("Erro ao encontrar operacao" ,e);
         }
     }
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
     
     
}
