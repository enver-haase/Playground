package org.vaadin.enver.app.security;

import org.vaadin.enver.backend.data.entity.User;

@FunctionalInterface
public interface CurrentUser {

	User getUser();
}
