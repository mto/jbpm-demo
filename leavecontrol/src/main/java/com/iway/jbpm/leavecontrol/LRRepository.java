package com.iway.jbpm.leavecontrol;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date 5/19/13
 */
public class LRRepository {

    private static volatile LRRepository instance;

    private final ConcurrentMap<String, LeaveRequest> leaveRequests;

    private LRRepository() {
        leaveRequests = new ConcurrentHashMap<String, LeaveRequest>();
    }

    public static LRRepository getInstance() {
        if (instance == null) {
            synchronized (LRRepository.class) {
                if (instance == null) {
                    LRRepository tmp = new LRRepository();
                    instance = tmp;
                }
            }
        }

        return instance;
    }

    public void registerLR(String key, LeaveRequest lr) {
        leaveRequests.put(key, lr);
    }

    public LeaveRequest getLR(String key) {
        return leaveRequests.get(key);
    }

    public List<LeaveRequest> viewedByLeader() {
        List<LeaveRequest> lrs = new LinkedList<LeaveRequest>();
        lrs.addAll(leaveRequests.values());
        return lrs;
    }

    public List<LeaveRequest> viewedByManager() {
        /*
        List<LeaveRequest> lrs = new LinkedList<LeaveRequest>();
        for (LeaveRequest lr : leaveRequests.values()) {
            switch (lr.getStatus()) {
                case LEADER_APPROVED:
                case MANAGER_APPROVED:
                case MANAGER_REJECTED:
                    lrs.add(lr);
                    break;
            }
        }
        return lrs;
        */
        List<LeaveRequest> lrs = new LinkedList<LeaveRequest>();
        lrs.addAll(leaveRequests.values());
        return lrs;
    }
}
