package com.example.calendarpicker.client.calendarpicker;

import java.util.Date;

import com.example.calendarpicker.CalendarPicker;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.ui.Connect;

@SuppressWarnings("serial")
@Connect(CalendarPicker.class)
public class CalendarPickerConnector extends AbstractComponentConnector {

	CalendarPickerServerRpc rpc = RpcProxy.create(CalendarPickerServerRpc.class, this);

	@SuppressWarnings("deprecation")
	public CalendarPickerConnector() {

		rpc.setDate(new Date(116, 0, 1)); // 1st Jan 2016. Most braindead API
											// ever.
		this.addStateChangeHandler(this);
		
	}

	@Override
	protected Widget createWidget() {
		return GWT.create(DatePicker.class);
	}

	@Override
	public DatePicker getWidget() {
		return (DatePicker) super.getWidget();
	}

	@Override
	public CalendarPickerState getState() {
		return (CalendarPickerState) super.getState();
	}

	@Override
	public void onStateChanged(StateChangeEvent stateChangeEvent) {
		super.onStateChanged(stateChangeEvent);

		final Date date = getState().date;
		getWidget().setValue(date);
	}

}
