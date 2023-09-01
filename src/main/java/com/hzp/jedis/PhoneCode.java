package com.hzp.jedis;

import redis.clients.jedis.Jedis;

import java.util.Random;

/**
 * @Classname PhoneCode
 * @Description TODO
 * @Version 1.0.0
 * @Date 2022/6/13 15:40
 * @Created by ASUS
 */
public class PhoneCode {
    public static void main(String[] args) {
        String code = getCode();
        System.out.println(code);
        verifyCode("15270673628",code);
        getRedisCode("15270673628",code);
    }

    //3.验证码校验
    public static void getRedisCode(String phone,String code){
        //从redis获取验证码
        Jedis jedis = new Jedis("192.168.139.120",6379);
        jedis.auth("1234");
        //验证码key
        //保证每个手机发的验证码保存到自己的手机唯一性
        String countKey="VerifyCode"+phone+":code";
        String redisCode = jedis.get(countKey);
        //判断
        if(redisCode.equals(code)){
            System.out.println("成功");
        }else {
            System.out.println("失败");
        }

    }


    //2.每个手机每天只能发送三次，验证码放到redis中，设置过期时间
    public static void verifyCode(String phone,String code){
        //连接redis
        Jedis jedis = new Jedis("192.168.139.120",6379);
        jedis.auth("1234");
        //拼接Key
        //保证每个手机发的验证码保存到自己的手机唯一性
        String countKey="VerifyCode"+phone+":count";
        //验证码key
        String codeKey="VerifyCode"+phone+":code";
        //每个手机每天只能发送三次
        String count=jedis.get(countKey);
        if(count==null){//说明该手机用户是今天第一次发送
            //设置发送次数是1
            //Redis Setex 命令为指定的 key 设置值及其过期时间。
            jedis.setex(countKey,60*60*24,"1");
        }else if(Integer.parseInt(count)<=2){
            //设置发送次数+1
            jedis.incr(countKey);
        }else{
            //发送三次以后就不发送
            System.out.println("今天发送次数已经超过三次");
            jedis.close();
            //超过三次就不再进行下面代码操作
            return;
        }
//        String vcode = getCode();
        jedis.setex(codeKey,120,code);
        jedis.close();
    }
    //1.生成6位数字验证码i
    public static String getCode(){
        Random random = new Random();
        String code="";
        for (int i = 0; i < 6; i++) {
            //限制只要10以内的数字
            int rand = random.nextInt(10);
            code+=rand;
        }
        return code;
    }
}
