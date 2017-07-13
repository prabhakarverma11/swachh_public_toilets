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
                        <th style="width: 10%">Action</th>
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
                                <button class="btn-success" onclick="update(event);"><i
                                        class="glyphicon glyphicon-ok" id="${adminVerification.id}"></i></button>
                                <button class="btn-danger" onclick="reject(event);"><i
                                        class="glyphicon glyphicon-remove" id="${adminVerification.id}"></i></button>
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
<script>
    function update(event) {
        var data = {id: event.target.id};
        data["${_csrf.parameterName}"] = "${_csrf.token}";
        $.ajax({
            url: "/api/admin/accept",
            type: 'POST',
            data: data,
            success: function (response) {
                alert("accepted");
            }
        });
    }
    function reject(event) {
        var data = {id: event.target.id};
        data["${_csrf.parameterName}"] = "${_csrf.token}";
        $.ajax({
            url: "/api/admin/reject",
            type: 'POST',
            data: data,
            success: function (response) {
                alert("rejected");
            }
        });
    }
</script>