package com.example.task3.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TestDTO {
    private int intField;
    private String stringField;
    private LocalDateTime dateField;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if ((obj instanceof TestDTO other)) {
            return Objects.equals(this.stringField, other.stringField)
                    && Objects.equals(this.dateField, other.dateField)
                    && this.intField == other.intField;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(stringField, intField, dateField);
    }
}
