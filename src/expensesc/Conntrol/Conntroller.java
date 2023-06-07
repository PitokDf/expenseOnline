/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expensesc.Conntrol;

import java.text.NumberFormat;
import java.io.*;
import java.sql.*;
import java.sql.ResultSetMetaData;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import expensesc.Connecsi.ConnDBO;
import expensesc.Dao.ExpensesDao;
import expensesc.Dao.ExpensesDaoImpl;
import expensesc.Model.Expenses;
import expensesc.View.FormExpenses;
import java.util.Locale;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author pitokdf
 */
public class Conntroller {
    private FormExpenses form;
    private ExpensesDao expensesDao;
    Connection cn = 
            ConnDBO.getConnection();
    ResultSet resultSet;
    public Statement st;
    public PreparedStatement pr;

    public Conntroller(FormExpenses form) {
        this.form = form;
    }
    public Conntroller() {
        
    }
    public void impor() {
    try {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int result = fileChooser.showOpenDialog(form);

        if (result == JFileChooser.APPROVE_OPTION) {
            String username = expensesc.Model.UsserSession.getUser();
            String selectedPath = fileChooser.getSelectedFile().getAbsolutePath();
            String outputPath = selectedPath + File.separator + "data_"+username+".csv"; // membuat nama output file

            Connection cn = ConnDBO.getConnection();
            String sql = "SELECT * FROM expenses WHERE username = '"+username+"'";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Menulis judul kolom
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                writer.write(columnName);
                if (i != columnCount) {
                    writer.write(","); // Tambahkan pemisah kolom sesuai kebutuhan (misalnya, koma)
                }
            }
            writer.newLine();

            // Menulis data
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String expenseData = rs.getString(i);
                    writer.write(expenseData);
                    if (i != columnCount) {
                        writer.write(","); // Tambahkan pemisah kolom sesuai kebutuhan (misalnya, koma)
                    }
                }
                writer.newLine();
            }

            writer.close();
            cn.close();

