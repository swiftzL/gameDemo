package com.zl.server.play.base.model;

import com.zl.server.commons.AbstractBlobModelEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;


@Data
@Entity
@Table(name = "accounts")
public class Account extends AbstractBlobModelEntity<AccountBox> {

    private String username;

    private String password;

    private Integer level = 1;

}
