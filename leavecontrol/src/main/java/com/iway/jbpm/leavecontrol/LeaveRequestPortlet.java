package com.iway.jbpm.leavecontrol;

import com.iway.jbpm.core.KnowledgeEngine;
import com.iway.jbpm.domain.LRFilter;
import com.iway.jbpm.domain.LRRepository;
import com.iway.jbpm.domain.LeaveRequest;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date 5/18/13
 */
public class LeaveRequestPortlet extends GenericPortlet {

    @Override
    protected void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        final String remoteuser = request.getRemoteUser();
        if (remoteuser == null) {
            response.getWriter().write("Only authenticated user could access this portlet");
        } else {
            LRRepository lrService = Util.getServiceComponent(LRRepository.class);
            List<LeaveRequest> lrs = lrService.getLeaveRequestsOfEmployee(remoteuser);
            request.setAttribute("lrs_of_employee", lrs);
            getPortletContext().getRequestDispatcher("/jsp/lrForm.jsp").include(request, response);
        }
    }

    @ProcessAction(name = "sendLeaveRequest")
    public void processLR(ActionRequest req, ActionResponse res) throws PortletException, IOException {
        String username = req.getParameter("username");
        if (req.getRemoteUser() == null || !req.getRemoteUser().equals(username)) {
            throw new PortletException("Prevent hack");
        } else {
            String startDate = req.getParameter("startDate");
            String endDate = req.getParameter("endDate");

            KnowledgeEngine kEngine = Util.getServiceComponent(KnowledgeEngine.class);

            LeaveRequest newLR = new LeaveRequest(0, username, startDate, endDate);
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("leaveRQ", newLR);
            kEngine.executeProcess("iway_demo.leave-request-process", params);
        }
    }


}
