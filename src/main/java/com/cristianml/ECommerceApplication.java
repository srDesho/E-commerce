package com.cristianml;

import com.cristianml.security.model.PermissionModel;
import com.cristianml.security.model.RoleEnum;
import com.cristianml.security.model.RoleModel;
import com.cristianml.security.model.UserModel;
import com.cristianml.security.repository.PermissionRepository;
import com.cristianml.security.repository.RoleRepository;
import com.cristianml.security.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@SpringBootApplication
public class ECommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
	}

	// ADD DATA IN THE DB
	@Bean
	@Transactional
	public CommandLineRunner init(UserRepository userRepository, RoleRepository roleRepository,
								  PermissionRepository permissionRepository) {
		return  args-> {
			// CREATE PERMISSIONS
			PermissionModel createPermission = savePermission(permissionRepository, "CREATE");
			PermissionModel readPermission = savePermission(permissionRepository, "READ");
			PermissionModel updatePermission = savePermission(permissionRepository, "UPDATE");
			PermissionModel deletePermission = savePermission(permissionRepository, "DELETE");

			// CREATE ROLES
			RoleModel roleAdmin = saveRole(roleRepository, RoleEnum.ADMIN, Set.of(createPermission, readPermission,
					updatePermission, deletePermission));
			RoleModel roleUser = saveRole(roleRepository, RoleEnum.USER, Set.of(createPermission, readPermission));

			// CREATE USERS
			UserModel cristianUser = saveUser(userRepository, "Cristian Montaño Laime", "cristianml@gmail.com",
					"$2a$10$iz6M0oKDmhf54ksubFSWWeK9SXvfOAyQd1Eni6VVLzoDDbYF1edCG", "Plan 3000",
					"cristian.png", "123456", Set.of(roleAdmin));

			UserModel danielUser = saveUser(userRepository, "Daniel Fernandez Fuentes", "daniel@gmail.com",
					"$2a$10$iz6M0oKDmhf54ksubFSWWeK9SXvfOAyQd1Eni6VVLzoDDbYF1edCG", "Plan 3000",
					"daniel.png", "123456", Set.of(roleUser));

			UserModel melUser = saveUser(userRepository, "Melani Ortiz LLanos", "mel@gmail.com",
					"$2a$10$iz6M0oKDmhf54ksubFSWWeK9SXvfOAyQd1Eni6VVLzoDDbYF1edCG", "Plan 3000",
					"mel.png", "123456", Set.of(roleUser));
		};
	}

	@Transactional
	private PermissionModel savePermission(PermissionRepository repository, String name) {
		return repository.save(PermissionModel.builder().name(name).build());
	}

	@Transactional
	private RoleModel saveRole(RoleRepository repository, RoleEnum roleEnum, Set<PermissionModel> permissionList) {
		return repository.save(RoleModel.builder().roleEnum(roleEnum).permissionList(permissionList).build());
	}

	@Transactional
	private UserModel saveUser(UserRepository repository, String name, String username, String password, String address,
							   String profileImage, String pincode, Set<RoleModel> roleList) {
		return repository.save(UserModel.builder()
						.name(name)
						.username(username)
						.password(password)
						.address(address)
						.profileImage(profileImage)
						.pincode(pincode)
						.isEnable(true)
						.accountNoExpired(true)
						.accountNoLocked(true)
						.credentialNoExpired(true)
						.roleList(roleList)
				.build());
	}
}
