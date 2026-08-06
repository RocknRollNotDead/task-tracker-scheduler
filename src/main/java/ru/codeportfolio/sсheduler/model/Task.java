package ru.codeportfolio.sсheduler.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Entity
@Table(name = "tasks")

public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String text;

    @JoinColumn(nullable = false)
    @ManyToOne
    private User owner;

    @Column(nullable = false)
    private Status status;

    @Column
    private Timestamp timestamp;

    public Task(String name, String text, User owner) {
        this.name = name;
        this.text = text;
        this.owner = owner;
        this.status = Status.IN_PROGRESS;
    }

    public Task() {

    }

}
