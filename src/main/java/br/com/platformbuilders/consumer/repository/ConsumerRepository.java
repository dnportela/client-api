package br.com.platformbuilders.consumer.repository;

import br.com.platformbuilders.consumer.entity.Consumer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConsumerRepository extends PagingAndSortingRepository<Consumer, Integer> {

    @Query(nativeQuery = true, value = "select * from Consumer")
    List<Consumer> getAllConsumersList(Pageable pageable);

    @Query(nativeQuery = true, value = "select * from Consumer where DOCUMENT_NUMBER = :documentNumber")
    Optional<Consumer> findByDocumentNumber(@Param("documentNumber") String documentNumber);

    @Query(nativeQuery = true, value = "select * from Consumer where DOCUMENT_NUMBER = :documentNumber " +
            "or MOBILE_PHONE_NUMBER = :mobilePhoneNumber or EMAIL = :email")
    Optional<Consumer> findByDocumentNumberOrMobilePhoneNumberOrEmail(@Param("documentNumber") String documentNumber,
                                                                      @Param("mobilePhoneNumber") String mobilePhoneNumber,
                                                                      @Param("email") String email);

}
