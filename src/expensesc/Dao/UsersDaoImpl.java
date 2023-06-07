/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expensesc.Dao;

import expensesc.Connecsi.ConnDBO;
import expensesc.Model.Luser;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Pitok
 */
public class UsersDaoImpl implements UsersDao{
    private Connection cn = ConnDBO.getConnection();
    
    
    public void UsersDaoImpl(Connection cn){
        this.cn = cn;
    }
    
    public void createAccount(Luser user)throws Exception{
        String query = "INSERT INTO users (username, password, nama_lengkap, alamat, saldo) VALUES (?,MD5(?),?,?,?)";
        PreparedStatement ps = cn.prepareStatement(query);
        ps.setString(1, user.getUsername());
        ps.setString(2, user.getPass());
        ps.setString(3, user.getNamaLengkap());
        ps.setString(4, user.getAlamat());
        ps.setInt(5, user.getSaldo());
        ps.executeUpdate();
        ps.close();
    }
    public void Tsaldo(Luser user) throws Exception{
        String username = expensesc.Model.UsserSession.getUser();
        String sqlUp = "UPDATE users SET saldo=?+? where username = '"+username+"'";
        PreparedStatement ps = cn.prepareStatement(sqlUp);
        double i = currentAmount();
        ps.setString(1, String.valueOf(i));
        ps.setInt(2, user.getSaldo());
        ps.executeUpdate();
        ps.close();
    }
    public double currentAmount() throws SQLException{
        String username = expensesc.Model.UsserSession.getUser();
        String sql = "SELECT * from users WHERE username = '"+username+"'";
        PreparedStatement ps = cn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();            
        double totalAmount = 0.0;
        if (rs.next()) {
            totalAmount = rs.getDouble("saldo");
        }
        ps.close();
        rs.close();
        return totalAmount;
    }
}
