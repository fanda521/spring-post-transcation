package com.wang.springposttransaction.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.wang.springposttransaction.entity.Person;
import com.wang.springposttransaction.mapper.PersonMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lucksoul 王吉慧
 * @version 1.0
 * @date 2019-12-01 18:47
 */

@RestController
@RequestMapping(value = "/com/wang/person")
public class PersonController {
    @Resource
    private PersonMapper personMapper;


    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public int insert(@RequestBody Person person1)
    {
        System.out.println("param="+person1);

        Person person=new Person();
        person.setfId(21);
        person.setfName("小菊21");
        person.setfAge(31);
        person.setfBirthday(LocalDateTime.now());
        person.setfWeigth(55.5f);
        person.setfIsSingle(true);
        person.setfSex("女");
        int insert = personMapper.insert(person);
        return insert;
    }



    @RequestMapping(value = "deleteById",method = RequestMethod.POST)
    public  int deleteById(@RequestBody Person param){
        System.out.println("param="+param);
        int deleteId = personMapper.deleteById(2);

        System.out.println("deleteId="+deleteId);
        return deleteId;
    }


    public int deleteByMap(){
        Map map=new HashMap();
        map.put("c_name","小菊");
        int i = personMapper.deleteByMap(map);
        System.out.println("i="+i);//更新的记录条数
        return i;
    }


    
    public void delete(){
        QueryWrapper<Person> queryWrapper=new QueryWrapper<>();
        queryWrapper.ge("c_age",17);
        int i = personMapper.delete(queryWrapper);
        System.out.println("i="+i);//更新的记录条数
    }

    

    public void deleteBatchIds(){
        List list=new ArrayList();
        list.add(1);
        list.add(3);
        int i = personMapper.deleteBatchIds(list);
        System.out.println("i="+i);//更新的记录条数
    }


    
    public void updateById(){
        Person p=new Person();
         p.setfId(2);
         p.setfAge(23);
         p.setfName("哈利");
        int i = personMapper.updateById(p);
        System.out.println("i="+i);//更新的记录条数

    }


    
    public void  update(){
        Person p=new Person();
        p.setfAge(22);
        p.setfName("詹姆");
        QueryWrapper<Person> queryWrapper=new QueryWrapper<>();
        queryWrapper.ge("c_id",3);
        int i = personMapper.update(p, queryWrapper);
        System.out.println("i="+i);//更新的记录条数

    }

    
    public void  selectById(){
        Person person = personMapper.selectById(1);
        System.out.println(person);

    }

    
    public void  selectBatchIds(){
        List list=new ArrayList();
        list.add(1);
        list.add(2);
        list.add(4);
        List list1 = personMapper.selectBatchIds(list);
        list1.forEach(System.out::println);

    }


    
    public void  selectByMap(){
        Map map=new HashMap();
        map.put("c_age",22);
        List list1 = personMapper.selectByMap(map);
        list1.forEach(System.out::println);

    }


    public void  selectOne(){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.ge("c_age",22);
        Person person = personMapper.selectOne(queryWrapper);
        System.out.println(person);

    }


    
    public void  selectCount(){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.ge("c_age",22);
        Integer count = personMapper.selectCount(queryWrapper);
        System.out.println("count="+count);

    }


    
    public void  selectList(){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.ge("c_age",22);
        List list = personMapper.selectList(queryWrapper);
        list.forEach(System.out::println);

    }


    
    public void  selectMaps(){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.ge("c_age",22);
        List list = personMapper.selectMaps(queryWrapper);
        list.forEach(System.out::println);

    }


    
    public void  selectObjs(){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.ge("c_age",22);
        List list = personMapper.selectObjs(queryWrapper);
        list.forEach(System.out::println);

    }


    public void  selectPage(){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.ge("c_age",22);
        Page<Person> page=new Page<>(1,3);
        IPage iPage = personMapper.selectPage(page, queryWrapper);
        System.out.println("getPages="+iPage.getPages());
        System.out.println("getCurrent="+iPage.getCurrent());
        System.out.println("getSize="+iPage.getSize());
        System.out.println("getTotal="+iPage.getTotal());
        System.out.println("getRecords="+iPage.getRecords());
        iPage.getRecords().forEach(System.out::println);


    }

    public void  selectMapsPage(){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.ge("c_age",22);
        Page<Person> page=new Page<>(1,3);
        IPage iPage = personMapper.selectPage(page, queryWrapper);
        System.out.println("getPages="+iPage.getPages());
        System.out.println("getCurrent="+iPage.getCurrent());
        System.out.println("getSize="+iPage.getSize());
        System.out.println("getTotal="+iPage.getTotal());
        System.out.println("getRecords="+iPage.getRecords());
        iPage.getRecords().forEach(System.out::println);


    }

}
