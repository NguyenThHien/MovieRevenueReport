<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- Get attributes --%>
<c:set var="manager" value="${sessionScope.loggedManager}" />
<c:set var="showtimeList" value="${requestScope.showtimeList}" />
<c:set var="movieTitle" value="${requestScope.movieTitle}" />
<c:set var="fromDate" value="${requestScope.fromDate}" />
<c:set var="toDate" value="${requestScope.toDate}" />
<c:set var="movieID" value="${requestScope.movieID}" />
<c:set var="currentPage" value="${not empty requestScope.currentPage ? requestScope.currentPage : 1}" />
<c:set var="totalPages" value="${not empty requestScope.totalPages ? requestScope.totalPages : 1}" />
<c:set var="recordsPerPage" value="${not empty requestScope.recordsPerPage ? requestScope.recordsPerPage : 3}" /> <%-- Match Servlet --%>
<c:set var="error" value="${requestScope.error}" />

<%-- Lấy các trang cần thiết cho nút Back --%>
<c:set var="showtimeListPageForNext" value="${requestScope.showtimeListPage}" /> <%-- Trang hiện tại, gửi đi cho bước sau --%>
<c:set var="moviePageForBack" value="${requestScope.moviePage}" /> <%-- Trang của Movie Report, dùng cho nút Back ở đây --%>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Movie Revenue Details</title>
    <link rel="stylesheet" href="css/showtimeDetail.css"> <%-- Dùng context path --%>
