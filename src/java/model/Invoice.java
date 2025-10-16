package model;

import java.util.List;
import java.sql.Time;
import java.util.Date;

public class Invoice {
    private String invoiceID;
    private float totalAmount;
    private Date issueDate;
    private Time issueTime;
    private String paymentMethod;
    private Customer customer;     // Quan hệ với Customer
    private List<Ticket> tickets;  // Danh sách vé thuộc hóa đơn

    // Constructors
    public Invoice() {}

    public Invoice(String invoiceID, float totalAmount, Date issueDate, Time issueTime, String paymentMethod, Customer customer, List<Ticket> tickets) {
        this.invoiceID = invoiceID;
        this.totalAmount = totalAmount;
        this.issueDate = issueDate;
        this.issueTime = issueTime;
        this.paymentMethod = paymentMethod;
        this.customer = customer;
        this.tickets = tickets;
    }

    // Getters & Setters
    public String getInvoiceID() { return invoiceID; }
    public void setInvoiceID(String invoiceID) { this.invoiceID = invoiceID; }

    public float getTotalAmount() { return totalAmount; }
    public void setTotalAmount(float totalAmount) { this.totalAmount = totalAmount; }

    public Date getIssueDate() { return issueDate; }
    public void setIssueDate(Date issueDate) { this.issueDate = issueDate; }

    public Time getIssueTime() { return issueTime; }
    public void setIssueTime(Time issueTime) { this.issueTime = issueTime; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public List<Ticket> getTickets() { return tickets; }
    public void setTickets(List<Ticket> tickets) { this.tickets = tickets; }
}
