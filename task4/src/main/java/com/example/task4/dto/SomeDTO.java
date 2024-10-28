package com.example.task4.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SomeDTO {
    private final int intValue;
    private final String stringValue;

    public SomeDTO(
            @JsonProperty("intValue") int intValue,
            @JsonProperty("stringValue") String stringValue
    ) {
        this.intValue = intValue;
        this.stringValue = stringValue;
    }
}
