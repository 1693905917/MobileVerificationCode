package com.hzp.jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JedisDemo1 {
    public static void main(String[] args) {
        //创建Jedis对象  填写redis的ip地址与存在于的端口号
        Jedis jedis = new Jedis("192.168.139.120",6379);
        //输入你在redis中设置好的密码
        String auth = jedis.auth("1234");
        System.out.println("auth:"+auth);
        //测试
        String ping = jedis.ping();
        System.out.println("ping:"+ping);
        //关闭连接
        jedis.close();
    }

    //Jedis-API: Key
    @Test
    public void test1(){
        //创建Jedis对象  填写redis的ip地址与存在于的端口号
        Jedis jedis = new Jedis("192.168.139.120",6379);
        //输入你在redis中设置好的密码
        jedis.auth("1234");
        jedis.set("k1","v1");
        jedis.set("k2","v2");
        jedis.set("k3","v3");
        Set<String> keys = jedis.keys("*");
        //输入iter按Tab
        for (String key : keys) {
            System.out.println(key);
        }
        System.out.println("**************************");
        System.out.println(jedis.exists("k1"));
        System.out.println(jedis.ttl("k1"));
        System.out.println(jedis.get("k1"));
        jedis.close();

    }


    //Jedis-API: String
    @Test
    public void test2(){
        //创建Jedis对象  填写redis的ip地址与存在于的端口号
        Jedis jedis = new Jedis("192.168.139.120",6379);
        //输入你在redis中设置好的密码
        jedis.auth("1234");
        jedis.mset("str1","v1","str2","v2","str3","v3");
        System.out.println(jedis.mget("str1","str2","str3"));
        jedis.close();
    }

    //Jedis-API: List
    @Test
    public void test3(){
        //创建Jedis对象  填写redis的ip地址与存在于的端口号
        Jedis jedis = new Jedis("192.168.139.120",6379);
        //输入你在redis中设置好的密码
        jedis.auth("1234");
        jedis.lpush("list1","list1","list2","list3");
        List<String> list1 = jedis.lrange("list1", 0, -1);
        for (String s : list1) {
            System.out.println(s);
        }
        jedis.close();
    }

    //Jedis-API: set
    @Test
    public void test4(){
        //创建Jedis对象  填写redis的ip地址与存在于的端口号
        Jedis jedis = new Jedis("192.168.139.120",6379);
        //输入你在redis中设置好的密码
        jedis.auth("1234");
        jedis.sadd("set1","val1","val2","val3");
        Set<String> set1 = jedis.smembers("set1");
        for (String s : set1) {
            System.out.println(s);
        }
        System.out.println("******************");
        Long srem = jedis.srem("set1", "val1");
        System.out.println(srem);
        jedis.close();
    }


    @Test
    public void test5(){
        //创建Jedis对象  填写redis的ip地址与存在于的端口号
        Jedis jedis = new Jedis("192.168.139.120",6379);
        //输入你在redis中设置好的密码
        jedis.auth("1234");
        //第一种方法存储
        System.out.println("第一种方法****************");
        jedis.hset("hash1","userName","lisi");
        System.out.println(jedis.hget("hash1","userName"));
        System.out.println("第二种方法****************");
        //第二种方法存储  Map
        Map<String,String> map=new HashMap<>();
        map.put("telephone","15270673628");
        map.put("address","jiangxi");
        map.put("email","1693905917@qq.com");
        jedis.hmset("hash2",map);
        List<String> hmget = jedis.hmget("hash2", "telephone", "address", "email");
        for (String s : hmget) {
            System.out.println(s);
        }
        jedis.close();
    }

    @Test
    public void test6(){
        //创建Jedis对象  填写redis的ip地址与存在于的端口号
        Jedis jedis = new Jedis("192.168.139.120",6379);
        //输入你在redis中设置好的密码
        jedis.auth("1234");
        jedis.zadd("zset01",100d,"z3");
        jedis.zadd("zset01",90d,"l4");
        jedis.zadd("zset01",80d,"w5");
        jedis.zadd("zset01",70d,"z6");
        Set<String> zset01 = jedis.zrange("zset01", 0, -1);
        for (String s : zset01) {
            System.out.println(s);
        }
        jedis.close();
    }
}
