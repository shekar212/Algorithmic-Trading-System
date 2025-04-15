package com.example.demo.dto;

import java.util.ArrayList;


import lombok.Data;

@Data
public class UserOrdersDTO {
    private MetaData meta;
    private ArrayList<OrderDTO> orders;
}
