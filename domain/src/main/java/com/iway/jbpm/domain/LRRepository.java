package com.iway.jbpm.domain;

import org.exoplatform.services.naming.InitialContextInitializer;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.util.LinkedList;
import java.util.List;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date 5/19/13
 */
public class LRRepository {

    private DataSource dataSource;

    public LRRepository(InitialContextInitializer ctxInitializer)
    {
        InitialContext ctx = ctxInitializer.getInitialContext();
        //ctx.lookup()
    }

    public void create(LeaveRequest lr)
    {
        //dataSource.getConnection();
    }

    public void update(LeaveRequest lr)
    {

    }

    public void delete(LeaveRequest lr)
    {
    }

    public List<LeaveRequest> getLeaveRequests()
    {
        return new LinkedList<LeaveRequest>();
    }

    public List<LeaveRequest> getLeaveRequestsOfEmployee(String employee)
    {
        return new LinkedList<LeaveRequest>();
    }

    public List<LeaveRequest> getLeaveRequests(LRFilter filter)
    {
        List<LeaveRequest> result = new LinkedList<LeaveRequest>();
        for(LeaveRequest lr : getLeaveRequests())
        {
            if(filter.accept(lr))
            {
                result.add(lr);
            }
        }
        return result;
    }
}
