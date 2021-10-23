package com.wang.springposttransaction.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.springposttransaction.entity.Person;

/**
 * @author lucksoul 王吉慧
 * @version 1.0
 * @date 2019-12-22 21:25
 */
public interface PersonService extends IService<Person> {
    /**
     * 成功的增加一条记录，查看事务的执行过程
     * @param person
     */
    void savePersonSuccess(Person person);

    /**
     * 失败的增加一条记录，查看事务的执行过程
     * @param person
     */
    void savePersonFailed(Person person);
}
