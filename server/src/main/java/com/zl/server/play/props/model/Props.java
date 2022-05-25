package com.zl.server.play.props.model;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "props")
@Getter
public class Props {

    @Id
    private Integer id;

    private String name;

    private Integer count;

}
