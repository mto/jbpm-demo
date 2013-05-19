package com.iway.jbpm.leavecontrol;

import com.iway.jbpm.core.KnowledgeEngine;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date 5/18/13
 */
public class LeaveRequestPortlet extends GenericPortlet {

    @Override
    protected void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        if(request.getRemoteUser() == null)
        {
            response.getWriter().write("Only authenticated user could access this portlet");
        }
        else
        {
            getPortletContext().getRequestDispatcher("/jsp/lrForm.jsp").include(request, response);
        }
    }

    @ProcessAction(name = "sendLeaveRequest")
    public void processLR(ActionRequest req, ActionResponse res) throws PortletException, IOException{
        String username = req.getParameter("username");
        if(req.getRemoteUser() == null || !req.getRemoteUser().equals(username))
        {
            throw new PortletException("Prevent hack");
        }
        else
        {
            String startDate = req.getParameter("startDate");
            String endDate = req.getParameter("endDate");

            KnowledgeEngine kEngine = Util.getServiceComponent(KnowledgeEngine.class);
            long processInstanceID = kEngine.executeProcess("com.iway.jbpm");

            LeaveRequest newLR = new LeaveRequest(processInstanceID, username, startDate, endDate);
            LRRepository.getInstance().registerLR(username + "_" + startDate, newLR);
        }
    }


}
