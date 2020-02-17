package service;

public interface GreetingService {

	
	String hello(String name);
	
	String hello_1(String name);
	
	String hello_2(String name);

	
	
	boolean setData(String key,String value);
	String getData(String key);
}
