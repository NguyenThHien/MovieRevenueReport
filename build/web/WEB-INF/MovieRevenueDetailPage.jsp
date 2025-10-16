<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.StatShowTime"%>
<%@page import="javax.servlet.http.HttpSession"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Movie Revenue Details</title>
    <link rel="stylesheet" type="text/css" href="css/MovieRevenueDetailPage.css">
</head>
<body>
<div class="container">
    <div class="right-panel">
        <%
            List<StatShowTime> showtimeList = (List<StatShowTime>) session.getAttribute("showtimeList");
            String movieTitle = (String) session.getAttribute("movieTitle");
            String fromDate = (String) session.getAttribute("fromDate");
            String toDate = (String) session.getAttribute("toDate");
            Integer movieID = (Integer) session.getAttribute("movieID");
        %>

        <h2>Showtime Revenue Statistics for 
            <span class="highlight"><%= movieTitle != null ? movieTitle : "" %></span><br>
            <% if (fromDate != null && toDate != null) { %>
                (from <%= fromDate %> to <%= toDate %>)
            <% } %>
        </h2>

        <table id="showtimeTable">
            <thead>
                <tr>
                    <th>Showtime ID</th>
                    <th>Date & Time</th>
                    <th>Total Revenue (VND)</th>
                    <th>Screening Room</th>
                </tr>
            </thead>
            <tbody>
            <% if (showtimeList != null && !showtimeList.isEmpty()) {
                   for (StatShowTime st : showtimeList) { %>
                       <form id="detailForm<%= st.getShowTimeID() %>" action="STDetailServlet" method="post" style="display:none;">
                           <input type="hidden" name="ShowtimeID" value="<%= st.getShowTimeID() %>">
                           <input type="hidden" name="movieTitle" value="<%= movieTitle %>">
                           <input type="hidden" name="roomName" value="<%= st.getSc().getRoomName() %>">
                           <input type="hidden" name="screeningDate" value="<%= st.getScreeningDate() %>">
                           <input type="hidden" name="startTime" value="<%= st.getStartTime() %>">
                           <input type="hidden" name="endTime" value="<%= st.getEndTime() %>">
                           <input type="hidden" name="totalRevenue" value="<%= st.getTotalRevenue() %>">
                           <input type="hidden" name="fromDate" value="<%= fromDate %>">
                           <input type="hidden" name="toDate" value="<%= toDate %>">
                           <input type="hidden" name="movieID" value="<%= movieID %>">
                       </form>
                       <tr style="cursor:pointer;" onclick="document.getElementById('detailForm<%= st.getShowTimeID() %>').submit();">
                           <td><%= st.getShowTimeID() %></td>
                           <td>
                               <%= st.getScreeningDate() != null ? st.getScreeningDate() : "" %>
                               <% if (st.getStartTime() != null) { %> - <%= st.getStartTime() %><% } %>
                               <% if (st.getEndTime() != null) { %> – <%= st.getEndTime() %><% } %>
                           </td>
                           <td><%= String.format("%,.0f", st.getTotalRevenue()) %></td>
                           <td><%= st.getSc().getRoomName() %></td>
                       </tr>

            <%       }
               } else { %>
                   <tr>
                       <td colspan="4" style="text-align:center;">No data available in session.</td>
                   </tr>
            <% } %>
            </tbody>
        </table>
        <div class="btn-container">
            <form action="MovieServlet" method="post">
                <input type="hidden" name="back" value="true">
                <button type="submit" class="btn-back">Back</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
