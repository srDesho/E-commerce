package com.cristianml.dataProvider;

import com.cristianml.models.CartModel;
import com.cristianml.models.OrderModel;
import com.cristianml.security.model.RoleEnum;
import com.cristianml.security.model.RoleModel;
import com.cristianml.security.model.UserModel;
import static org.mockito.Mockito.mock;

import java.util.*;

public class UserDataProvider {

    public static List<UserModel> userListMock() {
        return Arrays.asList(
                UserModel.builder()
                        .id(1L)
                        .name("John Doe")
                        .username("johndoe@gmail.com")
                        .password("123456")
                        .address("123 Main St")
                        .profileImage("profile1.jpg")
                        .pincode("12345")
                        .isEnable(true)
                        .accountNoExpired(true)
                        .accountNoLocked(true)
                        .credentialNoExpired(true)
                        .roleList(new HashSet<>())
                        .cart(null)
                        .orderList(Arrays.asList(new OrderModel()))
                        .build(),
                UserModel.builder()
                        .id(2L)
                        .name("Jane Smith")
                        .username("janesmith@gmail.com")
                        .password("123456")
                        .address("456 Oak Ave")
                        .profileImage("profile2.jpg")
                        .pincode("54321")
                        .isEnable(true)
                        .accountNoExpired(true)
                        .accountNoLocked(true)
                        .credentialNoExpired(true)
                        .roleList(new HashSet<>())
                        .cart(null)
                        .orderList(Arrays.asList(new OrderModel()))
                        .build(),
                UserModel.builder()
                        .id(3L)
                        .name("Michael Johnson")
                        .username("michaelj@gmail.com")
                        .password("123456")
                        .address("789 Elm Rd")
                        .profileImage("profile3.jpg")
                        .pincode("98765")
                        .isEnable(true)
                        .accountNoExpired(true)
                        .accountNoLocked(true)
                        .credentialNoExpired(true)
                        .roleList(new HashSet<>())
                        .cart(null)
                        .orderList(Arrays.asList(new OrderModel()))
                        .build()
        );
    }

    public static Optional<UserModel> optionalUserMock() {
        return Optional.of(
          UserModel.builder()
                  .id(1L)
                  .name("Cristian Montaño")
                  .username("cristianml@gmail.com")
                  .password("123456")
                  .address("Plan 3000")
                  .profileImage("profile.png")
                  .pincode("123")
                  .isEnable(true)
                  .accountNoLocked(true)
                  .accountNoExpired(true)
                  .credentialNoExpired(true)
                  .roleList(Set.of(RoleModel.builder().id(1L).roleEnum(RoleEnum.ADMIN).permissionList(null).build()))
                  .cart(CartModel.builder().id(1L).user(mock(UserModel.class)).cartItems(new ArrayList<>()).build())
                  .orderList(List.of(
                          new OrderModel(),
                          new OrderModel()
                  ))
                  .build()
        );
    }

    public static Optional<UserModel> optionalFindByUsernameMock(String username) {

        return Optional.of(
                UserModel.builder()
                        .id(1L)
                        .name("Cristian Montaño")
                        .username("cristianml@gmail.com")
                        .password("123456")
                        .address("Plan 3000")
                        .profileImage("profile.png")
                        .pincode("123")
                        .isEnable(true)
                        .accountNoLocked(true)
                        .accountNoExpired(true)
                        .credentialNoExpired(true)
                        .roleList(Set.of(RoleModel.builder().id(1L).roleEnum(RoleEnum.ADMIN).permissionList(null).build()))
                        .cart(CartModel.builder().id(1L).user(mock(UserModel.class)).cartItems(new ArrayList<>()).build())
                        .orderList(List.of(
                                new OrderModel(),
                                new OrderModel()
                        ))
                        .build()
        );

    }

    public static UserModel createUserMock(UserModel userModel){

        return UserModel.builder()
                .id(userModel.getId())
                .name(userModel.getName())
                .username(userModel.getUsername())
                .password(userModel.getPassword())
                .address(userModel.getAddress())
                .profileImage(userModel.getProfileImage())
                .pincode(userModel.getPincode())
                .isEnable(userModel.isEnable())
                .accountNoLocked(userModel.isAccountNoLocked())
                .accountNoExpired(userModel.isAccountNoExpired())
                .credentialNoExpired(userModel.isCredentialNoExpired())
                .roleList(userModel.getRoleList())
                .cart(userModel.getCart())
                .orderList(userModel.getOrderList())
                .build();

        }
}
