package ru.kishko.deal.dtos.jsonb;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EmploymentJsonb {
    private String employmentStatus;
    private String employerINN;
    private BigDecimal salary;
    private String employmentPosition;
    private Integer workExperienceTotal;
    private Integer workExperienceCurrent;
}
