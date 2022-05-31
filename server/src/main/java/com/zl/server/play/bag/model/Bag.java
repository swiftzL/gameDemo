package com.zl.server.play.bag.model;

import com.zl.server.commons.AbstractBlobModelEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "bags")
public class Bag extends AbstractBlobModelEntity<BagBox> {

}
