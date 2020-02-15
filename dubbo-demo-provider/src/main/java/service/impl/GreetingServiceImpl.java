package service.impl;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.config.support.Parameter;

import service.GreetingService;

@Path("httpTest")
@Service(timeout = 5000,protocol="rest")
public class GreetingServiceImpl implements GreetingService {

	@GET
	@Path("/getbyid/{name}")
	//@Consumes("application/json")
	//@Produces("application/json;charset=UTF-8")
//	public String hello(@PathParam("name") String name) {
	public String hello(String name) {
		System.out.println("Hello Service is calling :-----"+name+"-----");
		String greetMessage = "Hello, " + name;
		return greetMessage;
	}



}
