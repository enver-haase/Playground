package org.vaadin.enver.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.vaadin.enver.backend.data.entity.HistoryItem;

public interface HistoryItemRepository extends JpaRepository<HistoryItem, Long> {
}
