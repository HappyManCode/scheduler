package me.happyman.scheduler.event;

import lombok.AllArgsConstructor;
import me.happyman.scheduler.appuser.AppUser;
import me.happyman.scheduler.appuser.AppUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final AppUserRepository userRepository;

    public Event create(Event event) {
        eventRepository.save(event);

        return event;
    }

    public List<Event> getUserEvents(String userName) {
        AppUser user = userRepository.findAppUserByEmail(userName).orElseThrow();

        return eventRepository.findAllByUserId(user.getId()).stream().toList();
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
}
