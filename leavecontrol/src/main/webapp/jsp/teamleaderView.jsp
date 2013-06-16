<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@page language="java" import="java.util.List" %>
<%@page language="java" import="com.iway.jbpm.domain.LeaveRequest" %>

<portlet:defineObjects/>

<%
    List<LeaveRequest> pendingLRs = (List<LeaveRequest>) renderRequest.getAttribute("pendingLRs");
%>

<table>
    <% for (LeaveRequest lr : pendingLRs) {%>
    <tr id="<%=lr.instanceID%>">
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
    </tr>
    <% }%>
</table>
