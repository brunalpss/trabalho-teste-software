package alugueldecarros.service;

import alugueldecarros.enums.RolesEnum;
import alugueldecarros.models.Contracts;
import alugueldecarros.models.RequestEntity.ContractsRequest;
import alugueldecarros.models.RequestEntity.UserRequest;
import alugueldecarros.models.ResponseEntity.ContractsResponse;
import alugueldecarros.models.User;
import alugueldecarros.models.dto.UserDto;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface ContractsService {

    ContractsResponse createContract(ContractsRequest request);

    ContractsResponse editContract(ContractsRequest request);

    ContractsResponse deleteContract(Long idContract);

    Page<Contracts> listContractsByPage(Pageable pages);

    Page<Contracts> listContractsByPageAndIdRents(Pageable pages, Long idRent);

    ContractsResponse getContractById(Long idContract);
}
