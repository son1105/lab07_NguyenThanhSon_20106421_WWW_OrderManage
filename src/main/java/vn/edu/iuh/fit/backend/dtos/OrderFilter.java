package vn.edu.iuh.fit.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderFilter {
    private long employeeId;
    private String fromDate;
    private String toDate;
}
