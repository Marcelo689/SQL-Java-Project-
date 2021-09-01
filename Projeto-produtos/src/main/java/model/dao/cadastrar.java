/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static model.ConnectionManager.closeConnection;
import static model.ConnectionManager.getConnection;
import model.Produto;

/**
 *
 * @author Aluno
 */
public class cadastrar {
    public static void Cadastrar(Produto p){
        
        try{
        Connection con = getConnection();
        
        PreparedStatement stmt = null;
        
        stmt = con.prepareStatement("INSERT INTO cadastro(codigo,descricao,marca,preco,cor) VALUES(?,?,?,?,?)");
        
        stmt.setString(1,p.getCodigo());
        stmt.setString(2,p.getDescricao());
        stmt.setInt(3,p.getMarca());
        stmt.setDouble(4,p.getPreco());
        stmt.setInt(5,p.getCor());
        
        stmt.execute();
        closeConnection(con,stmt);
        }catch(SQLException e)
        {
            throw new RuntimeException("Erro",e);
        }finally{
            
        }
    }
}