</head>
<body>
    <header class="main-header">
        <ul class="nav-links">
             <c:if test="${not empty manager}"><li><a href="managerHome.jsp">Admin page</a></li></c:if>
        </ul>
        <ul class="user-actions">
            <li><span class="user-info">Hello, <c:out value="${manager.fullName}"/></span></li>
            <li><form action="LogoutServlet" method="post"><button type="submit" class="logout-button">Logout</button></form></li>
        </ul>
    </header>

    <div class="container">
         <c:if test="${not empty error}">
            <p style="color: red; border: 1px solid red; padding: 10px; margin-bottom: 15px;">
                <strong>Error:</strong> <c:out value="${error}"/>
            </p>
        </c:if>

        <h2>
            Showtime Revenue Statistics for <span class="highlight"><c:out value="${movieTitle}"/></span>
            <c:if test="${not empty fromDate and not empty toDate}">
                <small>(from <c:out value="${fromDate}"/> to <c:out value="${toDate}"/>)</small>
            </c:if>
        </h2>

        <%-- Form ẩn cho phân trang CỦA TRANG NÀY --%>
        <form id="paginationForm" action="DetailServlet" method="post" style="display: none;">
            <input type="hidden" id="pageInput" name="page" value="${currentPage}">
            <input type="hidden" name="movieID" value="${movieID}">
            <input type="hidden" name="movieTitle" value="<c:out value="${movieTitle}"/>">
            <input type="hidden" name="fromDate" value="<c:out value="${fromDate}"/>">
            <input type="hidden" name="toDate" value="<c:out value="${toDate}"/>">
            <%-- Cần gửi lại moviePage khi phân trang --%>
            <input type="hidden" name="moviePage" value="${moviePageForBack}"> 
        </form>

        <table id="showtimeTable">
             <thead>
                <tr>
                    <th>Showtime ID</th>
                    <th>Date & Time</th>
                    <th>Screening Room</th>
                    <th>Total Tickets</th>
                    <th>Base Price (VND)</th>
                    <th>Total Revenue (VND)</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty showtimeList}">
                        <c:forEach var="st" items="${showtimeList}">
                            <%-- Form ẩn gửi sang STDetailServlet --%>
                            <form id="detailForm${st.showTimeID}" action="STDetailServlet" method="post" style="display:none;">
                                <input type="hidden" name="ShowtimeID" value="${st.showTimeID}">
                                <input type="hidden" name="movieTitle" value="<c:out value="${movieTitle}"/>">
                                <input type="hidden" name="roomName" value="<c:out value="${st.sc.roomName}"/>">
                                <%-- Gửi Date/Time dưới dạng String mà STDetailServlet có thể parse --%>
                                <input type="hidden" name="screeningDate" value="<fmt:formatDate value="${st.screeningDate}" pattern="yyyy-MM-dd"/>">
                                <input type="hidden" name="startTime" value="<fmt:formatDate value="${st.startTime}" pattern="HH:mm:ss"/>">
                                <input type="hidden" name="endTime" value="<fmt:formatDate value="${st.endTime}" pattern="HH:mm:ss"/>">
                                <input type="hidden" name="totalRevenue" value="${st.totalRevenue}">
                                <input type="hidden" name="fromDate" value="<c:out value="${fromDate}"/>">
                                <input type="hidden" name="toDate" value="<c:out value="${toDate}"/>">
                                <input type="hidden" name="movieID" value="${movieID}">
                                <%-- Gửi trang hiện tại của Showtime List với tên là "showtimeListPage" --%>
                                <input type="hidden" name="showtimeListPage" value="${currentPage}"> 
                                <%-- Gửi cả trang của Movie Report đi nữa --%>
                                <input type="hidden" name="moviePage" value="${moviePageForBack}"> 
                            </form>
                            
                            <tr data-form-id="detailForm${st.showTimeID}">
                                <td>${st.showTimeID}</td>
                                <td>
                                    <fmt:formatDate value="${st.screeningDate}" pattern="yyyy-MM-dd" /> 
                                    at <fmt:formatDate value="${st.startTime}" pattern="HH:mm" /> 
                                </td>
                                <td><c:out value="${st.sc.roomName}"/></td>
                                <td><c:out value="${st.totalTicket}"/></td>
                                <td><fmt:formatNumber value="${st.basePrice}" type="number" groupingUsed="true" maxFractionDigits="0"/></td>
                                <td><fmt:formatNumber value="${st.totalRevenue}" type="number" groupingUsed="true" maxFractionDigits="0"/></td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="6" class="no-data">No showtimes found for this movie in the selected date range.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
        
        <%-- Phân trang cho DANH SÁCH SUẤT CHIẾU --%>
        <c:if test="${totalPages > 1}">
            <div class="pagination">
                <a data-page="${currentPage - 1}" class="${currentPage <= 1 ? 'disabled' : ''}">&laquo; Prev</a>
                <c:set var="startPage" value="${currentPage - 2 > 0 ? currentPage - 2 : 1}"/>
                <c:set var="endPage" value="${startPage + 4 <= totalPages ? startPage + 4 : totalPages}"/>
                <c:if test="${endPage - startPage < 4}">
                    <c:set var="startPage" value="${endPage - 4 > 0 ? endPage - 4 : 1}"/>
                </c:if>
                <c:forEach var="i" begin="${startPage}" end="${endPage}">
                    <a data-page="${i}" class="${i == currentPage ? 'active' : ''}">${i}</a>
                </c:forEach>
                <a data-page="${currentPage + 1}" class="${currentPage >= totalPages ? 'disabled' : ''}">Next &raquo;</a>
            </div>
        </c:if>
        
        <div class="btn-container">
            <%-- Nút Back về Movie Report --%>
            <form action="MovieServlet" method="post">
                <input type="hidden" name="fromDate" value="<c:out value="${fromDate}"/>">
                <input type="hidden" name="toDate" value="<c:out value="${toDate}"/>">
                <%-- Gửi lại trang của Movie Report với tên là "page" --%>
                <input type="hidden" name="page" value="${moviePageForBack}"> 
                <button type="submit" class="btn-back">Back to Movie Report</button>
            </form>
        </div>
    </div>

    <script src="js/showtimeDetail.js" defer></script> <%-- Đảm bảo JS đúng --%>
</body>
</html>