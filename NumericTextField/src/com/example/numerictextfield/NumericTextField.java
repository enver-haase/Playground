package com.example.numerictextfield;


public class NumericTextField extends com.vaadin.ui.TextField {


	public NumericTextField() {
	}

	@Override
	public void setValue(String value){
		Integer.parseInt(value); // throws NumberFormatException if value is not numerical. //TODO: do we want to exclude negative numbers?
	}
}
