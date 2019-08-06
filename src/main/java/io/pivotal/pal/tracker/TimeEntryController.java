package io.pivotal.pal.tracker;

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

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository=timeEntryRepository;
    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> listEntry =timeEntryRepository.list();
        ResponseEntity response=new ResponseEntity(listEntry, HttpStatus.OK);
        return response;
    }

    @GetMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {

        TimeEntry entry =timeEntryRepository.find(id);
        if(entry!=null){
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
        ResponseEntity response=new ResponseEntity(entry, HttpStatus.OK);
        return response;}
        else{
            ResponseEntity response=new ResponseEntity(entry, HttpStatus.NOT_FOUND);
            return response;
        }
    }

    @DeleteMapping("/time-entries/{id}")
    public ResponseEntity delete(@PathVariable long id) {
/*        if(timeEntryRepository.find(id) == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }*/
        timeEntryRepository.delete(id);
        ResponseEntity response=new ResponseEntity( HttpStatus.NO_CONTENT);
        return response;
    }

    @PostMapping("/time-entries")
    public ResponseEntity create(@RequestBody TimeEntry timeEntry) {
        TimeEntry entry =timeEntryRepository.create(timeEntry);
        ResponseEntity response=new ResponseEntity(entry, HttpStatus.CREATED);
        return response;


    }
}
