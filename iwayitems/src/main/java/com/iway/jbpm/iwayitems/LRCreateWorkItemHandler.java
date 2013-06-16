package com.iway.jbpm.iwayitems;

import com.iway.jbpm.domain.LRRepository;
import com.iway.jbpm.domain.LRStatus;
import com.iway.jbpm.domain.LeaveRequest;
import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemHandler;
import org.drools.runtime.process.WorkItemManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date 6/15/13
 */
public class LRCreateWorkItemHandler implements WorkItemHandler {

    private final LRRepository lrService;

    public LRCreateWorkItemHandler(LRRepository _lrService)
    {
        lrService = _lrService;
    }

    @Override
    public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
        LeaveRequest leaveRQ = (LeaveRequest)workItem.getParameter("leaveRQ");
        leaveRQ.instanceID = workItem.getProcessInstanceId();

        List<LeaveRequest> lrsOfCurrentEmployee = lrService.getLeaveRequestsOfEmployee(leaveRQ.username);
        int totalDaysOff = leaveRQ.numberOfDays;
        for(LeaveRequest lr : lrsOfCurrentEmployee)
        {
            if(lr.status == LRStatus.FULLY_APPROVED)
            {
                totalDaysOff += lr.numberOfDays;
            }
        }

        Map<String, Object> results = new HashMap<String,Object>();
        results.put("totalDaysOff", totalDaysOff);

        //TODO: Invoke this assynchronously and notify work item manager on success
        lrService.create(leaveRQ);

        //Need this hacky code as i don't want to add another service task into the process definition
        if(totalDaysOff > 12)
        {
            leaveRQ.status = LRStatus.MAX_DAYS_EXCEEDED;
            lrService.update(leaveRQ);
        }

        manager.completeWorkItem(workItem.getId(), results);
    }

    @Override
    public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
    }
}
