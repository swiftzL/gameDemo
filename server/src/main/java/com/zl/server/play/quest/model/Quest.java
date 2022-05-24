package com.zl.server.play.quest.model;

import com.zl.server.commons.AbstractBlobModelEntity;
import com.zl.server.play.quest.packet.QuestBox;
import lombok.Data;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "quests")
public class Quest extends AbstractBlobModelEntity<QuestBox> {


}
