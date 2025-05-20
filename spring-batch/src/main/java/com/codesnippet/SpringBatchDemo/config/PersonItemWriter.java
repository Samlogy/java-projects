package com.codesnippet.SpringBatchDemo.config;

import com.codesnippet.SpringBatchDemo.Person;
import com.codesnippet.SpringBatchDemo.PersonRepository;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.stereotype.Component;

@Component
public class PersonItemWriter {

    private final PersonRepository personRepository;

    public PersonItemWriter(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public RepositoryItemWriter<Person> writer() {
        RepositoryItemWriter<Person> writer = new RepositoryItemWriter<>();
        writer.setRepository(personRepository);
        writer.setMethodName("save");
        return writer;
    }
}
