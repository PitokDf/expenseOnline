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
                String databaseURL = "jdbc:mysql://sql12.freesqldatabase.com/sql12623212";
                String username = "sql12623212";
                String password = "MF7Hy4Eten";
                cn = DriverManager.getConnection(databaseURL, username, password);
                System.out.println("berhasil");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "periksa koneksi internet anda!!");
        }
        return cn;
    }
}
