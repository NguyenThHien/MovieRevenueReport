<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.Invoice"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Showtime Details</title>
    <link rel="stylesheet" href="css/showtimeDetail.css">
</head>
<body>
<div class="container">
    <div class="right-panel">

        <h2>
           Revenue Details for This Showtime -
            <span class="highlight"><%= request.getAttribute("showtimeID") %></span>
        </h2>

        <div class="showtime-info">
            <p><strong>Movie title:</strong> <%= request.getAttribute("movieTitle") %></p>
            <p><strong>Screening Date:</strong> <%= request.getAttribute("screeningDate") %></p>
            <p><strong>Time:</strong> 
                <%= request.getAttribute("startTime") %> - <%= request.getAttribute("endTime") %>
            </p>
            <p><strong>Screening Room:</strong> <%= request.getAttribute("roomName") %></p>
            <p><strong>Total Revenue:</strong> 
                <%= String.format("%,.0f", (Double) request.getAttribute("totalRevenue")) %> VND
            </p>
        </div>

        <h3>Invoice List</h3>
        <table>
            <thead>
                <tr>
                    <th>No.</th>
                    <th>Invoice ID</th>
                    <th>Issued Date & Time</th>
                    <th>Total Amount (VND)</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Invoice> invoiceList = (List<Invoice>) request.getAttribute("invoiceList");
                    if (invoiceList != null && !invoiceList.isEmpty()) {
                        int index = 1;
                        for (Invoice inv : invoiceList) {
                %>
                            <tr>
                                <td><%= index++ %></td>
                                <td><%= inv.getInvoiceID() %></td>
                                <td>
                                    <%= inv.getIssueDate() != null && inv.getIssueTime() != null 
                                            ? inv.getIssueDate() + " " + inv.getIssueTime() : "" %>
                                </td>
                                <td><%= String.format("%,.0f", inv.getTotalAmount()) %></td>
                            </tr>
                <%
                        }
                    } else {
                %>
                        <tr>
                            <td colspan="4" style="text-align:center;">No invoices found for this showtime.</td>
                        </tr>
                <%
                    }
                %>
            </tbody>
        </table>

       
        <div class="btn-container">
            <form action="DetailServlet" method="post">
                <input type="hidden" name="back" value="true">
                <button type="submit" class="btn-back">Back</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
