package com.codesnippet.SpringBatchDemo.config;

import com.codesnippet.SpringBatchDemo.Person;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

//@Slf4j
@Component
public class PersonProcessor implements ItemProcessor<Person, Person> {

	@Override
	public Person process(Person person) throws Exception {
		try {
			person.setFirstName(person.getFirstName().toUpperCase());
			person.setLastName(person.getLastName().toUpperCase());
			return person;
		} catch (Exception e) {
//			log.info("⛔ Error processing person: " + person + " - " + e.getMessage());
			throw e;
		}
	}
}