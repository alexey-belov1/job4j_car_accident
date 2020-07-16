package ru.job4j.accident.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.job4j.accident.model.Accident;

import java.sql.PreparedStatement;
import java.util.Collection;

public class AccidentJdbcTemplate {

    private final JdbcTemplate jdbc;

    private final RowMapper<Accident> mapper = (rs, row) -> {
        Accident accident = new Accident();
        accident.setId(rs.getInt("id"));
        accident.setName(rs.getString("name"));
        accident.setText(rs.getString("text"));
        accident.setAddress(rs.getString("address"));
        return accident;
    };

    public AccidentJdbcTemplate(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void save(Accident accident) {
        if (accident.getId() == 0) {
            create(accident);
        } else {
            update(accident);
        }
    }

    private void create(Accident accident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement("insert into accident (name, text, address) values (?, ?, ?)", new String[]{"id"});
            ps.setString(1, accident.getName());
            ps.setString(2, accident.getText());
            ps.setString(3, accident.getAddress());
            return ps;
        }, keyHolder);
        accident.setId(keyHolder.getKey().intValue());
    }

    private void update(Accident accident) {
        jdbc.update("update accident set name = ?, text = ?, address = ? where id = ?",
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getId()
        );
    }

    public Collection<Accident> findAllAccidents() {
        return jdbc.query("select * from accident", mapper);
    }

    public Accident findByIdAccident(int id) {
        return jdbc.queryForObject("select * from accident where id = ?", mapper, id);
    }
}