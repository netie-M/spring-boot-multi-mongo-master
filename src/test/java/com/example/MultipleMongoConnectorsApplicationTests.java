package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.model.repository.primary.PrimaryMongoObject;
import com.example.model.repository.primary.PrimaryRepository;
import com.example.model.repository.secondary.SecondaryMongoObject;
import com.example.model.repository.secondary.SecondaryRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MultipleMongoConnectorsApplicationTests {

	@Autowired PrimaryRepository primaryRepository;
	@Autowired SecondaryRepository secondaryRepository;
	@Test
	public void contextLoads() {
		primaryRepository.save(new PrimaryMongoObject(null , "primaryRepository"));
		secondaryRepository.save(new SecondaryMongoObject(null, "secondaryRepository"));
	}

}
