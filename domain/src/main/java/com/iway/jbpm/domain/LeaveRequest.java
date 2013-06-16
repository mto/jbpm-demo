package com.iway.jbpm.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date 5/19/13
 */
public class LeaveRequest {

    public long instanceID;

    public String username;

    public String startDate;

    public String endDate;

    public int numberOfDays;

    public LRStatus status;

    public LeaveRequest(long _instanceID, String _username, String _startDate, String _endDate)
    {
        instanceID = _instanceID;
        username = _username;
        startDate = _startDate;
        endDate = _endDate;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            Date start = dateFormat.parse(_startDate);
            Date end = dateFormat.parse(_endDate);
            numberOfDays = Math.max(1, (int) (end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24));
        } catch (ParseException parseEx) {
        }
        status = LRStatus.REQUEST_SENT;

    }
}
