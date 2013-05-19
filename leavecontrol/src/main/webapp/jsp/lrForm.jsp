<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

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

