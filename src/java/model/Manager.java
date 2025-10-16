package model;
import java.util.Date;
import model.Member;


public class Manager extends Member{
    private String manager_id;
    private Date dateHired;
    private String position;

    public Manager(String manager_id, Date dateHired, String position) {
        this.manager_id = manager_id;
        this.dateHired = dateHired;
        this.position = position;
    }

    public Manager(int memberID, String fullName, String username, String password, String email, String phoneNumber, String role, Date dateOfBirth, String address, String gender) {
        super(memberID, fullName, username, password, email, phoneNumber, role, dateOfBirth, address, gender);
        
    }
    
    public Manager(String manager_id, Date dateHired, String position, int memberID, String fullName, String username, String password, String email, String phoneNumber, String role, Date dateOfBirth, String address, String gender) {
        super(memberID, fullName, username, password, email, phoneNumber, role, dateOfBirth, address, gender);
        this.manager_id = manager_id;
        this.dateHired = dateHired;
        this.position = position;
    }
    
    
    
}
