package com.iway.jbpm.iwayitems;

import com.iway.jbpm.domain.LRRepository;
import com.iway.jbpm.domain.LRStatus;
import com.iway.jbpm.domain.LeaveRequest;
import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemHandler;
import org.drools.runtime.process.WorkItemManager;

import java.util.HashMap;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date 6/16/13
 */
public class LRUpdateWorkItemHandler implements WorkItemHandler {

    private final LRRepository lrService;

    public LRUpdateWorkItemHandler(LRRepository _lrService)
    {
      lrService = _lrService;
    }

    @Override
    public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
        LeaveRequest leaveRQ = (LeaveRequest)workItem.getParameter("leaveRQ");
        String role_of_replier = (String) workItem.getParameter("role_of_replier");
        boolean approval = (Boolean) workItem.getParameter("approval");

        if ("TEAM_LEADER".equals(role_of_replier)) {
            leaveRQ.status = approval ? LRStatus.LEADER_APPROVED : LRStatus.LEADER_REJECTED;
        } else if ("MANAGER".equals(role_of_replier)) {
            leaveRQ.status = approval ? LRStatus.FULLY_APPROVED : LRStatus.MANAGER_REJECTED;
        }

        lrService.update(leaveRQ);

        manager.completeWorkItem(workItem.getId(), new HashMap<String, Object>());
    }

    @Override
    public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
    }
}
