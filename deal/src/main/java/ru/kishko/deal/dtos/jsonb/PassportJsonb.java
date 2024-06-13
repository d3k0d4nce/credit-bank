package ru.kishko.deal.dtos.jsonb;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PassportJsonb {
    private String series;
    private String number;
    private String issueBranch;
    private Date issueDate;
}
