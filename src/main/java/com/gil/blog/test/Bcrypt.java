package com.gil.blog.test;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.sun.xml.bind.v2.runtime.output.Encoded;

public class Bcrypt {

	@Test
	public void decoding() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		
		if(encoder.matches("", "$2a$10$ClvDTVC5.4U/OhFGmQ3Ud.6vXMS97MBFQB7k2GR/7yPOtw0uYoTL6")){
			System.out.println("일치합니다.");
		}else {
			System.out.println("불일치입니다.");
		}
	}
}
