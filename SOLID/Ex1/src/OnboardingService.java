import java.util.*;

public class OnboardingService {
    private final StudentStore store;
    private final ConsoleInput parser;
    private final StudentValidator validator;
    private final OnboardingPrinter printer;

    public OnboardingService(ConsoleInput parser, StudentValidator validator,
                             StudentStore store, OnboardingPrinter printer) {
        this.parser = parser;
        this.validator = validator;
        this.store = store;
        this.printer = printer;
    }

    public void registerFromRawInput(String raw) {
        printer.printInput(raw);

        Map<String, String> fields = parser.parse(raw);
        List<String> errors = validator.validate(fields);

        if (!errors.isEmpty()) {
            printer.printErrors(errors);
            return;
        }

        String id = IdUtil.nextStudentId(store.count());
        StudentRecord rec = new StudentRecord(id,
                fields.get("name"), fields.get("email"),
                fields.get("phone"), fields.get("program"));

        store.save(rec);
        printer.printConfirmation(id, store.count(), rec);
    }
}
