package com.codesnippet.SpringBatchDemo.config;

import com.codesnippet.SpringBatchDemo.Person;
import org.springframework.batch.core.listener.SkipListenerSupport;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.stereotype.Component;

@Component
public class PersonSkipListener extends SkipListenerSupport<Person, Person> {

    @Override
    public void onSkipInRead(Throwable t) {
        if (t instanceof FlatFileParseException) {
            System.err.println("⛔ Skipped bad CSV row: " + ((FlatFileParseException) t).getInput());
        } else {
            System.err.println("⛔ Error reading record: " + t.getMessage());
        }
    }

    @Override
    public void onSkipInProcess(Person item, Throwable t) {
        System.err.println("⛔ Skipped processing person: " + item + " due to error: " + t.getMessage());
    }

    @Override
    public void onSkipInWrite(Person item, Throwable t) {
        System.err.println("⛔ Skipped writing person: " + item + " due to error: " + t.getMessage());
    }
}

