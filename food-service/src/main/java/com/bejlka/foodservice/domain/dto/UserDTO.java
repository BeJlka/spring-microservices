package com.bejlka.foodservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String login;
    private String name;
    private String address;
    private CartDTO cart;
    private List<OrderDTO> orderList;
}
