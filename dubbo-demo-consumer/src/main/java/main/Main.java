package main;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import service.GreetingService;

public class Main {



	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		System.out.println("111111111");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		context.start();
		GreetingService greetingService = (GreetingService) context.getBean("greetingService");
		String greetMessage = greetingService.hello("Eric");
		System.out.println("Consumer ==> " + greetMessage);
		greetingService.setData("bbsy1", "1bbsy");
		System.out.println("bbsy1值是:" + greetingService.getData("bbsy1"));
		
		greetingService.setData("bbsy2", "2bbsy");
		System.out.println("bbsy2值是:" + greetingService.getData("bbsy2"));
		context.destroy();
	}
	
	

}
