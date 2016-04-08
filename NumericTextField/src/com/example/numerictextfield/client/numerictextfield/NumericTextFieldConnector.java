package com.example.numerictextfield.client.numerictextfield;

import com.example.numerictextfield.NumericTextField;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ui.VTextField;
import com.vaadin.client.ui.textfield.TextFieldConnector;
import com.vaadin.shared.ui.Connect;

@Connect(NumericTextField.class)
public class NumericTextFieldConnector extends TextFieldConnector {

	private final static int[] allowedKeyCodes = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 46, 8, 37, 39 };

	public NumericTextFieldConnector() {
		getWidget().addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {
				// consoleLog(event.toDebugString());
				int key = event.getNativeKeyCode();
				for (int i = 0; i < allowedKeyCodes.length; i++) {
					if (allowedKeyCodes[i] == key) {
						return;
					}
				}
				event.preventDefault();

			}
		});
	}

	@Override
	protected Widget createWidget() {
		return GWT.create(VTextField.class);
	}

	@Override
	public VTextField getWidget() {
		return super.getWidget();
	}

	// native void consoleLog(String message) /*-{
	// console.log( message );
	// }-*/;
}
