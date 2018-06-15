package springunit;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.cache.CacheManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.smtv.rabbitmq.RmqProducer;

@RunWith(SpringJUnit4ClassRunner.class) // 表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class TestMybatis {
	private static Logger logger = Logger.getLogger(TestMybatis.class);
	// private ApplicationContext ac = null;

	// @Before
	// public void before() {
	// ac = new ClassPathXmlApplicationContext("applicationContext.xml");
	// userService = (IUserService) ac.getBean("userService");
	// }

	/**
	 * 测试mybatis和spring无缝连接
	 */
	@Resource(name="producer")  
    private RmqProducer producer;
	
	@Test
	public void test(){  
        try {  
        	System.out.println("发送消息！");
            //发送消息    
            producer.sendMessage("为什么出不来？");  
            System.out.println("结束发送消息！");
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    } 

		
}
