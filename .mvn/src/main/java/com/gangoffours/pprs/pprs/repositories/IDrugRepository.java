package com.gangoffours.pprs.pprs.repositories;

import java.util.List;

import com.gangoffours.pprs.pprs.models.Drug;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
 
@Repository

public interface IDrugRepository extends CrudRepository<Drug, String> {
    List<Drug> findByname(String name);

    @Cacheable("drugs")
    Drug findByDrugbankid(String drugbankid);

    @Query("select d from Drug d where d.drugbankid like %:id% and d.name like %:name% and d.drugclass like %:drugclass%")
    List<Drug> findByDrugDetails(@Param("id") String id, @Param("name") String name, @Param("drugclass") String drugclass);

    Drug getNameByDrugbankid(String drugbankid);
}
