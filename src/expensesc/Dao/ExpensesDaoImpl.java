/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expensesc.Dao;

import expensesc.Model.Expenses;
import expensesc.View.FormExpenses;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author pitokdf
 */
public class ExpensesDaoImpl implements ExpensesDao {
    private FormExpenses form;
    private Connection cn;
    private Date date;;
    private Expenses ex = new Expenses();

    public ExpensesDaoImpl(Connection cn) {
        this.cn = cn;
    }

    public void insert(Expenses expenses) throws Exception {
        String username = expensesc.Model.UsserSession.getUser();
        String sql = "INSERT INTO `expenses` (`expense_id`, username ,`date`, `waktu`, `category`, `jumlah`, `amount`, `description`, `total_harga`) VALUES (NULL, ?,CURRENT_DATE(), CURRENT_TIME(),?, ?, ?,?, jumlah*amount);";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, expenses.getCategory());
        ps.setString(4, String.valueOf(expenses.getAmount()));
        ps.setString(5, expenses.getDescription());
        ps.setString(3, expenses.getJumlah()+"");
        ps.executeUpdate();
        ps.close();
    }
    public void impor(Connection cn) throws Exception {
        try {
            String username = expensesc.Model.UsserSession.getUser();
            String sql = "SELECT expense_id, date, waktu, category, amount, description INTO OUTFILE '?/expenses.csv' FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY ':' ESCAPED BY '\\\\' LINES TERMINATED BY '\\r\\n' FROM Expenses where username = '"+username+"'";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.executeQuery();
            JOptionPane.showConfirmDialog(null, "berhasil");
        } catch (Exception e) {
            // TODO: handle exception
            JOptionPane.showMessageDialog(null, e);
        }

    }

    @Override
    public void search() throws SQLException {
        Statement statement = cn.createStatement();
        String sql = "SELECT * FROM Expenses WHERE ? LIKE '%?%'";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, form.getCboCari().getSelectedItem().toString());
        ps.setString(2, form.getTxtCari().getText());
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            Object[] data = {
                rs.getInt("expense_id")
            };
        }
        rs.close();
    }

    @Override
    public double calculateTotalAmount(Connection cn, FormExpenses form) throws SQLException {
        String sql = "SELECT SUM(amount) AS total_amount FROM Expenses";
        PreparedStatement ps = cn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        double totalAmount = 0.0;
        if (rs.next()) {
            totalAmount = rs.getDouble("total_amount");
        }
        ps.close();
        rs.close();
        return totalAmount;
    }

    public void update(Expenses expenses) throws SQLException {
        String username = expensesc.Model.UsserSession.getUser();
        String sqlUp = "UPDATE expenses SET category = ?, jumlah = ?, amount = ?, description = ?, total_harga = jumlah*amount WHERE expense_id =?";
        PreparedStatement ps = cn.prepareStatement(sqlUp);
        ps.setString(1, expenses.getCategory());
        ps.setString(3, String.valueOf(expenses.getAmount()));
        ps.setString(4, expenses.getDescription());
        ps.setString(2, String.valueOf(expenses.getJumlah()));
        ps.setString(5, String.valueOf(expenses.getId()));
        ps.executeUpdate();
        ps.close();
    }
    public void Ksaldo(Expenses expenses) throws Exception{
        String username = expensesc.Model.UsserSession.getUser();
        String sqlUp = "UPDATE users SET saldo=?-? where username = '"+username+"'";
        PreparedStatement ps = cn.prepareStatement(sqlUp);
        double i = currentAmount();
        long j = expenses.getAmount()*expenses.getJumlah();
        ps.setString(1, String.valueOf(i));
        ps.setLong(2, j);
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
