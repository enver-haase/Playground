package com.example.calendarpicker.client.calendarpicker;

import java.util.Date;

import com.vaadin.shared.communication.ServerRpc;

public interface CalendarPickerServerRpc extends ServerRpc {

	public void setDate(Date date);

}
