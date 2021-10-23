package com.wang.springposttransaction;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.wang.springposttransaction.entity.Person;
import com.wang.springposttransaction.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * 测试IService 的功能
 * @author lucksoul 王吉慧
 * @version 1.0
 * @date 2019-12-22 21:23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonServiceTest {
    @Autowired
    private PersonService personService;

    @Test
    public void testSavePersonSuccess(){
        Person p=new Person();
        p.setfIsSingle(true);
        p.setfAge(22);
        p.setfName("小琳琳");
        p.setfSex("女");

        personService.savePersonSuccess(p);
    }

    @Test
    public void testSavePersonFailed(){
        Person p=new Person();
        p.setfIsSingle(true);
        p.setfAge(22);
        p.setfName("小琳琳");
        p.setfSex("女");

        personService.savePersonFailed(p);
    }

    /**
     * // 插入一条记录（选择字段，策略插入）
     * boolean save(T entity);
     *
     * @author lucksoul 王吉慧
     * @Date 2019-12-22
     * @Time 21:30
     */
    //支持回填
    @Test
    public void testSave(){

        Person p=new Person();
        p.setfIsSingle(true);
        p.setfAge(22);
        p.setfName("小琳琳");
        p.setfSex("女");
        System.out.println("save before person="+p);
        boolean save = personService.save(p);
        System.out.println("影响行数="+save);
        System.out.println("save after person="+p);

    }

    /**
     * //插入（批量）
     * boolean saveBatch(Collection<T> entityList);
     * @author lucksoul 王吉慧
     * @Date 2019-12-22
     * @Time 21:46
     */
    @Test
    public void testSaveBatch(){
        //支持回填
        List<Person> personList=new ArrayList<>();
        for (int i=4;i<10;i++)
        {
            Person p=new Person();
            p.setfIsSingle(true);
            p.setfAge(20+i);
            p.setfName("小琳琳"+i);
            p.setfSex("女");
            System.out.println("save before person="+p);
            personList.add(p);
        }
        boolean save = personService.saveBatch(personList,4);
        System.out.println("影响行数="+save);
        System.out.println("save after personList=");
        personList.forEach(System.out::println);

    }

    /*// TableId 注解存在更新记录，否插入一条记录
    boolean saveOrUpdate(T entity);
    // 根据updateWrapper尝试更新，否继续执行saveOrUpdate(T)方法
    boolean saveOrUpdate(T entity, Wrapper<T> updateWrapper);
    // 批量修改插入
    boolean saveOrUpdateBatch(Collection<T> entityList);
    // 批量修改插入
    boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize);*/

    /**
     * @author lucksoul 王吉慧
     * @Date 2019-12-28
     * @Time 23:04
     * @return:a
     * @param: a
     * "DEBUG==>  Preparing: INSERT INTO t_person ( c_name, c_age, c_isSingle, c_sex ) VALUES ( ?, ?, ?, ? )
     * ""DEBUG==> Parameters: 小卓凡10(String), 20(Integer), true(Boolean), 女(String)
     * ""DEBUG<==    Updates: 1
     */
    @Test
    public void testSaveOrUpdate(){
        //支持回填，但是出现一个问题，mybatisplus底层的反射id方法是getFId()，
        //要不然报错，所以自己在person类中加了一个getFId()方法
        Person p=new Person();
        //p.setfId(33);
        p.setfIsSingle(true);
        p.setfAge(20);
        p.setfName("小卓凡"+10);
        p.setfSex("女");
        System.out.println("save before person="+p);
        boolean b = personService.saveOrUpdate(p);
        System.out.println("运行结果："+b);
        System.out.println("save after person="+p);

    }

    /**
     * @author lucksoul 王吉慧
     * @Date 2019-12-28
     * @Time 23:28
     * @return a
     * @paras: a
     * "DEBUG==>  Preparing: UPDATE t_person SET c_name=?, c_age=?, c_isSingle=?, c_sex=? WHERE (c_id >= ?)
     * ""DEBUG==> Parameters: 小卓凡11(String), 12(Integer), true(Boolean), 男(String), 34(Integer)
     * ""DEBUG<==    Updates: 1
     */
    @Test
    public void testSaveOrUpdate2(){
        //不支持回填
        Person p=new Person();
        //p.setfId(33);
        p.setfIsSingle(true);
        p.setfAge(12);
        p.setfName("小卓凡"+11);
        p.setfSex("男");
        System.out.println("save before person="+p);
        LambdaQueryWrapper<Person> personLambdaQueryWrapper=new LambdaQueryWrapper<>();
        personLambdaQueryWrapper.ge(Person::getfId,34);
        boolean b = personService.saveOrUpdate(p,personLambdaQueryWrapper);
        System.out.println("运行结果："+b);
        System.out.println("save after person="+p);

    }

    /**
     * @author lucksoul 王吉慧
     * @Date 2019-12-28
     * @Time 23:35
     * @return:a
     * @param: a
     * "DEBUG==>  Preparing: SELECT c_id AS fId,c_name AS fName,c_age AS fAge,c_weight AS fWeigth,
     * c_isSingle AS fIsSingle,c_birthday AS fBirthday,c_sex AS fSex FROM t_person WHERE c_id=?
     * ""DEBUG==> Parameters: 45(Integer)
     * ""TRACE<==    Columns: fId, fName, fAge, fWeigth, fIsSingle, fBirthday, fSex
     * ""TRACE<==        Row: 45, 小卓凡11, 12, null, 1, 2019-12-28 23:39:47.0, 男
     * ""DEBUG<==      Total: 1
     * ""DEBUG==>  Preparing: UPDATE t_person SET c_name=?, c_age=?, c_isSingle=?, c_sex=? WHERE c_id=?
     * ""DEBUG==> Parameters: 小卓凡11(String), 14(Integer), true(Boolean), 男(String), 45(Integer)
     * "运行结果：true
     * save after personList=
     * Person{fId=45, fName='小卓凡11', fAge=14, fSex='男', fBirthday=null, fWeigth=null, fIsSingle=true}
     *
     * "DEBUG==>  Preparing: INSERT INTO t_person ( c_name, c_age, c_isSingle, c_sex ) VALUES ( ?, ?, ?, ? )
     * ""DEBUG==> Parameters: 小A凡11(String), 14(Integer), true(Boolean), 男(String)
     * "运行结果：true
     * save after personList=
     * Person{fId=56, fName='小A凡11', fAge=14, fSex='男', fBirthday=null, fWeigth=null, fIsSingle=true}
     *
     *
     */
    @Test
    public void testSaveOrUpdateBatch(){
        //根据id判断是否存在，有的话就先查询后更新，支持回填
        //没有的话，就直接插入
        Person p=new Person();
        p.setfIsSingle(true);
        //p.setfId(45);
        p.setfAge(14);
        p.setfName("小A凡"+11);
        p.setfSex("男");
        List<Person> personList=new ArrayList<>();
        personList.add(p);
        /*for (int i=1;i<10;i++)
        {
            Person p1=new Person();
            p1.setfIsSingle(true);
            p1.setfAge(11+i);
            p1.setfName("爱爱"+i);
            p1.setfSex("女");
            System.out.println("save before person="+p1);
            personList.add(p1);
        }*/
        boolean b = personService.saveOrUpdateBatch(personList);
        System.out.println("运行结果："+b);
        System.out.println("save after personList=");
        personList.forEach(System.out::println);

    }

    /*// 根据 entity 条件，删除记录
    boolean remove(Wrapper<T> queryWrapper);
    // 根据 ID 删除
    boolean removeById(Serializable id);
    // 根据 columnMap 条件，删除记录
    boolean removeByMap(Map<String, Object> columnMap);
    // 删除（根据ID 批量删除）
    boolean removeByIds(Collection<? extends Serializable> idList);*/

    /**
     * @author lucksoul 王吉慧
     * @Date 2019-12-29
     * @Time 0:06
     * @return:a
     * @param: a
     *
     * "DEBUG==>  Preparing: DELETE FROM t_person WHERE (c_id = ?)
     * ""DEBUG==> Parameters: 55(Integer)
     * ""DEBUG<==    Updates: 1
     * "执行结果：true
     */
    @Test
    public void testRemove(){
        LambdaQueryWrapper<Person> personLambdaQueryWrapper=new LambdaQueryWrapper<>();
        personLambdaQueryWrapper.eq(Person::getfId,55);
        /*QueryWrapper<Person> personLambdaQueryWrapper=new QueryWrapper<>();
        personLambdaQueryWrapper.eq("id",38);*/
        boolean remove = personService.remove(personLambdaQueryWrapper);
        System.out.println("执行结果："+remove);

    }

    /**
     * @author lucksoul 王吉慧
     * @Date 2019-12-29
     * @Time 0:09
     * @return:a
     * @param: a
     * "DEBUG==>  Preparing: DELETE FROM t_person WHERE c_id=?
     * ""DEBUG==> Parameters: 48(Integer)
     * ""DEBUG<==    Updates: 1
     * "执行结果：true
     */
    @Test
    public void testRemoveById(){
        boolean remove = personService.removeById(48);
        System.out.println("执行结果："+remove);

    }

    /**
     * @author lucksoul 王吉慧
     * @Date 2019-12-29
     * @Time 0:13
     * @return:a
     * @param: a
     */
    @Test
    public void testRemoveByMap(){
        Map<String,Object> map=new HashMap<>();
        //要和数据的表字段名一样，不能是实体类的属性名，除非两者一样
        //构造器里的也是数据库表的字段名
        map.put("c_sex","女");
        map.put("c_name","小菊8");
        boolean remove = personService.removeByMap(map);
        System.out.println("执行结果："+remove);

    }

    /**
     * @author lucksoul 王吉慧
     * @Date 2019-12-29
     * @Time 0:21
     * @return:a
     * @param: a
     * "DEBUG==>  Preparing: DELETE FROM t_person WHERE c_id IN ( ? , ? , ? )
     * ""DEBUG==> Parameters: 34(Integer), 36(Integer), 47(Integer)
     * ""DEBUG<==    Updates: 3
     * "执行结果：true
     */
    @Test
    public void testRemoveIds(){
       List<Integer> list=new ArrayList<>();
       list.add(34);
       list.add(36);
       list.add(47);
        boolean remove = personService.removeByIds(list);
        System.out.println("执行结果："+remove);

    }

    /*// 根据 UpdateWrapper 条件，更新记录 需要设置sqlset
    boolean update(Wrapper<T> updateWrapper);
    // 根据 whereEntity 条件，更新记录
    boolean update(T entity, Wrapper<T> updateWrapper);
    // 根据 ID 选择修改
    boolean updateById(T entity);
    // 根据ID 批量更新
    boolean updateBatchById(Collection<T> entityList);
    // 根据ID 批量更新
    boolean updateBatchById(Collection<T> entityList, int batchSize);*/


    /**
     * @author lucksoul 王吉慧
     * @Date 2019-12-29
     * @Time 12:29
     * @return: a
      @params: a
      "DEBUG==>  Preparing: UPDATE t_person SET c_name=?, c_isSingle=? WHERE (c_age >= ? AND c_name LIKE ?)
      ""DEBUG==> Parameters: 憨八龟(String), false(Boolean), 22(Integer), %琳%(String)
      ""DEBUG<==    Updates: 10
      "执行结果：true
    */
    @Test
    public void testUpdate(){
        //光有构造器是不行的，要有更新的内容
        UpdateWrapper<Person> personUpdateWrapper=new UpdateWrapper<>();
        Person p=new Person();
        p.setfIsSingle(false);
        p.setfName("憨八龟");
        personUpdateWrapper.ge("c_age",22).like("c_name",'琳');
        boolean update = personService.update(p,personUpdateWrapper);
        System.out.println("执行结果："+update);
        
    }

    /**
     * @author lucksoul 王吉慧
     * @Date 2019-12-29
     * @Time 12:40
     * @return a
     * @paras: a
     * "DEBUG==>  Preparing: UPDATE t_person SET c_sex='女' ,c_age=23 WHERE (c_age > ? AND c_name LIKE ?)
     * ""DEBUG==> Parameters: 22(Integer), %小%(String)
     * ""DEBUG<==    Updates: 6
     * "执行结果：true
     */
    @Test
    public void testUpdate2(){
        //光有构造器是不行的，要有更新的内容
        UpdateWrapper<Person> personUpdateWrapper=new UpdateWrapper<>();
        /*Person p=new Person();
        p.setfIsSingle(false);
        p.setfName("憨八龟");*/
        String sql=" c_sex='女' ,c_age=23";
        personUpdateWrapper.gt("c_age",22).like("c_name", "小").setSql(sql);

        boolean update = personService.update(personUpdateWrapper);
        System.out.println("执行结果："+update);
        System.out.println("sqlset="+personUpdateWrapper.getSqlSet());
        System.out.println("getExpression="+personUpdateWrapper.getExpression());
        System.out.println("getSqlSelect="+personUpdateWrapper.getSqlSelect());
        System.out.println("getCustomSqlSegment="+personUpdateWrapper.getCustomSqlSegment());
        System.out.println("getEntity="+personUpdateWrapper.getEntity());
        System.out.println("getSqlComment="+personUpdateWrapper.getSqlComment());
        System.out.println("getSqlSegment="+personUpdateWrapper.getSqlSegment());

        /*sqlset= c_sex='女' ,c_age=23
        getExpression=com.baomidou.mybatisplus.core.conditions.segments.MergeSegments@75d982d3
        getSqlSelect=null
        getCustomSqlSegment=WHERE (c_age > #{ew.paramNameValuePairs.MPGENVAL1} AND c_name LIKE #{ew.paramNameValuePairs.MPGENVAL2})
        getEntity=null
        getSqlComment=null
        getSqlSegment=(c_age > #{ew.paramNameValuePairs.MPGENVAL1} AND c_name LIKE #{ew.paramNameValuePairs.MPGENVAL2})*/
    }

    /**
     * @author lucksoul 王吉慧
     * @Date 2019-12-29
     * @Time 12:54
     * @return:a
     * @param: a
     * "DEBUG==>  Preparing: UPDATE t_person SET c_name=?, c_isSingle=? WHERE c_id=?
     * ""DEBUG==> Parameters: 憨八龟(String), false(Boolean), 22(Integer)
     * ""DEBUG<==    Updates: 1
     * "执行结果：true
     */
    @Test
    public void testUpdateById(){
        UpdateWrapper<Person> personUpdateWrapper=new UpdateWrapper<>();
        Person p=new Person();
        p.setfId(22);
        p.setfIsSingle(false);
        p.setfName("憨八龟");
        boolean update = personService.updateById(p);
        System.out.println("执行结果："+update);

    }

    /**
     * @author lucksoul 王吉慧
     * @Date 2019-12-29
     * @Time 13:01
     * @return:a
     * @param: a
     * "DEBUG==>  Preparing: UPDATE t_person SET c_name=?, c_isSingle=?, c_sex=? WHERE c_id=?
     * ""DEBUG==> Parameters: 小精灵1(String), false(Boolean), 男(String), 24(Integer)
     * ""DEBUG==> Parameters: 小怪兽1(String), false(Boolean), 男(String), 25(Integer)
     * ""DEBUG==> Parameters: 小呆萌1(String), false(Boolean), 女(String), 29(Integer)
     * "执行结果：true
     * Person{fId=24, fName='小精灵1', fAge=null, fSex='男', fBirthday=null, fWeigth=null, fIsSingle=false}
     * Person{fId=25, fName='小怪兽1', fAge=null, fSex='男', fBirthday=null, fWeigth=null, fIsSingle=false}
     * Person{fId=29, fName='小呆萌1', fAge=null, fSex='女', fBirthday=null, fWeigth=null, fIsSingle=false}
     *
     * 如果有些实体字段不一样，他会单独执行多条不一样的
     * "DEBUG==>  Preparing: UPDATE t_person SET c_name=?, c_isSingle=? WHERE c_id=?
     * ""DEBUG==> Parameters: 小精灵1(String), false(Boolean), 24(Integer)
     * ""DEBUG==>  Preparing: UPDATE t_person SET c_name=?, c_isSingle=?, c_sex=? WHERE c_id=?
     * ""DEBUG==> Parameters: 小怪兽1(String), false(Boolean), 男(String), 25(Integer)
     * ""DEBUG==> Parameters: 小呆萌1(String), false(Boolean), 女(String), 29(Integer)
     *
     *
     *
     **/
    @Test
    public void testUpdateBatchById(){
        UpdateWrapper<Person> personUpdateWrapper=new UpdateWrapper<>();
        List<Person> list=new ArrayList<>();

        Person p=new Person();
        p.setfId(24);
        p.setfIsSingle(false);
        p.setfName("小精灵1");
        //p.setfSex("男");

        Person p1=new Person();
        p1.setfId(25);
        p1.setfIsSingle(false);
        p1.setfName("小怪兽1");
        p1.setfSex("男");

        Person p2=new Person();
        p2.setfId(29);
        p2.setfIsSingle(false);
        p2.setfName("小呆萌1");
        p2.setfSex("女");
        
        list.add(p);
        list.add(p1);
        list.add(p2);
        list.forEach(System.out::println);
        boolean update = personService.updateBatchById(list);
        System.out.println("执行结果："+update);
        list.forEach(System.out::println);

    }

    /*// 根据 ID 查询
    T getById(Serializable id);
    // 根据 Wrapper，查询一条记录。结果集，如果是多个会抛出异常，随机取一条加上限制条件 wrapper.last("LIMIT 1")
    T getOne(Wrapper<T> queryWrapper);
    // 根据 Wrapper，查询一条记录
    T getOne(Wrapper<T> queryWrapper, boolean throwEx);
    // 根据 Wrapper，查询一条记录
    Map<String, Object> getMap(Wrapper<T> queryWrapper);
    // 根据 Wrapper，查询一条记录
    <V> V getObj(Wrapper<T> queryWrapper, Function<? super Object, V> mapper);*/

    /**
     * @author lucksoul 王吉慧
     * @Date 2019-12-29
     * @Time 14:26
     * @return: a
    * @params: a
     * "DEBUG==>  Preparing: SELECT c_id AS fId,c_name AS fName,c_age AS fAge,c_weight AS fWeigth,c_isSingle AS fIsSingle,c_birthday AS fBirthday,c_sex AS fSex FROM t_person WHERE c_id=?
     * ""DEBUG==> Parameters: 44(Integer)
     * ""TRACE<==    Columns: fId, fName, fAge, fWeigth, fIsSingle, fBirthday, fSex
     * ""TRACE<==        Row: 44, 小卓凡11, 12, null, 1, 2019-12-28 23:36:00.0, 男
     * ""DEBUG<==      Total: 1
     * */
    @Test
    public void testGetById(){
        Person person = personService.getById(44);
        System.out.println("person="+person);
    }

    /**
     * @author lucksoul 王吉慧
     * @Date 2019-12-29
     * @Time 14:42
     * @return:a
     * @param: a
     * "DEBUG==>  Preparing: SELECT c_id AS fId,c_name AS fName,c_age AS fAge,c_weight AS fWeigth,c_isSingle AS fIsSingle,c_birthday AS fBirthday,c_sex AS fSex FROM t_person WHERE (c_id >= ?) limit 1
     * ""DEBUG==> Parameters: 54(Integer)
     * ""TRACE<==    Columns: fId, fName, fAge, fWeigth, fIsSingle, fBirthday, fSex
     * ""TRACE<==        Row: 54, 爱爱9, 20, null, 1, 2019-12-28 23:39:47.0, 女
     * ""DEBUG<==      Total: 1
     */
    @Test
    public void testGetOne(){
        LambdaQueryWrapper<Person> personLambdaQueryWrapper=new LambdaQueryWrapper<>();
        personLambdaQueryWrapper.ge(Person::getfId,54).last("limit 1");
        //多条记录会报错，需要加上limit 限制条件
        Person person = personService.getOne(personLambdaQueryWrapper);
        System.out.println("person="+person);
    }

    /**
     * @author lucksoul 王吉慧
     * @Date 2019-12-29
     * @Time 14:58
     * @return:a
     * @param: a
     * "DEBUG==>  Preparing: SELECT c_id AS fId,c_name AS fName,c_age AS fAge,c_weight AS fWeigth,c_isSingle AS fIsSingle,c_birthday AS fBirthday,c_sex AS fSex FROM t_person WHERE (c_id >= ?)
     * ""DEBUG==> Parameters: 54(Integer)
     * ""TRACE<==    Columns: fId, fName, fAge, fWeigth, fIsSingle, fBirthday, fSex
     * ""TRACE<==        Row: 54, 爱爱9, 20, null, 1, 2019-12-28 23:39:47.0, 女
     * ""TRACE<==        Row: 56, 小A凡11, 14, null, 1, 2019-12-28 23:49:06.0, 男
     * ""DEBUG<==      Total: 2
     * ""WARNWarn: execute Method There are  2 results.
     */
    @Test
    public void testGetOne2(){
        LambdaQueryWrapper<Person> personLambdaQueryWrapper=new LambdaQueryWrapper<>();
        personLambdaQueryWrapper.ge(Person::getfId,54);
        //多条记录会报错，需要加上limit 限制条件
        Person person = personService.getOne(personLambdaQueryWrapper,false);
        //有多个 result 是否抛出异常
        System.out.println("person="+person);
    }

    /**
     * @author lucksoul 王吉慧
     * @Date 2019-12-29
     * @Time 15:07
     * @return a
     * @paras: a
     * "DEBUG==>  Preparing: SELECT c_id AS fId,c_name AS fName,c_age AS fAge,c_weight AS fWeigth,c_isSingle AS fIsSingle,c_birthday AS fBirthday,c_sex AS fSex FROM t_person WHERE (c_id >= ?)
     * ""DEBUG==> Parameters: 54(Integer)
     * ""TRACE<==    Columns: fId, fName, fAge, fWeigth, fIsSingle, fBirthday, fSex
     * ""TRACE<==        Row: 54, 爱爱9, 20, null, 1, 2019-12-28 23:39:47.0, 女
     * ""TRACE<==        Row: 56, 小A凡11, 14, null, 1, 2019-12-28 23:49:06.0, 男
     * ""DEBUG<==      Total: 2
     * ""WARNWarn: execute Method There are  2 results.
     */
    @Test
    public void testGetMap(){
        LambdaQueryWrapper<Person> personLambdaQueryWrapper=new LambdaQueryWrapper<>();
        personLambdaQueryWrapper.ge(Person::getfId,54);
        //多条记录会报错，需要加上limit 限制条件
        Map<String, Object> map = personService.getMap(personLambdaQueryWrapper);
        //有多个 result 是否抛出异常
        System.out.println("map="+map);
    }

/*
    // 查询所有
    List<T> list();
    // 查询列表
    List<T> list(Wrapper<T> queryWrapper);
    // 查询（根据ID 批量查询）
    Collection<T> listByIds(Collection<? extends Serializable> idList);
    // 查询（根据 columnMap 条件）
    Collection<T> listByMap(Map<String, Object> columnMap);
    // 查询所有列表
    List<Map<String, Object>> listMaps();
    // 查询列表
    List<Map<String, Object>> listMaps(Wrapper<T> queryWrapper);
    // 查询全部记录
    List<Object> listObjs();
    // 查询全部记录
    <V> List<V> listObjs(Function<? super Object, V> mapper);
    // 根据 Wrapper 条件，查询全部记录
    List<Object> listObjs(Wrapper<T> queryWrapper);
    // 根据 Wrapper 条件，查询全部记录
    <V> List<V> listObjs(Wrapper<T> queryWrapper, Function<? super Object, V> mapper);
*/

    /**
     * @author lucksoul 王吉慧
     * @Date 2019-12-29
     * @Tme 15:43
    * @return: a
     * @params: a
     * "DEBUG==>  Preparing: SELECT c_id AS fId,c_name AS fName,c_age AS fAge,c_weight AS fWeigth,c_isSingle AS fIsSingle,c_birthday AS fBirthday,c_sex AS fSex FROM t_person
     * ""DEBUG==> Parameters:
     */
    @Test
    public void testList(){
        List<Person> list = personService.list();
        list.forEach(System.out::println);

    }

    /**
     * @author lucksoul 王吉慧
     * @Date 2019-12-29
     * @Time 15:46
     * @return:a
     * @param: a
     * "DEBUG==>  Preparing: SELECT c_id AS fId,c_name AS fName,c_age AS fAge,c_weight AS fWeigth,c_isSingle AS fIsSingle,c_birthday AS fBirthday,c_sex AS fSex FROM t_person WHERE (c_sex = ?)
     * ""DEBUG==> Parameters: 女(String)
     */
    @Test
    public void testList2(){
        LambdaQueryWrapper<Person> personLambdaQueryWrapper=new LambdaQueryWrapper<>();
        personLambdaQueryWrapper.eq(Person::getfSex,"女");
        List<Person> list = personService.list(personLambdaQueryWrapper);
        list.forEach(System.out::println);

    }

    /**
     * @author lucksoul 王吉慧
     * @Date 2019-12-29
     * @Time 16:04
     * @return:a
     * @param: a
     * "DEBUG==>  Preparing: SELECT c_id AS fId,c_name AS fName,c_age AS fAge,c_weight AS fWeigth,c_isSingle AS fIsSingle,c_birthday AS fBirthday,c_sex AS fSex FROM t_person WHERE c_id IN ( ? , ? , ? , ? )
     * ""DEBUG==> Parameters: 23(Integer), 24(Integer), 25(Integer), 27(Integer)
     * ""TRACE<==    Columns: fId, fName, fAge, fWeigth, fIsSingle, fBirthday, fSex
     * ""TRACE<==        Row: 23, 小呆萌, 20, null, 0, 2019-12-29 13:01:44.0, 女
     * ""TRACE<==        Row: 24, 小精灵1, 21, null, 0, 2019-12-29 13:04:05.0, 男
     * ""TRACE<==        Row: 25, 小怪兽1, 22, null, 0, 2019-12-29 13:04:05.0, 男
     * ""TRACE<==        Row: 27, 憨八龟, 24, null, 0, 2019-12-29 12:36:08.0, 女
     * ""DEBUG<==      Total: 4
     */
    @Test
    public void testListByIds(){
        List<Integer> list=new ArrayList<>();
        list.add(23);
        list.add(24);
        list.add(25);
        list.add(27);
        //list不能为空，要不然会报错
        Collection<Person> people = personService.listByIds(list);
        people.forEach(System.out::println);

    }

    /**
     * @author lucksoul 王吉慧
     * @Date 2019-12-29
     * @Time 16:10
     * @return:a
     * @param: a
     * "DEBUG==>  Preparing: SELECT c_id AS fId,c_name AS fName,c_age AS fAge,
     * c_weight AS fWeigth,c_isSingle AS fIsSingle,c_birthday AS fBirthday,c_sex AS fSex
     * FROM t_person WHERE c_sex = ? AND c_age = ?
     * ""DEBUG==> Parameters: 女(String), 22(Integer)
     * ""TRACE<==    Columns: fId, fName, fAge, fWeigth, fIsSingle, fBirthday, fSex
     * ""TRACE<==        Row: 4, 詹姆, 22, 45.6, 1, 2019-12-01 17:16:29.0, 女
     * ""TRACE<==        Row: 5, 小菊, 22, 55.5, 1, 2019-12-07 18:41:57.0, 女
     * ""TRACE<==        Row: 22, 憨八龟, 22, null, 0, 2019-12-29 12:36:08.0, 女
     * ""TRACE<==        Row: 33, 憨八龟, 22, null, 0, 2019-12-29 12:36:08.0, 女
     * ""DEBUG<==      Total: 4
     */
    @Test
    public void testListByMap(){
        Map<String,Object> map=new HashMap<>();
        map.put("c_sex","女");
        map.put("c_age",22);
        //list不能为空，要不然会报错
        Collection<Person> people = personService.listByMap(map);
        people.forEach(System.out::println);

    }

    /**
     * @author lucksoul 王吉慧
     * @Date 2019-12-29
     * @Time 16:12
     * @return:a
     * @param: a
     * "DEBUG==>  Preparing: SELECT c_id AS fId,c_name AS fName,c_age AS fAge,c_weight AS fWeigth,c_isSingle AS fIsSingle,c_birthday AS fBirthday,c_sex AS fSex FROM t_person
     * ""DEBUG==> Parameters: 
     */
    @Test
    public void testListMaps(){
        List<Map<String, Object>> maps = personService.listMaps();
        maps.forEach(System.out::println);

    }

    /**
     * @author lucksoul 王吉慧
     * @Date 2019-12-29
     * @Time 16:19
     * @return a
     * @paras: a
    "DEBUG==>  Preparing: SELECT c_id AS fId,c_name AS fName,
    c_age AS fAge,c_weight AS fWeigth,c_isSingle AS fIsSingle,
    c_birthday AS fBirthday,c_sex AS fSex FROM t_person
    WHERE (c_sex = ? AND c_name LIKE ?)
    ""DEBUG==> Parameters: 女(String), %小%(String)
     */
    @Test
    public void testListMaps2(){
        LambdaQueryWrapper<Person> personLambdaQueryWrapper=new LambdaQueryWrapper<>();
        personLambdaQueryWrapper.eq(Person::getfSex,"女").like(Person::getfName,"小");
        List<Map<String, Object>> maps = personService.listMaps(personLambdaQueryWrapper);
        maps.forEach(System.out::println);

    }

    /**
     * @author lucksoul 王吉慧
     * @Date 2019-12-29
     * @Time 16:22
     * @return:a
     * @param: a
     */
    @Test
    public void testListObjs(){
        //只返回记录的第一个字段
        List<Object> objects = personService.listObjs();
        objects.forEach(System.out::println);

    }

    /**
     * @author lucksoul 王吉慧
     * @Date 2019-12-29
     * @Time 16:26
     * @return:a
     * @param: a
     * "DEBUG==>  Preparing: SELECT c_id AS fId,c_name AS fName,
     *
     * c_age AS fAge,c_weight AS fWeigth,c_isSingle AS fIsSingle,
     * c_birthday AS fBirthday,c_sex AS fSex FROM t_person
     * WHERE (c_sex = ? AND c_name LIKE ?)
     * ""DEBUG==> Parameters: 女(String), %小%(String)
     */
    @Test
    public void testListObjs2(){
        //只返回记录的第一个字段
        LambdaQueryWrapper<Person> personLambdaQueryWrapper=new LambdaQueryWrapper<>();
        personLambdaQueryWrapper.eq(Person::getfSex,"女").like(Person::getfName,"小");
        List<Object> objects = personService.listObjs(personLambdaQueryWrapper );
        objects.forEach(System.out::println);

    }

/*
    // 无条件翻页查询
    IPage<T> page(IPage<T> page);
    // 翻页查询
    IPage<T> page(IPage<T> page, Wrapper<T> queryWrapper);
    // 无条件翻页查询
    IPage<Map<String, Object>> pageMaps(IPage<T> page);
    // 翻页查询
    IPage<Map<String, Object>> pageMaps(IPage<T> page, Wrapper<T> queryWrapper);
*/

    /**
     * @author lucksoul 王吉慧
     * @Date 2019-12-29
     * @Tme 16:39
    * @return: a
     * @params: a
     * "DEBUG==>  Preparing: SELECT COUNT(1) FROM t_person
     * ""DEBUG==> Parameters:
     * ""TRACE<==    Columns: COUNT(1)
     * ""TRACE<==        Row: 48
     * ""DEBUG==>  Preparing: SELECT c_id AS fId,c_name AS fName,c_age AS fAge,c_weight AS fWeigth,c_isSingle AS fIsSingle,c_birthday AS fBirthday,c_sex AS fSex FROM t_person LIMIT ?,?
     * ""DEBUG==> Parameters: 0(Long), 20(Long)
     */
    @Test
    public  void testPage(){
        IPage<Person> iPage=new Page<>(1,20,true);
        //Page mypage=(Page) iPage;
        //mypage.setOptimizeCountSql(false);
        IPage<Person> page = personService.page(iPage);
        System.out.println("getRecords="+page.getRecords());
        System.out.println("getPages="+page.getPages());
        System.out.println("getTotal="+page.getTotal());

    }

    /**
     * @author lucksoul 王吉慧
     * @Date 2019-12-29
     * @Time 16:53
     * @return a
     * @paras: a
     * "DEBUG==>  Preparing: SELECT COUNT(1) FROM t_person WHERE (c_sex = ? AND c_name LIKE ?)
     * ""DEBUG==> Parameters: 男(String), %小%(String)
     * ""TRACE<==    Columns: COUNT(1)
     * ""TRACE<==        Row: 14
     * ""DEBUG==>  Preparing: SELECT c_id AS fId,c_name AS fName,c_age AS fAge,c_weight AS fWeigth,c_isSingle AS fIsSingle,c_birthday AS fBirthday,c_sex AS fSex FROM t_person WHERE (c_sex = ? AND c_name LIKE ?) LIMIT ?,?
     * ""DEBUG==> Parameters: 男(String), %小%(String), 0(Long), 20(Long)
     */
    @Test
    public  void testPage2(){
        IPage<Person> iPage=new Page<>(1,20,true);
        //Page mypage=(Page) iPage;
        //mypage.setOptimizeCountSql(false);
        LambdaQueryWrapper<Person> personLambdaQueryWrapper=new LambdaQueryWrapper<>();
        personLambdaQueryWrapper.eq(Person::getfSex,"男").like(Person::getfName,"小");
        IPage<Person> page = personService.page(iPage,personLambdaQueryWrapper);
        System.out.println("getRecords="+page.getRecords());
        System.out.println("getPages="+page.getPages());
        System.out.println("getTotal="+page.getTotal());

    }

    /**
     * @author lucksoul 王吉慧
     * @Date 2019-12-29
     * @Time 17:01
     * @return a
     * @paras: a
     * "DEBUG==>  Preparing: SELECT COUNT(1) FROM t_person
     * ""DEBUG==> Parameters:
     * ""TRACE<==    Columns: COUNT(1)
     * ""TRACE<==        Row: 48
     * ""DEBUG==>  Preparing: SELECT c_id AS fId,c_name AS fName,c_age AS fAge,c_weight AS fWeigth,c_isSingle AS fIsSingle,c_birthday AS fBirthday,c_sex AS fSex FROM t_person LIMIT ?,?
     * ""DEBUG==> Parameters: 0(Long), 20(Long)
     */
    @Test
    public  void testPageMaps(){
        IPage<Person> iPage=new Page<>(1,20,true);
        IPage<Map<String, Object>> mapIPage = personService.pageMaps(iPage);
        System.out.println("getRecords="+mapIPage.getRecords());
        System.out.println("getPages="+mapIPage.getPages());
        System.out.println("getTotal="+mapIPage.getTotal());

    }

    /**
     * @author lucksoul 王吉慧
     * @Date 2019-12-29
     * @Time 17:09
     * @return:a
     * @param: a
     * "DEBUG==>  Preparing: SELECT COUNT(1) FROM t_person WHERE (c_sex = ? AND c_name LIKE ?)
     * ""DEBUG==> Parameters: 男(String), %小%(String)
     * ""TRACE<==    Columns: COUNT(1)
     * ""TRACE<==        Row: 14
     * ""DEBUG==>  Preparing: SELECT c_id AS fId,c_name AS fName,c_age AS fAge,c_weight AS fWeigth,c_isSingle AS fIsSingle,c_birthday AS fBirthday,c_sex AS fSex FROM t_person WHERE (c_sex = ? AND c_name LIKE ?) LIMIT ?,?
     * ""DEBUG==> Parameters: 男(String), %小%(String), 0(Long), 20(Long)
     */
    @Test
    public  void testPageMaps2(){
        IPage<Person> iPage=new Page<>(1,20,true);
        LambdaQueryWrapper<Person> personLambdaQueryWrapper=new LambdaQueryWrapper<>();
        personLambdaQueryWrapper.eq(Person::getfSex,"男").like(Person::getfName,"小");
        IPage<Map<String, Object>> page = personService.pageMaps(iPage, personLambdaQueryWrapper);
        System.out.println("getRecords="+page.getRecords());
        System.out.println("getPages="+page.getPages());
        System.out.println("getTotal="+page.getTotal());

    }

    /*// 查询总记录数
    int count();
    // 根据 Wrapper 条件，查询总记录数
    int count(Wrapper<T> queryWrapper);*/

    /**
     * @author lucksoul 王吉慧
     * @Date 2019-12-29
     * @Time 17:23
     * @return a
     * @paras: a
     * "DEBUG==>  Preparing: SELECT COUNT( 1 ) FROM t_person
     * ""DEBUG==> Parameters:
     * ""TRACE<==    Columns: COUNT( 1 )
     * ""TRACE<==        Row: 48
     * ""DEBUG<==      Total: 1
     */
    @Test
    public void testCount(){
        int count = personService.count();
        System.out.println("记录数="+count);

    }

    /**
     * @author lucksoul 王吉慧
     * @Date 2019-12-29
     * @Time 17:26
     * @return a
     * @paras: a
     *
     * "DEBUG==>  Preparing: SELECT COUNT( 1 ) FROM t_person WHERE (c_sex = ? AND c_name LIKE ?)
     * ""DEBUG==> Parameters: 男(String), %小%(String)
     * ""TRACE<==    Columns: COUNT( 1 )
     * ""TRACE<==        Row: 14
     * ""DEBUG<==      Total: 1
     *
     */
    @Test
    public void testCount2(){
        LambdaQueryWrapper<Person> personLambdaQueryWrapper=new LambdaQueryWrapper<>();
        personLambdaQueryWrapper.eq(Person::getfSex,"男").like(Person::getfName,"小");
        int count = personService.count(personLambdaQueryWrapper);
        System.out.println("记录数="+count);

    }

   /* // 链式查询 普通
    QueryChainWrapper<T> query();
    // 链式查询 lambda 式。注意：不支持 Kotlin
    LambdaQueryChainWrapper<T> lambdaQuery();

    // 示例：
    query().eq("column", value).one();
    lambdaQuery().eq(Entity::getId, value).list();*/

    /**
     * @author lucksoul 王吉慧
     * @Date 2019-12-29
     * @Time 17:35
     * @retun: a
     * @prams: a
     * "DEBUG==>  Preparing: SELECT c_id AS fId,c_name AS fName,c_age AS fAge,c_weight AS fWeigth,c_isSingle AS fIsSingle,c_birthday AS fBirthday,c_sex AS fSex FROM t_person WHERE (c_age = ?) limit 1 
     * ""DEBUG==> Parameters: 22(Integer)
     * ""TRACE<==    Columns: fId, fName, fAge, fWeigth, fIsSingle, fBirthday, fSex
     * ""TRACE<==        Row: 3, 詹姆, 22, 60.6, 0, 2019-12-01 17:16:29.0, 男
     * ""DEBUG<==      Total: 1
     */
   @Test
   public void testQuery(){
       Person person = personService.query().eq("c_age", 22).last("limit 1").one();
       System.out.println("person="+person);
       
   }

    /**
     * @author lucksoul 王吉慧
     * @Date 2019-12-29
     * @Time 17:42
     * @rturn: a
     *@params: a
     */
    @Test
    public void testQuery2(){
        List<Person> list = personService.query().eq("c_age", 22).list();
        list.forEach(System.out::println);

    }

    /**
     * @author lucksoul 王吉慧
     * @Date 2019-12-29
     * @Time 17:49
     * @return a
     * @paras: a"DEBUG==>  Preparing: SELECT c_id AS fId,c_name AS fName,c_age AS fAge,c_weight AS fWeigth,c_isSingle AS fIsSingle,c_birthday AS fBirthday,c_sex AS fSex FROM t_person WHERE (c_age = ? AND c_name LIKE ?)
     * ""DEBUG==> Parameters: 22(Integer), %小%(String)
     * ""TRACE<==    Columns: fId, fName, fAge, fWeigth, fIsSingle, fBirthday, fSex
     * ""TRACE<==        Row: 5, 小菊, 22, 55.5, 1, 2019-12-07 18:41:57.0, 女
     * ""TRACE<==        Row: 25, 小怪兽1, 22, null, 0, 2019-12-29 13:04:05.0, 男
     * ""DEBUG<==      Total: 2
     *
     */
    @Test
    public void testLambdaQuery(){
        List<Person> list = personService.lambdaQuery().eq(Person::getfAge,22).like(Person::getfName,"小").list();
        list.forEach(System.out::println);

    }


    /*// 链式更改 普通
    UpdateChainWrapper<T> update();
    // 链式更改 lambda 式。注意：不支持 Kotlin
    LambdaUpdateChainWrapper<T> lambdaUpdate();

    // 示例：
    update().eq("column", value).remove();
    lambdaUpdate().eq(Entity::getId, value).update(entity);*/

    /**
     * @author lucksoul 王吉慧
     * @Date 2019-12-29
     * @Time 18:00
     * @return:a
     * @param: a
     * "DEBUG==>  Preparing: UPDATE t_person SET c_weight=?, c_sex=? WHERE (c_sex = ? AND c_name LIKE ?)
     * ""DEBUG==> Parameters: 55.5(Float), 女(String), 男(String), %小%(String)
     * ""DEBUG<==    Updates: 14
     * "执行结果=true
     */
    @Test
    public void testUpdate1(){

        Person p=new Person();
        p.setfSex("女");
        p.setfWeigth(55.5f);
        boolean update = personService.update().eq("c_sex", "男").like("c_name", "小").update(p);
        System.out.println("执行结果="+update);
        System.out.println(p);
    }


}
