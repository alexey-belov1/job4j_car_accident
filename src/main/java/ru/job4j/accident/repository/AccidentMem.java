package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {

    public static AccidentMem INSTANCE = new AccidentMem();

    private Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    private AtomicInteger ACCIDENT_ID = new AtomicInteger(1);

    private AccidentMem() {
        Accident accident1 = new Accident();
        accident1.setName("accident1");
        accident1.setAddress("address1");
        accident1.setText("text1");
        save(accident1);
        Accident accident2 = new Accident();
        accident2.setName("accident2");
        accident2.setAddress("address2");
        accident2.setText("text2");
        save(accident2);
    }

    public Collection<Accident> findAllAccidents() {
        return accidents.values();
    }

    public void save(Accident accident) {
        if (accident.getId() == 0) {
            accident.setId(ACCIDENT_ID.incrementAndGet());
        }
        accidents.put(accident.getId(), accident);
    }

    public Accident findByIdAccident(int id) {
        return accidents.get(id);
    }
}