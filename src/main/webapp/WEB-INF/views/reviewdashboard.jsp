<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@include file="header.jsp" %>

<div class="container">
    <div class="row">
        <div class="col-xs-12 col-md-12">
            <div class="table-responsive">
                <table class="table table-bordered numeric-table table-striped table-hover">
                    <thead>
                    <tr>
                        <th>Location</th>
                        <th>Suggested Type</th>
                        <th>Suggested ULB</th>
                        <th>Suggested By</th>
                        <th>Name</th>
                        <th>Phone</th>
                        <th>Email</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${adminVerifications}" var="adminVerification">
                        <tr>
                            <td>${adminVerification.location.address}</td>
                            <td>${adminVerification.locationType}</td>
                            <td>${adminVerification.ULBName}</td>
                            <td>${adminVerification.category}</td>
                            <td>${adminVerification.name}</td>
                            <td>${adminVerification.phone}</td>
                            <td>${adminVerification.email}</td>
                            <td>
                                <button class="btn-success"><i class="glyphicon glyphicon-ok"></i></button>
                                <button class="btn-danger"><i class="glyphicon glyphicon-remove"></i></button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<%@include file="footer-new.jsp" %>
<%@include file="header-end.jsp" %>