package com.example.beantest;

import com.vaadin.data.util.AbstractProperty;
import com.vaadin.data.util.BeanItemContainer;

public class Model {
	private static Model theInstance = new Model();
	
	private final BeanItemContainer<Bean> bic;
	
	public static Model getInstance(){
		return Model.theInstance;
	}
	
	private Model(){
		bic = new BeanItemContainer<Bean>(Bean.class);
		bic.addBean(new Bean());
		
		@SuppressWarnings("unchecked")
		AbstractProperty<Integer> valProp = (AbstractProperty<Integer>) bic.getItem(bic.getItemIds().get(0)).getItemProperty("val");

		new Thread(){
			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					int i = valProp.getValue() + 1;
					valProp.setValue(i);
					
				}
			}
		}.start();
	}
	
	public BeanItemContainer<Bean> getContainer(){
		return bic;
	}
}
