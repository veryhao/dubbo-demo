package service.impl;

import java.sql.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;

import data.StudentMapper;
import dto.StudentEntity;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisShardInfo;
import service.GreetingService;

@Path("httpTest")
@Service(timeout = 5000, protocol = "rest")
public class GreetingServiceImpl implements GreetingService {

	/**
	 * 问号传参 http://127.0.0.1:9088/httpTest/getbyid?name=123
	 */
	@GET
	@Path("/getbyid")
	public String hello(@QueryParam("name") String name) {
		System.out.println("hello Service is calling :-----" + name + "-----");
		String greetMessage = "Hello, " + name;
		testMysql();
		return greetMessage;
	}

	/**
	 * URI传参123是参数，但如果入参是id，刚好有一个url也是getbyid/id的，就还是会进URI的请求，不会进getbyid/id的请求
	 * http://127.0.0.1:9088/httpTest/getbyid/123
	 */
	@GET
	@Path("/getbyid/{name}")
	public String hello_1(@PathParam("name") String name) {
		System.out.println("hello_1 Service is calling :-----" + name + "-----");
		String greetMessage = "Hello, " + name;
		return greetMessage;
	}

	@GET
	@Path("/getbyid/id")
	public String hello_2(@QueryParam("name") String name) {
		System.out.println("hello_2 Service is calling :-----" + name + "-----");
		String greetMessage = "Hello, " + name;
		return greetMessage;
	}

	/**
	 * 获取键和值后，向redis写值
	 */
	@GET
	@Path("/setvalue")
	public boolean setData(@QueryParam("key") String key, @QueryParam("value") String value) {
		// redis的路径ip
		String h = "192.168.1.103";
		// 密码
		String passwd = "qweqwe123";
		/**
		 * 引入类 将redis服务器的ip地址进行一个加入
		 */
		JedisShardInfo jedisShardInfo = new JedisShardInfo(h);
		/**
		 * 设置一个redis的密码 如果没有设置的密码的话 则可能会出现 NOAUTH Authentication required.
		 * 身份验证出现问题
		 */
		jedisShardInfo.setPassword(passwd);
		Jedis jedis = new Jedis(jedisShardInfo);
//		Jedis jedis = jedisPool.getResource();
		String r = jedis.set(key, value);
		System.out.println("写入结果：" + r);
		return true;
	}

	
	@GET
	@Path("/getvalue")
	public String getData(@QueryParam("key") String key) {
		// redis的路径ip
//		String h = "127.0.0.1:6379";
		String h = "192.168.1.103";
		// 密码
		String passwd = "qweqwe123";
		/**
		 * 引入类 将redis服务器的ip地址进行一个加入
		 */
		JedisShardInfo jedisShardInfo = new JedisShardInfo(h);
		/**
		 * 设置一个redis的密码 如果没有设置的密码的话 则可能会出现 NOAUTH Authentication required.
		 * 身份验证出现问题
		 */
		jedisShardInfo.setPassword(passwd);
		Jedis jedis = new Jedis(jedisShardInfo);
//		Jedis jedis = jedisPool.getResource();
		String greetMessage = jedis.get(key);
		System.out.println("key:" + key  + "取出值为:" + greetMessage);
		return greetMessage;
	}
	
	@Autowired  
    private StudentMapper studentMapper;  
	
	public void testMysql(){
		StudentEntity se = new StudentEntity();
		se.setStudentBirthday(new Date(2020,1,23));
		se.setStudentID("id:1");
		se.setStudentSex("女");
		se.setStudentName("sunying");
		studentMapper.insertStudent(se);
//		StudentEntity entity = studentMapper.getStudent("10000013");  
		System.out.println("写入成功");
//        System.out.println("name：" + entity.getStudentName());  
	}
	
	
	//Redis服务器IP
    private static String ADDR = "192.168.0.103";
    
    //Redis的端口号
    private static int PORT = 6379;
    
    //访问密码
    private static String AUTH = "qweqwe123";
    
    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int MAX_ACTIVE = 500;
    
    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 8;
    
    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT = 10000;
    
    private static int TIMEOUT = 10000;
    
    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;
    

    private static JedisPool jedisPool = null;

//    static {
//        try {
//            JedisPoolConfig config = new JedisPoolConfig();
//            config.setMaxIdle(MAX_IDLE);
//            config.setTestOnBorrow(TEST_ON_BORROW);
//            jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
//            Jedis jedis = jedisPool.getResource();
//            System.out.println("初始化完成");
//        } catch (Exception e) {   
//        	e.printStackTrace();
//            System.out.println("redis初始化失败" + e.getMessage());
//        }
//    }

}
