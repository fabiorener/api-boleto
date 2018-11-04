package com.contaazul.boleto.api.repository;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.contaazul.boleto.api.domain.Bankslip;
import com.contaazul.boleto.api.enums.StatusEnum;

@Repository
public interface BankslipRepository extends PagingAndSortingRepository<Bankslip, UUID> {


	Stream<Bankslip> findByCustomerLike(String customer);
	Page<Bankslip> findByCustomerLike(String customer,Pageable pages);

	@Transactional
	@Modifying
	//@Query("UPDATE Bankslip c SET c.status = :status, paymentDate = CURRENT_DATE  WHERE c.id = :id")
	@Query("UPDATE Bankslip c SET c.status = :status, paymentDate = :paymentDate  WHERE c.id = :id")
	int updatePayment(@Param("id") UUID id, @Param("status") StatusEnum status, @Param("paymentDate") Date paymentDate );
	
	@Transactional
	@Modifying
	@Query("UPDATE Bankslip c SET c.status = :status WHERE c.id = :id")
	int updateCancel(@Param("id") UUID id, @Param("status") StatusEnum status);

}
