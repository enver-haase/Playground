package com.example.calendarpicker;

import java.util.Date;
import java.util.HashSet;

import com.example.calendarpicker.client.calendarpicker.CalendarPickerServerRpc;
import com.example.calendarpicker.client.calendarpicker.CalendarPickerState;

public class CalendarPicker extends com.vaadin.ui.AbstractComponent implements CalendarPickerServerRpc{

	public interface CalendarPickerListener{
		public void onDateChanged();
	};
	
	private final HashSet<CalendarPickerListener> listeners = new HashSet<>();
	
	private final CalendarPickerServerRpc rpc = new CalendarPickerServerRpc() {
		@Override
		public void setDate(Date date) {
			CalendarPicker.this.setDate(date);
			
			for(CalendarPickerListener listener : listeners){
				listener.onDateChanged();
			}
		}
	};  
	
	public CalendarPicker() {
		registerRpc(rpc);
	}
	
	public Date getDate() {
		return getState().date;
	}
	
	@Override
	public void setDate(Date d) {
		getState().date = d;
	}
	
	public void addDateChangeListener(CalendarPickerListener cpl){
		this.listeners.add(cpl);
	}
	
	@Override
	public CalendarPickerState getState() {
		return (CalendarPickerState) super.getState();
	}
}
