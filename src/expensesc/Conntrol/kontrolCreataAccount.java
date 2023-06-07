/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expensesc.Conntrol;

import expensesc.Connecsi.ConnDBO;
import expensesc.Dao.UsersDao;
import expensesc.Dao.UsersDaoImpl;
import expensesc.Model.Luser;
import expensesc.View.FormCreateAccount;
import java.sql.Connection;
import javax.swing.JOptionPane;

/**
 *
 * @author Pitok
 */
public class kontrolCreataAccount {
    private Luser user = new Luser();
    private Connection cn = ConnDBO.getConnection();
    private FormCreateAccount fc;

    public kontrolCreataAccount(FormCreateAccount fc) {
        this.fc = fc;
    }
    
    public void createAccount()throws Exception{
        try {
            UsersDao dao = new UsersDaoImpl();
            user.setUsername(fc.getTxtUsername().getText());
            user.setNamaLengkap(fc.getTxtNama().getText());
            user.setAlamat(fc.getTxtAlamat().getText());
            user.setPass(fc.getTxtPass().getText());
            user.setSaldo(Integer.parseInt(fc.getTxtSaldoAwal().getText()));
            dao.createAccount(user);
            JOptionPane.showMessageDialog(fc, "Akun berhasil dibuat, silahkan Login!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(fc, "Username sudah ada.");
        }
    }
    public void cls(){
        fc.getTxtAlamat().setText("");
        fc.getTxtNama().setText("");
        fc.getTxtPass().setText("");
        fc.getTxtSaldoAwal().setText("0");
        fc.getTxtUsername().setText("");
    }
}
