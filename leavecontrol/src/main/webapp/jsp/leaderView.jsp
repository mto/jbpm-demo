<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@page language="java" import="java.util.List,com.iway.jbpm.leavecontrol.*" %>

<%
    List<LeaveRequest> lrs = LRRepository.getInstance().viewedByLeader();
%>

<table>
    <% for (LeaveRequest lr : lrs) {%>
    <tr id="<%=lr.instanceID%>">
        <td><%= lr.username%>
        </td>
        <td><%= lr.startDate%>
        </td>
        <td><%= lr.endDate%>
        </td>
        <td><%= lr.getStatus()%>
        </td>
    </tr>
    <% }%>
</table>