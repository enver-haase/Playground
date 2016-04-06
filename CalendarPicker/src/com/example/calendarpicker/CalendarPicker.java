package com.example.calendarpicker;

import com.example.calendarpicker.client.calendarpicker.CalendarPickerServerRpc;
import com.example.calendarpicker.client.calendarpicker.CalendarPickerState;
import com.vaadin.shared.MouseEventDetails;

public class CalendarPicker extends com.vaadin.ui.AbstractComponent {

	private final CalendarPickerServerRpc rpc = new CalendarPickerServerRpc() {
		private final int clickCount = 0;

		@Override
		public void clicked(MouseEventDetails mouseDetails) {
			// update shared state
			getState().text = "You have clicked " + clickCount + " times";
		}
	};  

	public CalendarPicker() {
		registerRpc(rpc);
	}

	@Override
	public CalendarPickerState getState() {
		return (CalendarPickerState) super.getState();
	}
}
