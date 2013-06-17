<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@page language="java" import="java.util.List" %>
<%@page language="java" import="com.iway.jbpm.domain.LeaveRequest" %>

<portlet:defineObjects/>

<%
    List<LeaveRequest> pendingLRs = (List<LeaveRequest>) renderRequest.getAttribute("pendingLRs");
%>

<table border="1">
    <tr>
        <th>Process InstanceID</th>
        <th>Employee</th>
        <th>Start Date</th>
        <th>End Date</th>
        <th>Status</th>
        <th>Actions</th>
    </tr>

    <% for (LeaveRequest lr : pendingLRs) {%>
    <tr id="<%=lr.instanceID%>">
        <td>
            <%= lr.instanceID%>
        </td>
        <td>
            <%= lr.username%>
        </td>
        <td>
            <%= lr.startDate%>
        </td>
        <td>
            <%= lr.endDate%>
        </td>
        <td>
            <%= lr.status%>
        </td>
        <td>
            <% String lrInstanceID = "" + lr.instanceID; %>
            <form action="<portlet:actionURL name="teamleaderReply"><portlet:param name="leaveRequestInstanceID" value="<%= lrInstanceID%>"/></portlet:actionURL>" method="POST">
                <input type="radio" name="approval" value="APPROVE">APPROVE<br>
                <input type="radio" name="approval" value="REJECT">REJECT<br>
                <input type="submit" value="Reply">
            </form>
        </td>
    </tr>
    <% }%>
</table>
