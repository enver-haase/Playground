package org.vaadin.enver.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.vaadin.enver.backend.data.entity.PickupLocation;

public interface PickupLocationRepository extends JpaRepository<PickupLocation, Long> {

	Page<PickupLocation> findByNameLikeIgnoreCase(String nameFilter, Pageable pageable);

	int countByNameLikeIgnoreCase(String nameFilter);
}
