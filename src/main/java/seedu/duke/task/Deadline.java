package seedu.duke.task;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Deadline extends Task {
    private final LocalDate by;

    public Deadline(String moduleCode, String description, LocalDate by) {
        super(moduleCode, description, false);
        this.by = by;
    }

    public Deadline(String moduleCode, String description, boolean isDone, LocalDate by) {
        super(moduleCode, description, isDone);
        this.by = by;
    }

    public LocalDate getBy() {
        return by;
    }

    public long getDaysLeft() {
        return ChronoUnit.DAYS.between(LocalDate.now(), by);
    }

    @Override
    protected char getTypeCode() {
        return 'D';
    }

    @Override
    protected String encodeExtra() {
        return by.toString();
    }

    @Override
    public String formatForList(int index) {
        return super.formatForList(index) + " (by: " + by + ", " + getDaysLeft() + " days left)";
    }
}
