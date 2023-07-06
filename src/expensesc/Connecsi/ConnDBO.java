/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expensesc.Connecsi;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Pitok
 */
public class ConnDBO {
    private static Connection cn;

    public static Connection getConnection() {
        try {
            if (cn == null) {
                String databaseURL = "jdbc:mysql://www.db4free.net/xpenses";
                String username = "pitokyaa";
                String password = "5012a87c";
                cn = DriverManager.getConnection(databaseURL, username, password);
                System.out.println("berhasil");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "periksa koneksi internet anda!!"+e);
        }
        return cn;
    }
}
