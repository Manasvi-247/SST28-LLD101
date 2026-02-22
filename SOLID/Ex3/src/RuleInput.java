import java.util.*;

interface EligibilityRule {
    String evaluate(StudentProfile s);
}

class DisciplinaryFlagRule implements EligibilityRule {
    @Override
    public String evaluate(StudentProfile s) {
        if (s.disciplinaryFlag != LegacyFlags.NONE) return "disciplinary flag present";
        return null;
    }
}

class CgrRule implements EligibilityRule {
    @Override
    public String evaluate(StudentProfile s) {
        if (s.cgr < 8.0) return "CGR below 8.0";
        return null;
    }
}

class AttendanceRule implements EligibilityRule {
    @Override
    public String evaluate(StudentProfile s) {
        if (s.attendancePct < 75) return "attendance below 75";
        return null;
    }
}

class CreditsRule implements EligibilityRule {
    @Override
    public String evaluate(StudentProfile s) {
        if (s.earnedCredits < 20) return "credits below 20";
        return null;
    }
}

public class RuleInput {
    public double minCgr = 8.0;
    public int minAttendance = 75;
    public int minCredits = 20;
}
