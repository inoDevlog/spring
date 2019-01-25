package org.ino.controller;

import org.ino.domain.SampleVO;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // JSP와 같은 뷰를 만들어 내지 않는 대신에 데이터 자체를 반환.(ex:단순 문자열 /JSON/XML)
@RequestMapping("/sample")
public class SampleController {

	@RequestMapping("/hello")
	public String sayHello() {
		return "Hello World";
	}

	@RequestMapping("/sendVO")
	public SampleVO sendVO() {

		SampleVO vo = new SampleVO();
		vo.setFirstName("길동");
		vo.setLastName("홍");
		vo.setMno(123);

		return vo;
	}

}