            JOptionPane.showMessageDialog(form, "Data berhasil diimpor...");
        } else {
            JOptionPane.showMessageDialog(form, "Operasi dibatalkan.");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(form, "Terjadi kesalahan saat mengimpor data: " + e.getMessage());
    }
}


    public void insert() throws Exception {
        try {
            Expenses expenses = new Expenses();
            cn = 
                    ConnDBO.getConnection();
            ExpensesDao dao = new ExpensesDaoImpl(cn);
            expenses.setCategory(form.getCboCategory().getSelectedItem().toString());
            expenses.setAmount(Long.parseLong(form.getTxtAmount().getText()));
            expenses.setJumlah(Integer.parseInt(form.getTxtJumlah().getText()));
            expenses.setDescription(form.getTxtDescription().getText());
            dao.insert(expenses);
            JOptionPane.showMessageDialog(null, "Data telah ditambahkan...");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(form, "Pastikan Colom telah terisi!!."+e);
        }

    }
    
    public void tampiData() {
        try {
            Statement statement = cn.createStatement();
            String username = expensesc.Model.UsserSession.getUser();
            ResultSet resultSet = statement.executeQuery("SELECT * from expenses WHERE username = '"+username+"'");
            
            DefaultTableModel tableModel = (DefaultTableModel) form.getTblxpenses().getModel();
            tableModel.setRowCount(0);
            int no = 1;
            while (resultSet.next()) {
                Object[] data = {
                        resultSet.getString("expense_id"),
                        resultSet.getTime("waktu"),
                        resultSet.getDate("date"),
                        resultSet.getString("category"),
                        resultSet.getString("jumlah"),
                        formatCurrency(resultSet.getDouble("amount")),
                        formatCurrency(resultSet.getDouble("total_harga")),
                        resultSet.getString("description")
                };
                tableModel.addRow(data);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String formatCurrency(double amount) {
        java.text.NumberFormat formatter = java.text.NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        return formatter.format(amount);
    }
    
    public void update() throws Exception{
        Expenses expenses = new Expenses();
        if (form.getBtnInsert().getText() != ("Insert")) {
            cn = 
                    ConnDBO.getConnection();
            ExpensesDao dao = new ExpensesDaoImpl(cn);
            expenses.setCategory(form.getCboCategory().getSelectedItem().toString());
            expenses.setAmount(Integer.parseInt(form.getTxtAmount().getText()));
            expenses.setJumlah(Integer.parseInt(form.getTxtJumlah().getText()));
            expenses.setDescription(form.getTxtDescription().getText());
            expenses.setId(Integer.parseInt(
                            form.getTblxpenses().getValueAt(form.getTblxpenses().getSelectedRow(), 0).toString()));
            dao.update(expenses);
            JOptionPane.showMessageDialog(null, "Data telah dirubah...");
        }
    }
    
    public void TBelanja() {
        try {
            String column = form.getCboCari().getSelectedItem().toString();
            String searchTerm = form.getTxtCari().getText();
            String username = expensesc.Model.UsserSession.getUser();
            String sql = "SELECT SUM(total_harga) AS total_amount FROM expenses WHERE " + column + " LIKE '%" + searchTerm
                    + "%' AND username = '"+username+"'";
            PreparedStatement ps = cn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            double totalAmount = 0.0;
            if (rs.next()) {
                totalAmount = rs.getDouble("total_amount");
            }

            NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
            String formattedTotal = format.format(totalAmount);
            form.getLabelHarga().setText(formattedTotal);

            ps.close();
            rs.close();
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(form, e.getMessage());
        }
    }
    
    public void cariData() {
        try {
            String username = expensesc.Model.UsserSession.getUser();
            st = cn.createStatement();
            resultSet = st.executeQuery("SELECT * FROM expenses WHERE " + form.getCboCari().getSelectedItem().toString()
                    + " LIKE '%" + form.getTxtCari().getText() + "%' AND username = '"+username+"'");

            DefaultTableModel tableModel = (DefaultTableModel) form.getTblxpenses().getModel();
            tableModel.setRowCount(0);
            int no = 1;
            while (resultSet.next()) {
                Object[] data = {
                        resultSet.getString("expense_id"),
                        resultSet.getTime("waktu"),
                        resultSet.getDate("date"),
                        resultSet.getString("category"),
                        resultSet.getString("jumlah"),
                        formatCurrency(resultSet.getDouble("amount")),
                        formatCurrency(resultSet.getDouble("total_harga")),
                        resultSet.getString("description")
                };
                tableModel.addRow(data);
            }
        } catch (Exception e) {
            // TODO: handle exception
            JOptionPane.showMessageDialog(form, e);
        }

    }
    public void cls() {
        form.getCboCategory().setSelectedIndex(0);
        form.getTxtAmount().setText("");
        form.getTxtDescription().setText("");
        form.getTxtJumlah().setText("");
        form.getTxtCari().setText("");
        form.getBtnInsert().setText("Insert");
        form.getLabelerrror().setText("");
        String s = expensesc.Model.UsserSession.getUser();
    }
    
    public void delete() throws SQLException {
        if (!form.getBtnInsert().getText().equals("Ubah")) {
            JOptionPane.showMessageDialog(form, "Pilih data yang ingin dihapus");
        } else {
            int j = JOptionPane.showConfirmDialog(null, "Data akan dihapus, Lanjutkan?", "Konfirmasi",
                    JOptionPane.YES_NO_OPTION);
            if (j == 0) {
                try {
                    int i = Integer.parseInt(
                            form.getTblxpenses().getValueAt(form.getTblxpenses().getSelectedRow(), 0).toString());
                    st = cn.createStatement();
                    String username = expensesc.Model.UsserSession.getUser();
                    String sqldel = "DELETE FROM expenses where expense_id = " +
                            i + "";
                    st.executeUpdate(sqldel);
                } catch (Exception e) {
                    // TODO: handle exception
                    JOptionPane.showMessageDialog(form, e);
                }
            }

        }
    }
    
    public void getExpenses() {
        form.getCboCategory()
                .setSelectedItem(form.getTblxpenses().getValueAt(form.getTblxpenses().getSelectedRow(), 3).toString());
        form.getTxtAmount()
                .setText(form.getTblxpenses().getValueAt(form.getTblxpenses().getSelectedRow(), 5).toString()
                        .replace("Rp", "").replaceFirst(",.*", "").replace(".", ""));
        form.getTxtJumlah().setText(form.getTblxpenses().getValueAt(form.getTblxpenses().getSelectedRow(), 4).toString());
        form.getTxtDescription()
                .setText(form.getTblxpenses().getValueAt(form.getTblxpenses().getSelectedRow(), 7).toString());
    }
    
    public double Balance() throws Exception{
        String username = expensesc.Model.UsserSession.getUser();
        String sql = "SELECT * from users WHERE username = '"+username+"'";
        PreparedStatement ps = cn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        double totalAmount = 0.0;
        if (rs.next()) {
            totalAmount = rs.getDouble("saldo");
            ps.close();
            rs.close();
        }
        return totalAmount;
    }
            
    
    public void Ksaldo() {
        try { 
        Expenses ex = new Expenses();
        cn = 
                ConnDBO.getConnection();
        ExpensesDao dao = new ExpensesDaoImpl(cn);
        ex.setAmount(Integer.parseInt(form.getTxtAmount().getText()));
        ex.setJumlah(Integer.parseInt(form.getTxtJumlah().getText()));
        dao.Ksaldo(ex);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(form, e);
        }
    }
    
    public void iKsaldo() {
        try { 
        Expenses ex = new Expenses();
        cn = ConnDBO.getConnection();
        ExpensesDao dao = new ExpensesDaoImpl(cn);
        ex.setAmount(Integer.parseInt(form.getTxtAmount().getText())-Integer.parseInt(form.getTblxpenses().getValueAt(form.getTblxpenses().getSelectedRow(), 5).toString()
                        .replace("Rp", "").replaceFirst(",.*", "").replace(".", "")));
        ex.setJumlah(Integer.parseInt(form.getTxtJumlah().getText())-Integer.parseInt(form.getTblxpenses().getValueAt(form.getTblxpenses().getSelectedRow(), 4).toString()));
        dao.Ksaldo(ex);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(form, e);
        }
    }
}
