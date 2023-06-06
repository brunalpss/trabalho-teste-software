package alugueldecarros.service.impl;

import alugueldecarros.models.Contracts;
import alugueldecarros.models.RequestEntity.ContractsRequest;
import alugueldecarros.models.ResponseEntity.ContractsResponse;
import alugueldecarros.service.ContractsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ContractsServiceImpl implements ContractsService {
    @Override
    public ContractsResponse createContract(ContractsRequest request) {
        return null;
    }

    @Override
    public ContractsResponse editContract(ContractsRequest request) {
        return null;
    }

    @Override
    public ContractsResponse deleteContract(Long idContract) {
        return null;
    }

    @Override
    public Page<Contracts> listContractsByPage(Pageable pages) {
        return null;
    }

    @Override
    public Page<Contracts> listContractsByPageAndIdRents(Pageable pages, Long idRent) {
        return null;
    }

    @Override
    public ContractsResponse getContractById(Long idContract) {
        return null;
    }
}
