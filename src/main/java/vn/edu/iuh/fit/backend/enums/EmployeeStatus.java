package vn.edu.iuh.fit.backend.enums;

import jakarta.persistence.Enumerated;

public enum EmployeeStatus {
    TERMINATED(-1),
    IN_ACTIVE(0),

    ACTIVE(1);
    private int value;

    EmployeeStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
