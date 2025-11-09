<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<c:set var="manager" value="${sessionScope.loggedManager}" />
<c:set var="movieList" value="${requestScope.movieList}" />
<c:set var="fromDate" value="${requestScope.fromDate}" />
<c:set var="toDate" value="${requestScope.toDate}" />
<c:set var="grandTotalRevenue" value="${requestScope.grandTotalRevenue}" />
<c:set var="currentPage" value="${not empty requestScope.currentPage ? requestScope.currentPage : 1}" />
<c:set var="totalPages" value="${not empty requestScope.totalPages ? requestScope.totalPages : 0}" />
<c:set var="recordsPerPage" value="${not empty requestScope.recordsPerPage ? requestScope.recordsPerPage : 10}" /> 
<c:set var="error" value="${requestScope.error}" />

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Box Office Revenue Report</title>
        <link rel="stylesheet" href="css/movieReport.css">
    </head>
    <body>
        <header class="main-header">
            <ul class="nav-links">
                <c:if test="${not empty manager}">
                    <li><a href="managerHome.jsp">CineMan Dashboard: Movie Revenue Statistic</a></li>
                    </c:if>
            </ul>
            <ul class="user-actions">
                <li><span class="user-info">Hello, <c:out value="${manager.fullName}"/></span></li>
                <li>
                    <form action="LogoutServlet" method="post">
                        <button type="submit" class="logout-button">Logout</button>
                    </form>
                </li>
            </ul>
        </header>

        <div class="container">
            <div class="left-panel">
                <h2>Revenue Statistic Form</h2>
                <p class="note">Select a date range to generate the report</p>

                <form id="filterForm" action="MovieServlet" method="post">
                    <label for="fromDate">From</label>
                    <input type="date" id="fromDate" name="fromDate" value="<c:out value="${fromDate}"/>" required>

                    <label for="toDate">To</label>
                    <input type="date" id="toDate" name="toDate" value="<c:out value="${toDate}"/>" required>

                    <%-- trang hien tai --%>
                    <input type="hidden" id="page" name="page" value="${currentPage}">

                    <div class="btn-container">
                        <button type="submit" class="btn-statistics">Statistic</button>
                        <button type="button" class="btn-back" onclick="window.location.href = 'StatServlet'">Back</button>
                    </div>
                </form>


                <c:if test="${not empty error}">
                    <p class="error-message"><c:out value="${error}"/></p>
                </c:if>
            </div>

            <div class="right-panel">
                <h3>
                    <c:choose>
                        <c:when test="${not empty fromDate and not empty toDate}">
                            Report <c:out value="${fromDate}"/> to <c:out value="${toDate}"/>
                        </c:when>
                        <c:otherwise>
                            Please select a date range to view the report.
                        </c:otherwise>
                    </c:choose>
                </h3>

                <c:if test="${not empty grandTotalRevenue and grandTotalRevenue > 0}">
                    <div class="revenue-summary">
                        <h4>Grand Total Revenue: 
                            <fmt:formatNumber value="${grandTotalRevenue}" type="number" groupingUsed="true" maxFractionDigits="0"/> VND
                        </h4>

                    </div>

                    <h4 style="font-style : italic"> Click on a row to see detailed revenue for this movie. </h4>
                </c:if>

                <table>
                    <thead>
                        <tr><th>No.</th><th>Movie ID</th><th>Title</th><th>Total Revenue (VND)</th> <th> Release Date </th> </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty movieList}">
                                <c:forEach var="m" items="${movieList}" varStatus="loop">
                                    <%-- Form ẩn cho mỗi hàng để submit khi click --%>
                                <form id="detailForm${m.movieID}" action="DetailServlet" method="post" style="display:none;">
                                    <input type="hidden" name="movieID" value="${m.movieID}">
                                    <input type="hidden" name="movieTitle" value="<c:out value="${m.title}"/>">
                                    <input type="hidden" name="fromDate" value="<c:out value="${fromDate}"/>">
                                    <input type="hidden" name="toDate" value="<c:out value="${toDate}"/>">
                                    <input type="hidden" name="moviePage" value="${currentPage}">
                                    <input type="hidden" name="totalRevenue" value="${m.totalRevenue}">
                                </form>
                                <tr data-form-id="detailForm${m.movieID}">

                                    <td>${(currentPage - 1) * recordsPerPage + loop.count}</td>
                                    <td>${m.movieID}</td>
                                    <td><c:out value="${m.title}"/></td>
                                    <td><fmt:formatNumber value="${m.totalRevenue}" type="number" groupingUsed="true" maxFractionDigits="0"/></td>
                                    <td> ${m.releaseDate} </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr><td colspan="4" class="no-data">No data found for the selected date range.</td></tr>
                        </c:otherwise>
                    </c:choose>
                    </tbody>
                </table>


                <c:if test="${totalPages > 1}">
                    <div class="pagination">

                        <a href="javascript:goToPage(${currentPage - 1})" 
                           class="${currentPage <= 1 ? 'disabled' : ''}">&laquo; Prev</a>

                        <c:forEach var="i" begin="1" end="${totalPages}">
                            <a href="javascript:goToPage(${i})" 
                               class="${i == currentPage ? 'active' : ''}">${i}</a>
                        </c:forEach>


                        <a href="javascript:goToPage(${currentPage + 1})" 
                           class="${currentPage >= totalPages ? 'disabled' : ''}">Next &raquo;</a>
                    </div>
                </c:if>
            </div>
        </div>


        <script src="js/movieReport.js" defer></script>
    </body>
</html>