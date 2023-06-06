package alugueldecarros.controller;

import alugueldecarros.models.RequestEntity.UserRequest;
import alugueldecarros.models.User;
import alugueldecarros.models.dto.UserDto;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/create")
    @ApiOperation(value = "Criar novo usuário")
    //@PreAuthorize("@authorityChecker.isAllowed({'ADMIN','DEF'})")
    public ResponseEntity<UserDto> createUser(
            @ApiParam(value = "Json da requisição que contem o dado do usuario a ser salvo")
            @Valid @RequestBody UserRequest request) throws NotFoundException {
        UserDto userDto = this.userService.create(request);
        return ResponseEntity.ok().body(
                userDto
        );
    }


    @PostMapping(path = "/edit")
    @ApiOperation(value = "Editar usuário existente")
    public ResponseEntity<UserDto> editUser(
            @ApiParam(value = "Json da requisição que contem o dado a ser editado")
            @Valid @RequestBody UserRequest request) throws NotFoundException {

        return ResponseEntity.ok().body(
                this.userService.editUser(request)
        );
    }

//    @Secure({RolesEnum.ADMIN})
    @DeleteMapping(path = "/delete/{email}")
    @ApiOperation(value = "Desativa usuário existente")
    public ResponseEntity<UserDto> deleteUser(@PathVariable(value="email") final String email){
        return ResponseEntity.ok().body(
                this.userService.deleteUser(email)
        );
    }

    @DeleteMapping(path = "/delete")
    @ApiOperation(value = "Desativa usuário existente")
    public ResponseEntity<UserDto> deleteLoggedUser(){
        return ResponseEntity.ok().body(
                this.userService.deleteLoggedUser()
        );
    }


    @GetMapping(path = "/page/{page}/size/{size}")
    @ResponseBody
    @ApiOperation(value = "Lista usuários por página quantidade")
    public Page<User> listUsersByPageWithSize(
            @ApiParam(value = "Página que deseja visualizar iniciando em 0", example = "0")
            @PathVariable(value="page")
            int page,
            @ApiParam(value = "Quantidade de usuários a serem listados por página", example = "10")
            @PathVariable(value="size")
            int size){

        Pageable pages = PageRequest.of(page, size);
        return this.userService.listUsersByPage(pages);

    }

    @PreAuthorize("@authorityChecker.isAllowed({'ADMIN','FINANCIAL','SELLERS','THIRDPARTY','CLIENT'})")
    @GetMapping(path = "page/{page}/size/{size}/name/{name}")
    @ResponseBody
    @ApiOperation(value = "Lista usuários por página quantidade")
    public Page<User> listUserByNameAndPageWithSize(
            @ApiParam(value = "Página que deseja visualizar iniciando em 0", example = "0")
            @PathVariable(value="page")
                    int page,
            @ApiParam(value = "Quantidade de usuários a serem listados por página", example = "10")
            @PathVariable(value="size")
                    int size,
            @PathVariable(value="name")
                    String name
    ){

        Pageable pages = PageRequest.of(page, size);

        return this.userService.listUsersByPageAndName(pages, name);

    }

    @PreAuthorize("@authorityChecker.isAllowed({'ADMIN','FINANCIAL','SELLERS','THIRDPARTY','CLIENT'})")
    @GetMapping(path = "getuserbyid/userId/{userId}")
    @ResponseBody
    @ApiOperation(value = "Lista usuários por página quantidade")
    public ResponseEntity<User> getUserById(
              @PathVariable(value="userId")
            Long userId)throws NotFoundException{

        return ResponseEntity.ok().body(
                this.userService.getUserById(userId)
        );

    }
}
