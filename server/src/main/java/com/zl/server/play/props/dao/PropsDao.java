package com.zl.server.play.props.dao;

import com.zl.server.play.props.model.Props;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropsDao extends JpaRepository<Props, Integer> {
}
