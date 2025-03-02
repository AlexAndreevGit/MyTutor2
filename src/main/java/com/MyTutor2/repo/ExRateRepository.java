//ExchangeRates_Step_6 define a repository for the exchange rates
package com.MyTutor2.repo;


import com.MyTutor2.model.entity.ExRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ExRateRepository extends JpaRepository<ExRateEntity,Long> {

    Optional<ExRateEntity> findByCurrency(String currency);

}
