package alugueldecarros.repository;

import alugueldecarros.models.Address;
import alugueldecarros.models.Contracts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractsRepository extends JpaRepository<Contracts,Long> {

}
