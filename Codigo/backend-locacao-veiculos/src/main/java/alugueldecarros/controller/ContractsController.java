package alugueldecarros.controller;

import alugueldecarros.models.Contracts;
import alugueldecarros.models.RequestEntity.ContractsRequest;
import alugueldecarros.models.RequestEntity.UserRequest;
import alugueldecarros.models.ResponseEntity.ContractsResponse;
import alugueldecarros.models.User;
import alugueldecarros.models.dto.UserDto;
import alugueldecarros.service.ContractsService;
import alugueldecarros.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/contracts")
public class ContractsController {

    @Autowired
    private ContractsService contractsService;

    @PostMapping(path = "/create")
    @ApiOperation(value = "Criar um novo contrato para atrelar a uma RENT")
    @PreAuthorize("@authorityChecker.isAllowed({'ADMIN','FINANCIAL','SELLERS','THIRDPARTY'})")
    public ResponseEntity<ContractsResponse> createContract(
            @ApiParam(value = "Json da requisição que contem o dado do contrato a ser salvo")
            @Valid @RequestBody ContractsRequest request) throws NotFoundException {
        ContractsResponse response = this.contractsService.createContract(request);
        return ResponseEntity.ok().body(
                response
        );
    }

    @PostMapping(path = "/edit")
    @ApiOperation(value = "Editar contrato existente")
    public ResponseEntity<ContractsResponse> editContract(
            @ApiParam(value = "Json da requisição que contem o dado do contrato ser editado")
            @Valid @RequestBody ContractsRequest request) throws NotFoundException {

        return ResponseEntity.ok().body(
                this.contractsService.editContract(request)
        );
    }

    @DeleteMapping(path = "/delete/idContract{idContract}")
    @ApiOperation(value = "Desativa o contrato com este id existente em uma rent")
    public ResponseEntity<ContractsResponse> deleteContract(@PathVariable(value="idContract") final Long idContract){
        return ResponseEntity.ok().body(
                this.contractsService.deleteContract(idContract)
        );
    }

    @GetMapping(path = "/page/{page}/size/{size}")
    @ResponseBody
    @ApiOperation(value = "Lista contratos por página e quantidade")
    public Page<Contracts> listContractsByPageWithSize(
            @ApiParam(value = "Página que deseja visualizar iniciando em 0", example = "0")
            @PathVariable(value="page")
            int page,
            @ApiParam(value = "Quantidade de contratos a serem listados por página", example = "10")
            @PathVariable(value="size")
            int size){

        Pageable pages = PageRequest.of(page, size);
        return this.contractsService.listContractsByPage(pages);

    }


    @GetMapping(path = "page/{page}/size/{size}/idRent/{idRent}")
    @ResponseBody
    @ApiOperation(value = "Lista todos os contratos de uma RENT por página quantidade")
    public Page<Contracts> listContractsByIdRentAndPageWithSize(
            @ApiParam(value = "Página que deseja visualizar iniciando em 0", example = "0")
            @PathVariable(value="page")
                    int page,
            @ApiParam(value = "Quantidade de contratos a serem listados por página", example = "10")
            @PathVariable(value="size")
                    int size,
            @PathVariable(value="idRent")
                    Long idRent
    ){

        Pageable pages = PageRequest.of(page, size);

        return this.contractsService.listContractsByPageAndIdRents(pages, idRent);

    }

    @GetMapping(path = "getContractById/idContract/{idContract}")
    @ResponseBody
    @ApiOperation(value = "Retorna um contrato por idContract")
    public ResponseEntity<ContractsResponse> getContractById(
              @PathVariable(value="idContract")
            Long idContract)throws NotFoundException{

        return ResponseEntity.ok().body(
                this.contractsService.getContractById(idContract)
        );

    }
}
