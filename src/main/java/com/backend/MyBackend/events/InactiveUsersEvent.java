package com.backend.MyBackend.events;

import java.util.List;

/**
 * InactiveUsersEvent represents an event containing a list of user IDs that are inactive.
 */
public class InactiveUsersEvent{

    /** The list of user IDs that are inactive */
    private final List<Long> userIds;

    /**
     * Constructs an InactiveUsersEvent with the specified list of user IDs.
     *
     * @param userIds
     *            the list of user IDs that are inactive
     */
    public InactiveUsersEvent(List<Long> userIds){
        this.userIds = userIds;
    }

    /**
     * Gets the list of user IDs that are inactive.
     *
     * @return the list of user IDs
     */
    public List<Long> getUserIds(){
        return userIds;
    }
}
