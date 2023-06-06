package alugueldecarros;

import alugueldecarros.enums.RolesEnum;
import alugueldecarros.enums.SexEnum;
import alugueldecarros.models.*;
import alugueldecarros.repository.AddressRepository;
import alugueldecarros.repository.RoleRepository;
import alugueldecarros.repository.UserRepository;
import alugueldecarros.service.CityService;
import alugueldecarros.service.StateService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Date;

@SpringBootApplication
public class AluguelCarrosJavaApplication {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private CityService cityService;

	@Autowired
	private StateService stateService;

	@Autowired
	private AddressRepository addressRepository;

	public static void main(String[] args) {
		SpringApplication.run(AluguelCarrosJavaApplication.class, args);
	}

	@PostConstruct
	@Transactional
	public void createFirstAdminUser() throws NotFoundException {
		User admin = this.userRepository.findOneByEmailAndDeletedAtIsNull("admin@admin.com");

		Role admRole = this.roleRepository.findByName(RolesEnum.ADMIN.getCode());

		if(admin == null){
			System.out.println("Creating first admin default account...");

			Cities city = this.cityService.findByCity("BH");
			States states = this.stateService.findByUf("MG");
			Address address = this.addressRepository.save(new Address(
					"Rua",
					123,
					"bairro",
					city,
					states,
					"30690740"
			));

			admin = new User("Admin",
					"admin@admin.com",
					this.bcryptEncoder.encode("12345678"),
					new Date(),
					"33853056",
					"33853056",
					"123456789",
					address,
					SexEnum.MALE,
					Collections.singletonList(admRole)
				);
		}else if(admin.getRoles().stream().noneMatch(role -> role.equals(admRole))){
			System.out.println("Adding role to existing Admin account...");
			admin.setRoles(Collections.singletonList(admRole));
		}else{
			return;
		}

		this.userRepository.save(admin);
	}
}
