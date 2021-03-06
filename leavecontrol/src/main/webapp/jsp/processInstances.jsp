<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@page language="java" import="java.util.Collection" %>
<%@page language="java" import="org.drools.runtime.process.ProcessInstance" %>
<%@page language="java" import="org.drools.runtime.process.NodeInstance" %>
<%@page language="java" import="org.drools.runtime.process.NodeInstanceContainer" %>

<portlet:defineObjects/>

<%
    Collection<ProcessInstance> activeProcessInstances = (Collection<ProcessInstance>) renderRequest.getAttribute("activeProcessInstances");
%>

<table border="1">
    <tr>
        <th>Process Instance ID</th>
        <th>Process Definition Name</th>
        <th>Current Node </th>
        <th>Process Instance State</th>
    </tr>

    <% for (ProcessInstance activeInstance : activeProcessInstances) {
        NodeInstance currentNode = ((NodeInstanceContainer) activeInstance).getNodeInstances().iterator().next();
    %>
    <tr id="<%=activeInstance.getId()%>">
        <td>
            <%=activeInstance.getId()%>
        </td>
        <td>
            <%=activeInstance.getProcessName()%>
        </td>
        <td>
            <%=currentNode.getNodeName()%>
        </td>
        <td>
            <%=activeInstance.getState()%>
        </td>
    </tr>
    <% }%>
</table>
