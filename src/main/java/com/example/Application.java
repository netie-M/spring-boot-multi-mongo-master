package com.example;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.config.props.MultipleMongoProperties;
import com.example.model.repository.primary.PrimaryRepository;
import com.example.model.repository.secondary.SecondaryRepository;

@Slf4j
@EnableConfigurationProperties(MultipleMongoProperties.class)
@SpringBootApplication(exclude = MongoAutoConfiguration.class)
public class Application {

	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PrimaryRepository primaryRepository;

	@Autowired
	private SecondaryRepository secondaryRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		log.info("************************************************************");
//		log.info("Start printing mongo objects");
//		log.info("************************************************************");
//		this.primaryRepository
//				.save(new PrimaryMongoObject(null, "Primary database plain object"));
//
//		this.secondaryRepository
//				.save(new SecondaryMongoObject(null, "Secondary database plain object"));
//
//		List<PrimaryMongoObject> primaries = this.primaryRepository.findAll();
//		for (PrimaryMongoObject primary : primaries) {
//			log.info(primary.toString());
//		}
//
//		List<SecondaryMongoObject> secondaries = this.secondaryRepository.findAll();
//
//		for (SecondaryMongoObject secondary : secondaries) {
//			log.info(secondary.toString());
//		}
//
//		log.info("************************************************************");
//		log.info("Ended printing mongo objects");
//		log.info("************************************************************");
//
//	}
}
