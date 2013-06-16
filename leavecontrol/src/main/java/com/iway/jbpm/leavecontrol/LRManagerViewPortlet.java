package com.iway.jbpm.leavecontrol;

import com.iway.jbpm.core.KnowledgeEngine;
import com.iway.jbpm.domain.LRFilter;
import com.iway.jbpm.domain.LRRepository;
import com.iway.jbpm.domain.LRStatus;
import com.iway.jbpm.domain.LeaveRequest;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date 6/16/13
 */
public class LRManagerViewPortlet extends GenericPortlet {

    @Override
    protected void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        String user = request.getRemoteUser();
        if (user == null) {
            response.getWriter().write("Only authenticated user could access to this portlet");
        } else {
            LRRepository lrService = Util.getServiceComponent(LRRepository.class);
            List<LeaveRequest> pendingLRs = lrService.getLeaveRequests(new LRFilter() {
                @Override
                public boolean accept(LeaveRequest lr) {
                    return lr.status == LRStatus.LEADER_APPROVED;
                }
            });
            request.setAttribute("pendingLRs", pendingLRs);
            getPortletContext().getRequestDispatcher("/jsp/managerView.jsp").include(request, response);
        }
    }

    @ProcessAction(name = "managerReply")
    public void managerReply(ActionRequest req, ActionResponse res) throws PortletException, IOException {
        boolean approval = "true".equals(req.getParameter("approval"));
        long processInstanceID = Long.parseLong(req.getParameter("leaveRequestInstanceID"));

        KnowledgeEngine kEngine = Util.getServiceComponent(KnowledgeEngine.class);
        kEngine.signalEvent("Manager_Reply", approval, processInstanceID);
    }

}
