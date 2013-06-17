<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@page language="java" import="java.util.List" %>
<%@page language="java" import="com.iway.jbpm.domain.LeaveRequest" %>

<portlet:defineObjects/>

<div class="portlet-section-header"><h3>Welcome: <i><%= renderRequest.getRemoteUser()%></i></h3></div>

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

<hr>

<div><h3>Leave requests of: <i><%= renderRequest.getRemoteUser()%></i></h3></div>
<%
    List<LeaveRequest> lrs_of_employee = (List<LeaveRequest>) renderRequest.getAttribute("lrs_of_employee");
%>

<table border="1">
    <tr>
        <th>Process InstanceID</th>
        <th>Employee</th>
        <th>Start Date</th>
        <th>End Date</th>
        <th>Status</th>
    </tr>

    <% for (LeaveRequest lr : lrs_of_employee) {%>
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
    </tr>
    <% }%>
</table>

