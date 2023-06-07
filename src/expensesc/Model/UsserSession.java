/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expensesc.Model;

/**
 *
 * @author Pitok
 */
public class UsserSession {
    private static String user;
    private static String nama_lengkap;
    public static void setUser(String user){
        UsserSession.user = user;
    }
    public static String getUser(){
        return user;
    }

    public static String getNama_lengkap() {
        return nama_lengkap;
    }

    public static void setNama_lengkap(String nama_lengkap) {
        UsserSession.nama_lengkap = nama_lengkap;
    }
    
}
