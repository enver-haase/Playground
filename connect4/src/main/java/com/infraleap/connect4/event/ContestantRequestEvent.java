package com.infraleap.connect4.event;

import java.util.EventObject;

public class ContestantRequestEvent extends EventObject {

    public ContestantRequestEvent(PlayerData requestor) {
        super(requestor);
    }

    @Override
    public PlayerData getSource() {
        return (PlayerData) super.getSource();
    }

}
