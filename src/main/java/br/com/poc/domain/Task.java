package br.com.poc.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Builder
@ToString
@EqualsAndHashCode(exclude = "id")
public class Task {
    private int id;
    private String subject;
    private String description;
    private LocalDate start;
    private LocalDate end;
}
