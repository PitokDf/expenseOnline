/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expensesc.Dao;

import java.sql.Connection;


import expensesc.Model.Expenses;
import expensesc.View.FormExpenses;

/**
 *
 * @author pitokdf
 */
public interface ExpensesDao {
    void insert(Expenses expenses) throws Exception;

    void search() throws Exception;

    double calculateTotalAmount(Connection cn, FormExpenses form) throws Exception;

    void update(Expenses expenses) throws Exception;
    
    public void Ksaldo(Expenses expenses)throws Exception;
}