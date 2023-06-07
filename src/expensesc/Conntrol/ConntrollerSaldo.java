/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expensesc.Conntrol;

import expensesc.Connecsi.ConnDBO;
import expensesc.Dao.UsersDao;
import expensesc.Dao.UsersDaoImpl;
import expensesc.Model.Luser;

import java.sql.*;
import expensesc.View.FormSaldo;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JOptionPane;

/**
 *
 * @author Pitok
 */
public class ConntrollerSaldo {
    private FormSaldo forms;
    private Luser user;
    private Connection cn = ConnDBO.getConnection();
    private String test;
    
    public ConntrollerSaldo(FormSaldo forms){
        this.forms = forms;
    }
    
    public void Tsaldo() throws Exception{
        try { 
        user = new Luser();
        UsersDao dao = new UsersDaoImpl();
        user.setSaldo(Integer.parseInt(forms.getTxtTambah().getText()));
        dao.Tsaldo(user);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(forms, "Silahkan masukkan nominal");
        }
    }
    public void Balance(){
        try {
            String username = expensesc.Model.UsserSession.getUser();
            String sql = "SELECT * from users WHERE username = '"+username+"'";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            double totalAmount = 0.0;
            if (rs.next()) {
                totalAmount = rs.getDouble("saldo");
            }
            NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
            String formattedTotal = format.format(totalAmount);
            forms.getLabelBalance().setText(formattedTotal);
            ps.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public void cls(){
        forms.getTxtTambah().setText("");
        forms.getTxtpass().setText("");
    }
}
