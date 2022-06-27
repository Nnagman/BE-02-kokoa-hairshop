package com.prgms.kokoahairshop.designer.repository;

import com.prgms.kokoahairshop.designer.entity.Designer;
import com.prgms.kokoahairshop.hairshop.entity.Hairshop;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DesignerRepository extends JpaRepository<Designer, Long> {

    @Query(value = "SELECT distinct d from Designer d join fetch d.reservations r where d.hairshop.id = :hairshopId and r.date=:date")
    List<Designer> findByHairshopIdAndDate(Long hairshopId, LocalDate date);
}
