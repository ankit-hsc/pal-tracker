package io.pivotal.pal.tracker;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class InMemoryTimeEntryRepository  implements TimeEntryRepository{

    public long counter =1L;
    HashMap<Long,TimeEntry> repo = new HashMap<>();
        public TimeEntry create(TimeEntry timeEntry) {
       TimeEntry entry = new TimeEntry(counter, timeEntry.getProjectId(),timeEntry.getUserId(),timeEntry.getDate(),timeEntry.getHours());
        repo.put(counter,entry);
        ++counter;
        return entry;
    }

    @Override
    public TimeEntry find(long timeEntryId) {
            if(!repo.containsKey(timeEntryId)){
                return null;
            }

        return repo.get(timeEntryId);
    }

    public List<TimeEntry> list() {
       return new ArrayList<>(repo.values());
    }

    @Override
    public TimeEntry update(long eq, TimeEntry entry) {
        TimeEntry timeEntry=repo.get(eq);
        if(timeEntry!=null){
        timeEntry.setProjectId(entry.getProjectId());
        timeEntry.setUserId(entry.getUserId());
        timeEntry.setDate(entry.getDate());
        timeEntry.setHours(entry.getHours());
        return timeEntry;}
        else{return null;}
    }

    @Override
    public void delete(long timeEntryId) {
            repo.remove(timeEntryId);
    }
}
