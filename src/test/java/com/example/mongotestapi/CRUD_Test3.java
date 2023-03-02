package com.example.mongotestapi;

import com.example.mongotestapi.domain.User;
import com.example.mongotestapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import java.util.List;

/**
 *
 * 基于MongoRepository开发CRUD
 *
 *
**/
@SpringBootTest
public class CRUD_Test3 {

    @Autowired
    private UserRepository userRepository;

    //添加操作
    @Test
    public void save(){
        User user = new User();
        user.setName("ertong");
        user.setAge(20);
        user.setEmail("ertong@qq.com");
        User user1 = userRepository.save(user);
        System.out.println(user1);
    }

    //查询所有
    @Test
    public void findAll(){
        List<User> lists = userRepository.findAll();
        for(User user:lists){
            System.out.println(user);
        }
    }

    //根据id查询
    @Test
    public void findById(){
        User user = userRepository.findById("640053948b417b27a0ef68ec").get();
        System.out.println(user);
    }
    //条件查询
    @Test
    public void findUserList(){
        User user = new User();
        user.setName("ertong");
        user.setAge(20);
        Example<User> example = Example.of(user);
        List<User> all = userRepository.findAll(example);
        System.out.println(all);
    }
    //模糊条件查询
    @Test
    public void findLikeUserList(){
        ///创建匹配器，即如何使用查询条件
        ExampleMatcher matcher = ExampleMatcher.matching()//构建对象
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)//改变默认字符串匹配方式：模糊查询
                .withIgnoreCase(true);//改变默认大小写忽略方式：忽略大小写

        User user = new User();
        user.setName("e");
        user.setAge(20);
        Example<User> example = Example.of(user,matcher);
        List<User> all = userRepository.findAll(example);
        System.out.println(all);
    }
    //分页查询
    @Test
    public void findPageUserAll(){
        //设置分页参数
        //0代表第一页
        Pageable pageable = PageRequest.of(0, 3);
        //查询条件
        User user = new User();
        user.setName("test");
        Example<User> userExample = Example.of(user);//查询条件
        Page<User> page = userRepository.findAll(userExample, pageable);
        System.out.println(page.getContent());
    }
    //修改操作
    @Test
    public void updateUser(){
        //先根据id出要修改的用户
        User user = userRepository.findById("60b5a37209aa4d6face6bacd").get();
        //设置修改的值
        user.setName("haha");
        user.setAge(23);
        user.setEmail("hah@qq.com");
        User user1 = userRepository.save(user);
        System.out.println(user1);
    }
    //删除
    @Test
    public void delete(){
        userRepository.deleteById("60b5a37209aa4d6face6bacd");
    }

}
