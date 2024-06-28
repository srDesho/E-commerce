package com.cristianml;

import com.cristianml.models.OrderModel;
import com.cristianml.security.model.UserModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class DataProvider {

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

}
