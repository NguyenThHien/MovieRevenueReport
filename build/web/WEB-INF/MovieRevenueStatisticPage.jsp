<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.StatMovie"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Box Office Revenue Report</title>
    <link rel="stylesheet" href="css/MovieRevenueStatisticPage.css">
</head>
<body>
<div class="container">

    <!-- Left Panel: Date Selection -->
    <div class="left-panel">
        <h2>Box Office Revenue Report</h2>
        <p class="note">Select a date range to generate the report 📅</p>

        <form action="MovieServlet" method="post">
            <label for="fromDate">From</label>
            <input type="date" id="fromDate" name="fromDate"
                   value="<%= request.getAttribute("fromDate") != null ? request.getAttribute("fromDate") : "" %>" required>

            <label for="toDate">To</label>
            <input type="date" id="toDate" name="toDate"
                   value="<%= request.getAttribute("toDate") != null ? request.getAttribute("toDate") : "" %>" required>

            <div class="btn-container">
                <button type="submit" class="btn-statistics">Statistics</button>
                <button type="button" class="btn-back" onclick="window.location.href='StatServlet'">Back</button>
            </div>
        </form>

        <% String error = (String) request.getAttribute("error"); %>
        <% if (error != null) { %>
            <p style="color:red;"><%= error %></p>
        <% } %>
    </div>

    <!-- Right Panel: Report Table -->
    <div class="right-panel">
        <%
            List<StatMovie> movieList = (List<StatMovie>) request.getAttribute("movieList");
            String fromDate = (String) request.getAttribute("fromDate");
            String toDate = (String) request.getAttribute("toDate");
        %>

        <h3>
            <% if (fromDate != null && toDate != null) { %>
                Box office report from <%= fromDate %> to <%= toDate %>
            <% } else { %>
                Please select a date range to view the report.
            <% } %>
        </h3>

        <table>
            <thead>
            <tr>
                <th>No.</th>
                <th>Movie ID</th>
                <th>Title</th>
                <th>Total Revenue (VND)</th>
            </tr>
            </thead>
            <tbody>
            <% if (movieList != null && !movieList.isEmpty()) {
                   int index = 1;
                   for (StatMovie m : movieList) { %>

                       <!-- Hidden form to submit detail -->
                       <form id="detailForm<%= m.getMovieID() %>" action="DetailServlet" method="post" style="display:none;">
                           <input type="hidden" name="movieID" value="<%= m.getMovieID() %>">
                           <input type="hidden" name="movieTitle" value="<%= m.getTitle() %>">
                           <input type="hidden" name="fromDate" value="<%= fromDate %>">
                           <input type="hidden" name="toDate" value="<%= toDate %>">
                       </form>

                       <!-- Table row -->
                       <tr style="cursor:pointer;" onclick="document.getElementById('detailForm<%= m.getMovieID() %>').submit();">
                           <td><%= index++ %></td>
                           <td><%= m.getMovieID() %></td>
                           <td><%= m.getTitle() %></td>
                           <td><%= String.format("%,.0f", m.getTotalRevenue()) %></td>
                       </tr>

            <%   }
               } else { %>
                   <tr>
                       <td colspan="4" style="text-align:center;">No data found for the selected date range.</td>
                   </tr>
            <% } %>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
