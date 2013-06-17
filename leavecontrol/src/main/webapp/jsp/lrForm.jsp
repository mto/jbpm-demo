<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@page language="java" import="java.util.List" %>
<%@page language="java" import="com.iway.jbpm.domain.LeaveRequest" %>

<portlet:defineObjects/>

<div class="portlet-section-header">Welcome <%= renderRequest.getRemoteUser()%></div>

<br/>
<br/>

<div class="portlet-font">Leave request form<br/>

    <portlet:actionURL var="sendLR" name="sendLeaveRequest">
        <portlet:param name="username" value="<%= renderRequest.getRemoteUser()%>"/>
    </portlet:actionURL>

    <form action="<%= sendLR %>" method="POST">
        <input class="portlet-form-input-field" type="date" name="startDate"/>
        <input class="portlet-form-input-field" type="date" name="endDate"/>
        <input class="portlet-form-button" type="Submit" value="Send"/>
    </form>
</div>

<div>Leave requests of <%= renderRequest.getRemoteUser()%></div>
<%
    List<LeaveRequest> lrs_of_employee = (List<LeaveRequest>) renderRequest.getAttribute("lrs_of_employee");
%>

<table>
    <% for (LeaveRequest lr : lrs_of_employee) {%>
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

