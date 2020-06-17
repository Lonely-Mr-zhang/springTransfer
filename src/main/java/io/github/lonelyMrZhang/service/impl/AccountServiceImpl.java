package io.github.lonelyMrZhang.service.impl;

import io.github.lonelyMrZhang.dao.IAccountDao;
import io.github.lonelyMrZhang.entity.Account;
import io.github.lonelyMrZhang.service.IAccountService;
import io.github.lonelyMrZhang.utils.TransactionManager;

import java.util.List;

/**
 * @description: 账户业务层实现类
 * @author: lonely.mr.zhang
 * @date: 2020/6/15 11:31 下午
 */
public class AccountServiceImpl implements IAccountService {

    private IAccountDao accountDao;

    private TransactionManager transactionManager;

    public void setTransactionManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void setAccountDao(IAccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public List<Account> findAllAccount() {
        return accountDao.findAllAccount();
    }

    @Override
    public Account findAccountById(Integer id) {
       return accountDao.findAccountById(id);
    }

    @Override
    public void saveAccount(Account account) {
        accountDao.saveAccount(account);
    }

    @Override
    public void updateAccount(Account account) {
        accountDao.updateAccount(account);
    }

    @Override
    public void deleteAccount(Integer id) {
        accountDao.deleteAccount(id);
    }

    /**
     * 对accountDao的每一次操作，都会获取一个连接，都是一个单独的事务，所以当转出账户跟新成功，而后抛出异常时，转账就会出现错误。
     *
     * 需要使用ThreadLocal对象把Connection和当前线程绑定，从而使一个线程中只有一个能控制事务的对象
     */
    @Override
    public void transfer(String sourceName, String targetName, Float money) {
        //2.1、根据名称查询转出账户
        Account source = accountDao.findAccountByName(sourceName);
        //2.2、根据名称查询转入账户
        Account target = accountDao.findAccountByName(targetName);
        //2.3、转出账户减钱
        source.setMoney(source.getMoney()-money);
        //2.4、转入账户加钱
        target.setMoney(source.getMoney()+money);
        //2.5、跟新转出账户
        accountDao.updateAccount(source);
        int i = 1/0;
        //6、跟新转入账户
        accountDao.updateAccount(target);
    }
}
