package com.zl.server.play.quest.model;

import com.zl.server.commons.AbstractBlobModelEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "quests")
public class Quest extends AbstractBlobModelEntity<QuestBox> {


}
