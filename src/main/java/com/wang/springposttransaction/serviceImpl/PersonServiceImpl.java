package com.wang.springposttransaction.serviceImpl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wang.springposttransaction.entity.Person;
import com.wang.springposttransaction.service.PersonService;
import com.wang.springposttransaction.transcation.MyTransactionSynchronizationAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author lucksoul 王吉慧
 * @version 1.0
 * @date 2019-12-22 21:25
 */
@Service
public class PersonServiceImpl extends ServiceImpl<BaseMapper<Person>,Person> implements PersonService {
    @Autowired
    private MyTransactionSynchronizationAdapter myTransactionSynchronizationAdapter;

    /**
     * 成功的增加一条记录，查看事务的执行过程
     *
     * @param person
     */
    @Override
    @Transactional
    public void savePersonSuccess(Person person) {
        boolean save = save(person);
        System.out.println("save:"+save);
        TransactionSynchronizationManager.registerSynchronization(myTransactionSynchronizationAdapter);

        System.out.println("this method complete....");
    }

    /**
     * 失败的增加一条记录，查看事务的执行过程
     *
     * @param person
     */
    @Override
    @Transactional
    public void savePersonFailed(Person person) {
        boolean save = save(person);
        System.out.println("save:"+save);
        int i=1/0;
        TransactionSynchronizationManager.registerSynchronization(myTransactionSynchronizationAdapter);

        System.out.println("this method complete....");
    }
}
