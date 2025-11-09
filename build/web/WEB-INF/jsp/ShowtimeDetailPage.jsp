<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="manager" value="${sessionScope.loggedManager}" />
<c:set var="invoiceList" value="${requestScope.invoiceList}" />
<c:set var="movieTitle" value="${requestScope.movieTitle}" />
<c:set var="showtimeID" value="${requestScope.showtimeID}" />
<c:set var="screeningDate" value="${requestScope.screeningDate}" />
<c:set var="startTime" value="${requestScope.startTime}" />
<c:set var="endTime" value="${requestScope.endTime}" />
<c:set var="roomName" value="${requestScope.roomName}" />
<c:set var="totalRevenue" value="${requestScope.totalRevenue}" />
<c:set var="fromDate" value="${requestScope.fromDate}" />
<c:set var="toDate" value="${requestScope.toDate}" />
<c:set var="movieID" value="${requestScope.movieID}" />
<c:set var="movieReportPage" value="${requestScope.moviePage}" /> <%-- Trang của Movie Report --%>
<c:set var="showtimeListPageForBack" value="${requestScope.showtimeListPageForBack}" /> <%-- Trang của Showtime List --%>
<c:set var="currentInvoicePage" value="${not empty requestScope.currentInvoicePage ? requestScope.currentInvoicePage : 1}" />
<c:set var="totalInvoicePages" value="${not empty requestScope.totalInvoicePages ? requestScope.totalInvoicePages : 1}" />
<c:set var="recordsPerInvoicePage" value="${not empty requestScope.recordsPerInvoicePage ? requestScope.recordsPerInvoicePage : 5}" />
<c:set var="totalRevenueShowTime" value="${requestScope.showTimeRevenue}" />
<c:set var="error" value="${requestScope.error}" />

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Showtime Details - ID ${showtimeID}</title>
        <link rel="stylesheet" href="css/Invoice.css"> 
    </head>
    <body>
        <header class="main-header">
            <ul class="nav-links">
                <c:if test="${not empty manager}"><li><a href="managerHome.jsp">CineMan Dashboard: Showtime Detail Revenue Statistic</a></li></c:if>
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
                Detailed Revenue For Showtime - <span class="highlight">${showtimeID}</span>
            </h2>

            <div class="showtime-info">
                <p><strong>Movie Title:</strong> <c:out value="${movieTitle}"/></p>
                <p><strong>Screening Date:</strong> <fmt:formatDate value="${screeningDate}" pattern="yyyy-MM-dd" /></p>
                <p><strong>Time:</strong>
                    <fmt:formatDate value="${startTime}" pattern="HH:mm" /> - <fmt:formatDate value="${endTime}" pattern="HH:mm" />
                </p>
                <p><strong>Screening Room:</strong> <c:out value="${roomName}"/></p>
                <p><strong>Total Showtime Revenue:</strong>
                    <fmt:formatNumber value="${totalRevenue}" type="number" groupingUsed="true" maxFractionDigits="0"/> VND
                </p>
            </div>

            <h3>Invoice List</h3>

            <%-- Form ẩn cho phân trang HÓA ĐƠN --%>
            <form id="invoicePaginationForm" action="STDetailServlet" method="post" style="display: none;">
                <input type="hidden" id="invoicePageInput" name="page" value="${currentInvoicePage}"> <%-- chuyen trang --%>
                <input type="hidden" name="ShowtimeID" value="${showtimeID}">
                <input type="hidden" name="movieTitle" value="<c:out value="${movieTitle}"/>">
                <input type="hidden" name="roomName" value="<c:out value="${roomName}"/>">
                <input type="hidden" name="screeningDate" value="<fmt:formatDate value="${screeningDate}" pattern="yyyy-MM-dd"/>">
                <input type="hidden" name="startTime" value="<fmt:formatDate value="${startTime}" pattern="HH:mm:ss"/>">
                <input type="hidden" name="endTime" value="<fmt:formatDate value="${endTime}" pattern="HH:mm:ss"/>">
                <input type="hidden" name="totalRevenue" value="${totalRevenue}">
                <input type="hidden" name="fromDate" value="<c:out value="${fromDate}"/>">
                <input type="hidden" name="toDate" value="<c:out value="${toDate}"/>">
                <input type="hidden" name="movieID" value="${movieID}">
                <%-- Gửi lại các trang trước đó --%>
                <input type="hidden" name="moviePage" value="${movieReportPage}">
                <input 
                    <input type="hidden" name="showtimeListPage" value="${showtimeListPageForBack}"> 
            </form>

            <table>
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Invoice ID</th>
                        <th>Issued Date & Time</th>
                        <th>Payment Method</th>
                        <th>Total Amount (VND)</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty invoiceList}">
                            <c:forEach var="inv" items="${invoiceList}" varStatus="loop">
                                <tr>
                                    <td>${(currentInvoicePage - 1) * recordsPerInvoicePage + loop.count}</td>
                                    <td><c:out value="${inv.invoiceID}"/></td>
                                    <td>
                                        <fmt:formatDate value="${inv.issueDate}" pattern="yyyy-MM-dd" />
                                        <fmt:formatDate value="${inv.issueTime}" pattern="HH:mm:ss" />
                                    </td>
                                    <td><c:out value="${inv.paymentMethod}"/></td>
                                    <td><fmt:formatNumber value="${inv.totalAmount}" type="number" groupingUsed="true" maxFractionDigits="0"/></td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="5" class="no-data">No invoices found for this showtime.</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>


            <c:if test="${totalInvoicePages > 1}">
                <div class="pagination">
                    <a data-page="${currentInvoicePage - 1}" class="${currentInvoicePage <= 1 ? 'disabled' : ''}">&laquo; Prev</a>
                    <c:set var="startPage" value="${currentInvoicePage - 2 > 0 ? currentInvoicePage - 2 : 1}"/>
                    <c:set var="endPage" value="${startPage + 4 <= totalInvoicePages ? startPage + 4 : totalInvoicePages}"/>
                    <c:if test="${endPage - startPage < 4}">
                        <c:set var="startPage" value="${endPage - 4 > 0 ? endPage - 4 : 1}"/>
                    </c:if>
                    <c:forEach var="i" begin="${startPage}" end="${endPage}">
                        <a data-page="${i}" class="${i == currentInvoicePage ? 'active' : ''}">${i}</a>
                    </c:forEach>
                    <a data-page="${currentInvoicePage + 1}" class="${currentInvoicePage >= totalInvoicePages ? 'disabled' : ''}">Next &raquo;</a>
                </div>
            </c:if>

            <div class="btn-container">

                <form action="DetailServlet" method="post">
                    <%-- Back--%>
                    <input type="hidden" name="movieID" value="${movieID}">
                    <input type="hidden" name="movieTitle" value="<c:out value="${movieTitle}"/>">
                    <input type="hidden" name="fromDate" value="<c:out value="${fromDate}"/>">
                    <input type="hidden" name="toDate" value="<c:out value="${toDate}"/>">
                    <input type="hidden" name="page" value="${showtimeListPageForBack}"> 
                    <input type="hidden" name="totalRevenue" value="${totalRevenueShowTime}">
                    <input type="hidden" name="moviePage" value="${movieReportPage}"> 
                    <button type="submit" class="btn-back">Back to Showtime List</button>
                </form>
            </div>
        </div>

        <script src="js/Invoice.js" defer></script> 
    </body>
</html>