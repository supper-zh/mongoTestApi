package com.example.mongotestapi;

import com.example.mongotestapi.domain.Book;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;
import java.util.regex.Pattern;

@SpringBootTest
class MongoTestApiApplicationTests {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Test
    void contextLoads() {
        Book book = new Book();
//        插入10条数据
        int i =0,sum=10;
        for (i = 10; i < sum; i++) {
            book.setId(i);
            book.setName("test"+i);
            book.setType("test");
            book.setDescription("testMongoDB");
            mongoTemplate.save(book);
        }
    }

//    查询集合（表）全部
    @Test
    void find(){
        List<Book> all = mongoTemplate.findAll(Book.class);
        System.out.println(all);
    }

    //根据id查询
    @Test
    public void findById(){
        Book book = mongoTemplate.findById(1, Book.class);
        System.out.println(book);
    }

//    条件查询
    @Test
    public void findUserList(){
        Query query = new Query(Criteria.where("name").is("testMongoDB0").and("type").is("testMongoDB"));
        List<Book> books = mongoTemplate.find(query, Book.class);
        System.out.println(books);
    }

//    模糊查询
//    Pattern.compile函数用法
@Test
public void findLikeUserList(){
//        name like test
    String name = "est";
    String regex = String.format("%s%s%s", "^.*", name, ".*$");
        /*1、在使用Pattern.compile函数时，可以加入控制正则表达式的匹配行为的参数：
        Pattern Pattern.compile(String regex, int flag)
        2、regex设置匹配规则
        3、Pattern.CASE_INSENSITIVE,这个标志能让表达式忽略大小写进行匹配。*/
    Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
    //创建一个query对象（用来封装所有条件对象)，再创建一个criteria对象（用来构建条件）
    Query query = new Query(//构建查询条件
            Criteria.where("name").regex(pattern));
    List<Book> books = mongoTemplate.find(query, Book.class);
    System.out.println(books);
}

//    分页查询带条件
//分页查询（带条件）
@Test
public void pageLikeUserList(){
    int pageNo = 1;//设置当前页
    int pageSize = 3;//设置每页显示的记录数

    //条件构建
    String name = "est";
    String regex = String.format("%s%s%s", "^.*", name, ".*$");
     /*1、在使用Pattern.compile函数时，可以加入控制正则表达式的匹配行为的参数：
     Pattern Pattern.compile(String regex, int flag)
     2、regex设置匹配规则
     3、Pattern.CASE_INSENSITIVE,这个标志能让表达式忽略大小写进行匹配。*/
    Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
    //创建一个query对象（用来封装所有条件对象)，再创建一个criteria对象（用来构建条件）
    Query query = new Query(//构建查询条件
            Criteria.where("name").regex(pattern));

    //分页构建
    //查询数来集合（表）中的总记录数
    long count = mongoTemplate.count(query, Book.class);
    List<Book> users = mongoTemplate.find(
            query.skip((pageNo - 1) * pageSize).limit(pageSize), Book.class);
    System.out.println(count);
    System.out.println(users);
}

    //修改操作
    @Test
    public void  updateUser(){
        //根据id查询
        Book book = mongoTemplate.findById(3, Book.class);
        System.out.println(book);
        //修改值
        book.setName("test_03");
        book.setType("no");
//        user.setEmail("test_02@qq.com");

        //调用方法实现修改
        Query query = new Query(Criteria.where("_id").is(book.getId()));
        Update update = new Update();
        update.set("name",book.getName());
        update.set("typt",book.getType());
//        update.set("email",book.getEmail());
        //调用mongoTemplate的修改方法实现修改
        UpdateResult upsert = mongoTemplate.upsert(query, update, Book.class);
        long modifiedCount = upsert.getModifiedCount();//获取到修改受影响的行数
        System.out.println("受影响的条数："+modifiedCount);
    }
    //删除条件
    @Test
    public void deleteBook(){
        Query query = new Query(Criteria.where("_id").is(4));
        DeleteResult remove = mongoTemplate.remove(query, Book.class);
        long deletedCount = remove.getDeletedCount();
        System.out.println("删除的条数："+deletedCount);
    }


}
