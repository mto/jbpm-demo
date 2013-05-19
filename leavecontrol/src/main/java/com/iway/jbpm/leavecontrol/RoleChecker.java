package com.iway.jbpm.leavecontrol;

import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date 5/19/13
 */
public class RoleChecker {

    private final static List<String> MANAGERS = Arrays.asList("root", "john");

    public static boolean isManager(String username)
    {
        return MANAGERS.contains(username) || username.startsWith("manager");
    }

    public static boolean isLeader(String username)
    {
        return username.startsWith("leader");
    }
}
