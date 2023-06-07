/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package expensesc.Dao;

import expensesc.Model.Luser;

/**
 *
 * @author Pitok
 */
public interface UsersDao {
    void Tsaldo(Luser luser) throws Exception;
    void createAccount(Luser user)throws Exception;
}
