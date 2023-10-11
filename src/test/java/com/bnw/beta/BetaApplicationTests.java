package com.bnw.beta;

import com.bnw.beta.domain.admin.dao.EdupostDAO;
import com.bnw.beta.domain.admin.dto.EdupostDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;

@SpringBootTest
class BetaApplicationTests {

	@Test
	void contextLoads() {
		for (int i= 1; i <= 100; i++) {
			EdupostDTO params = new EdupostDTO();
			params.setEdupost_title(i+"번 게시글 목록");
			params.setMember_id("dumy");
		}
	}
}
