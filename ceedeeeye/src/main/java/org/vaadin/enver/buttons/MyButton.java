package org.vaadin.enver.buttons;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;

public class MyButton extends Button implements LocaleChangeObserver {
	@Override
	public void localeChange(LocaleChangeEvent event) {
		setText(getTranslation("my-app.button.continue.caption", event.getLocale(), "<infix>"));

		setIcon(new Label(getTranslation("loca.button.label", event.getLocale())));

		Notification.show(event.getLocale().toLanguageTag());
	}


	public MyButton(String text,
					ComponentEventListener<ClickEvent<Button>> clickListener) {
		super(text, clickListener);
	}
}
