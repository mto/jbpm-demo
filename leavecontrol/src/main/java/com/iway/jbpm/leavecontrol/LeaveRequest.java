package com.iway.jbpm.leavecontrol;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date 5/19/13
 */
public class LeaveRequest {

    public final long instanceID;

    public String username;

    public String startDate;

    public String endDate;

    private LRStatus status;

    public LeaveRequest(long _instanceID, String _username, String _startDate, String _endDate)
    {
        instanceID = _instanceID;
        username = _username;
        startDate = _startDate;
        endDate = _endDate;
        status = LRStatus.REQUEST_SENT;
    }

    public LRStatus getStatus()
    {
        return status;
    }

    public void setStatus(LRStatus _status)
    {
        status = _status;
    }
}
