package com.zl.server.play.base.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountBox {
    private boolean joining;
    private AttrStorage attrStorage;
    private EquipmentStorage equipmentStorage;
}
