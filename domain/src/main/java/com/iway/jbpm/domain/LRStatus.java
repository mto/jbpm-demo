package com.iway.jbpm.domain;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date 5/19/13
 */
public enum LRStatus {

    REQUEST_SENT,

    LEADER_APPROVED,

    LEADER_REJECTED,

    FULLY_APPROVED,

    MANAGER_REJECTED,

    MAX_DAYS_EXCEEDED
}
