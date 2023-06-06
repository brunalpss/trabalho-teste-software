package alugueldecarros.service;

import alugueldecarros.enums.RolesEnum;
import alugueldecarros.models.Rents;
import alugueldecarros.models.RequestEntity.RentRequest;
import alugueldecarros.models.RequestEntity.UserRequest;
import alugueldecarros.models.ResponseEntity.RentsResponse;
import alugueldecarros.models.User;
import alugueldecarros.models.dto.UserDto;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RentsService {

    RentsResponse createRent(RentRequest request);

    RentsResponse editRent(RentRequest request);

    RentsResponse deleteRent(Long idRent);
    RentsResponse cancelRentLoggedUser(Long idRent);

    Page<Rents> listRentsByPage(Pageable pages);

    Page<Rents> listRentsByPageAndStatus(Pageable pages, String status);

    Page<Rents> listRentsByUserAndPage(Pageable pages, Long idUser);

    Page<Rents> listRentsByPageAndStatusAndUser(Pageable pages, String status, Long idUser);

    RentsResponse getRentById(Long idRent);

    RentsResponse changeStatus(RentRequest request);
}
