package com.codesnippet.SpringBatchDemo.config;

import com.codesnippet.SpringBatchDemo.Person;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonObjectReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class PersonJsonItemReader {
    public ResourceAwareItemReaderItemStream<Person> reader() {
        return new JsonItemReaderBuilder<Person>()
                .name("personJsonItemReader")
                .resource(new ClassPathResource("people.json")) // JSON file location
                .jsonObjectReader(new JacksonJsonObjectReader<>(Person.class))
                .strict(true)  // Set to true to fail fast if JSON is invalid
                .build();
    }
}
