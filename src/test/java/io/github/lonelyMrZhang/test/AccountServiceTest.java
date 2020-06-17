package io.github.lonelyMrZhang.test;

import io.github.lonelyMrZhang.entity.Account;
import io.github.lonelyMrZhang.service.IAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @description:
 * @author: lonely.mr.zhang
 * @date: 2020/6/16 12:40 上午
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:bean.xml")
public class AccountServiceTest {

    @Autowired
    private IAccountService accountService;


    @Test
    public void testTransfer(){
        accountService.transfer("aaa","bbb",100f);
    }

}
