package com.main;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	
	public static void main(String[] args) throws IOException {
		System.out.println("进入1");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		context.start();
//		while(true){
//			System.in.read();
//			if(false){
//				break;
//			}
//		}
		 
		System.in.read();
		System.out.println("结束");
	}
	
}
