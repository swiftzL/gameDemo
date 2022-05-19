package com.zl.server.dao;


import com.zl.server.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDao extends JpaRepository<Account,Integer> {
}
