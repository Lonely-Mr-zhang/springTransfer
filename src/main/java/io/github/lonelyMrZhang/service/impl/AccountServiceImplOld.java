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
public class AccountServiceImplOld implements IAccountService {

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
        try {
            //1、开启事务
            transactionManager.beginTransaction();
            //2、执行操作
            List<Account> accounts = accountDao.findAllAccount();
            //3、提交事务
            transactionManager.commit();
            //4、返回结果
            return accounts;
        } catch (Exception e) {
            //回滚
            transactionManager.rollback();
            throw new RuntimeException(e);
        } finally {
            //释放连接
            transactionManager.release();
        }
    }

    @Override
    public Account findAccountById(Integer id) {
        try {
            //1、开启事务
            transactionManager.beginTransaction();
            //2、执行操作
            Account account = accountDao.findAccountById(id);
            //3、提交事务
            transactionManager.commit();
            //4、返回结果
            return account;
        } catch (Exception e) {
            //回滚
            transactionManager.rollback();
            throw new RuntimeException(e);
        } finally {
            //释放连接
            transactionManager.release();
        }
    }

    @Override
    public void saveAccount(Account account) {
        try {
            //1、开启事务
            transactionManager.beginTransaction();
            //2、执行操作
            accountDao.saveAccount(account);
            //3、提交事务
            transactionManager.commit();
        } catch (Exception e) {
            //回滚
            transactionManager.rollback();
        } finally {
            //释放连接
            transactionManager.release();
        }
    }

    @Override
    public void updateAccount(Account account) {
        try {
            //1、开启事务
            transactionManager.beginTransaction();
            //2、执行操作
            accountDao.updateAccount(account);
            //3、提交事务
            transactionManager.commit();
        } catch (Exception e) {
            //回滚
            transactionManager.rollback();
        } finally {
            //释放连接
            transactionManager.release();
        }
    }

    @Override
    public void deleteAccount(Integer id) {
        try {
            //1、开启事务
            transactionManager.beginTransaction();
            //2、执行操作
            accountDao.deleteAccount(id);
            //3、提交事务
            transactionManager.commit();
        } catch (Exception e) {
            //回滚
            transactionManager.rollback();
        } finally {
            //释放连接
            transactionManager.release();
        }
    }

    /**
     * 对accountDao的每一次操作，都会获取一个连接，都是一个单独的事务，所以当转出账户跟新成功，而后抛出异常时，转账就会出现错误。
     *
     * 需要使用ThreadLocal对象把Connection和当前线程绑定，从而使一个线程中只有一个能控制事务的对象
     */
    @Override
    public void transfer(String sourceName, String targetName, Float money) {
        try {
            //1、开启事务
            transactionManager.beginTransaction();
            //2、执行操作
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
            //3、提交事务
            transactionManager.commit();
        } catch (Exception e) {
            //回滚
            transactionManager.rollback();
            e.printStackTrace();
        } finally {
            //释放连接
            transactionManager.release();
        }
    }
}
