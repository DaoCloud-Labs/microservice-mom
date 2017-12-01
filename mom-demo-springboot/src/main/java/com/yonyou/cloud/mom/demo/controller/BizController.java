package com.yonyou.cloud.mom.demo.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yonyou.cloud.mom.demo.service.BizService;


@RestController
@RequestMapping("/biz")
public class BizController {

	@Resource
	private BizService bizService;
	

	@RequestMapping("/login")
	public String sendMsg(String name) throws Exception {
		return bizService.saveLoginUser(name);
	}

 
}
