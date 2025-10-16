/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author HP
 */
public class Customer extends Member{

    public Customer() {
    }

    public Customer(int memberID, String fullName, String username, String password, String email, String phoneNumber, String role, Date dateOfBirth, String address, String gender) {
        super(memberID, fullName, username, password, email, phoneNumber, role, dateOfBirth, address, gender);
    }
    
    
}
