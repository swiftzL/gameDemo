package com.zl.server.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    private Integer id;

    private String username;

    private String password;

    private Integer level;

}
