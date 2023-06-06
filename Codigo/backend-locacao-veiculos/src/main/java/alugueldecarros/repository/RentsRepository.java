package alugueldecarros.repository;

import alugueldecarros.models.Address;
import alugueldecarros.models.Rents;
import alugueldecarros.models.ResponseEntity.RentsResponse;
import alugueldecarros.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentsRepository extends JpaRepository<Rents,Long> {

    Rents findOneByIdRentAndDeletedAtIsNull(Long idRent);

    Page<Rents> findAllByDeletedAtIsNullOrderByIdRent(Pageable page);
    Page<Rents> findAllByStatusIgnoreCaseAndDeletedAtIsNull(Pageable page, String status);

    Page<Rents> findAllByIdCreatorAndDeletedAtIsNullOrderByIdRent(Pageable page,Long idCreator);

    Page<Rents> findAllByIdCreatorAndStatusIgnoreCaseAndDeletedAtIsNull(Pageable page,Long idCreator, String status);


}
