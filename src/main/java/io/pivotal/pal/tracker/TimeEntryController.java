package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.ServiceMode;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TimeEntryController {

    public final TimeEntryRepository timeEntryRepository;
    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;

    public TimeEntryController(TimeEntryRepository timeEntryRepository,  MeterRegistry meterRegistry) {
        this.timeEntryRepository=timeEntryRepository;
        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {
        actionCounter.increment();
        List<TimeEntry> listEntry =timeEntryRepository.list();
        ResponseEntity response=new ResponseEntity(listEntry, HttpStatus.OK);
        return response;
    }

    @GetMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {

        TimeEntry entry =timeEntryRepository.find(id);

        if(entry!=null){
            actionCounter.increment();
        ResponseEntity response=new ResponseEntity(entry, HttpStatus.OK);
        return response;}
        else{
            ResponseEntity response=new ResponseEntity(entry, HttpStatus.NOT_FOUND);
            return response;
        }
    }

    @PutMapping("/time-entries/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody TimeEntry expected) {
        TimeEntry entry =timeEntryRepository.update(id, expected);
        if(entry!=null){
            actionCounter.increment();
        ResponseEntity response=new ResponseEntity(entry, HttpStatus.OK);
        return response;}
        else{
            ResponseEntity response=new ResponseEntity(entry, HttpStatus.NOT_FOUND);
            return response;
        }
    }

    @DeleteMapping("/time-entries/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        timeEntryRepository.delete(id);
        actionCounter.increment();
        timeEntrySummary.record(timeEntryRepository.list().size());
        ResponseEntity response=new ResponseEntity( HttpStatus.NO_CONTENT);
        return response;
    }

    @PostMapping("/time-entries")
    public ResponseEntity create(@RequestBody TimeEntry timeEntry) {
        TimeEntry entry =timeEntryRepository.create(timeEntry);
        actionCounter.increment();
        timeEntrySummary.record(timeEntryRepository.list().size());
        ResponseEntity response=new ResponseEntity(entry, HttpStatus.CREATED);
        return response;


    }
}
