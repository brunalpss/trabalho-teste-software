package alugueldecarros.repository;

import alugueldecarros.models.Address;
import alugueldecarros.models.User;
import alugueldecarros.models.Vehicles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiclesRepository extends JpaRepository<Vehicles,Long> {

    Vehicles findOneByLicensePlateAndDeletedAtIsNull(String licensePlate);

    Page<Vehicles> findAllByDeletedAtIsNullOrderByName(Pageable page);

    Page<Vehicles> findAllByNameAndDeletedAtIsNullOrderByName(String name, Pageable page);

    Vehicles findOneByIdVehicleAndDeletedAtIsNull(Long idVehicle);
}
