package com.iway.jbpm.domain;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date 6/16/13
 */
public interface LRFilter {

    public boolean accept(LeaveRequest lr);
}
