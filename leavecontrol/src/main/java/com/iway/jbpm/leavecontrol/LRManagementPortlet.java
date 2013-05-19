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
 * @date 5/19/13
 */
public class LRManagementPortlet extends GenericPortlet {

    @Override
    protected void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        String user = request.getRemoteUser();
        if (user == null) {
            response.getWriter().write("Only authenticated user could access to this portlet");
        } else {
            if (RoleChecker.isManager(user)) {
                getPortletContext().getRequestDispatcher("/jsp/managerView.jsp").include(request, response);
            } else {
                response.getWriter().write("User " + user + " is not manager, he/she does not access to this portlet");
            }
        }
    }

    @ProcessAction(name = "managerReply")
    public void managerValidate(ActionRequest req, ActionResponse res) throws PortletException, IOException {
        String lrKey = req.getParameter("leaveRequestKey");
        boolean approved = "true".equals(req.getParameter("approved"));
        LeaveRequest lr = LRRepository.getInstance().getLR(lrKey);

        KnowledgeEngine kEngine = Util.getServiceComponent(KnowledgeEngine.class);
        kEngine.signalEvent("ManagerReply", approved, lr.instanceID);
        lr.setStatus(approved ? LRStatus.MANAGER_APPROVED : LRStatus.MANAGER_REJECTED);
    }

    @ProcessAction(name = "leaderReply")
    public void leaderValidate(ActionRequest req, ActionResponse res) throws PortletException, IOException {
        String lrKey = req.getParameter("leaveRequestKey");
        boolean approved = "true".equals(req.getParameter("approved"));
        LeaveRequest lr = LRRepository.getInstance().getLR(lrKey);

        KnowledgeEngine kEngine = Util.getServiceComponent(KnowledgeEngine.class);
        kEngine.signalEvent("LeaderReply", approved, lr.instanceID);
        lr.setStatus(approved ? LRStatus.LEADER_APPROVED : LRStatus.LEADER_REJECTED);
    }
}
