public interface ClubAdminTools {
}

interface FinanceTool extends ClubAdminTools {
    void addIncome(double amt, String note);
    void addExpense(double amt, String note);
}

interface MinutesTool extends ClubAdminTools {
    void addMinutes(String text);
}

interface EventTool extends ClubAdminTools {
    void createEvent(String name, double budget);
    int getEventsCount();
}
