package com.gyj.yingx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "yx_admin")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Admin {
    @Id
    private String id;

    private String username;

    private String password;

}