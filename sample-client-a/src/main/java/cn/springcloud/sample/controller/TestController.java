package cn.springcloud.sample.controller;

import cn.springcloud.sample.api.TestAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController implements TestAPI {

    @Autowired
    Environment environment;

    @Override
	public Integer add(Integer a, Integer b){
		return a + b;
	}

    @Override
	public Integer sub(Integer a, Integer b){
		return a - b;
	}

    @Override
	public Integer mul(Integer a, Integer b){
		return a * b;
	}

    @Override
	public Integer div(Integer a, Integer b){
		return a / b;
	}
}
