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
import static model.ConnectionManager.getConnection;
import model.Usuario;

/**
 *
 * @author Aluno
 */
public class login {
    public static int Login(Usuario u)
    {
        try{
            Connection con = getConnection();
            PreparedStatement stmt = null;
            stmt = con.prepareStatement("SELECT id from usuario WHERE nome = ? AND senha = ?");
            stmt.setString(1, u.getNome());
            stmt.setString(2, u.getSenha());
            
            ResultSet result = stmt.executeQuery();
            boolean resulta =result.next();
            if(resulta)
            {
                return 1;
            }else
            {
                return 0;
            }
        }catch(SQLException e)
        {
            throw new RuntimeException("Erro no login ",e);
        }
    }
}
