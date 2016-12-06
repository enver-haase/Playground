package com.example.numerictextfield;


public class NumericTextField extends com.vaadin.ui.TextField {


	public NumericTextField() {
	}

	@Override
	public void setValue(String value){
		if (value.length() != 0){
			Integer.parseInt(value); // throws NumberFormatException if value is not numerical. //TODO: do we want to exclude negative numbers?value = "0";
		}
		super.setValue(value);
	}
}
