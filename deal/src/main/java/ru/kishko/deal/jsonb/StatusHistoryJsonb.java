package ru.kishko.deal.jsonb;

import lombok.*;
import ru.kishko.api.enums.ChangeType;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StatusHistoryJsonb {
    private String status;
    private Timestamp timestamp;
    private ChangeType changeType;
}
