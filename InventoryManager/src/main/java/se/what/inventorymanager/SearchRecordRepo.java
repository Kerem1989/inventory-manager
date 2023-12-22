package se.what.inventorymanager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchRecordRepo extends JpaRepository<SearchRecord, Integer> {

    // Lägg till metoder
}
