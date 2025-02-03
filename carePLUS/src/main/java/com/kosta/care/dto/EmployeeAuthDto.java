package com.kosta.care.dto;



import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmployeeAuthDto {
    private Long id;
    private String identity;

    public EmployeeAuthDto(Long id, String identity) {
        this.id = id;
        this.identity = identity;
    }

}
