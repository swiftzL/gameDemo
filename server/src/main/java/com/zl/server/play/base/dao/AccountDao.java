package com.zl.server.play.base.dao;


import com.zl.server.play.base.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDao extends JpaRepository<Account,Integer> {
    Account findAccountByUsernameAndPassword(String username,String password);
    boolean existsAccountByUsername(String username);
}
