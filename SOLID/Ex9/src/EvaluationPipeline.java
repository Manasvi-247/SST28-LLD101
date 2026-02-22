public class EvaluationPipeline {
    // DIP violation – high-level module depends directly on concrete low-level modules
    public void evaluate(Submission sub) {
        PlagiarismChecker checker = new PlagiarismChecker();
        int plag = checker.check(sub);
        System.out.println("PlagiarismScore=" + plag);

        Rubric rubric = new Rubric();
        CodeGrader grader = new CodeGrader();
        int code = grader.grade(sub, rubric);
        System.out.println("CodeScore=" + code);

        ReportWriter writer = new ReportWriter();
        String reportName = writer.write(sub, plag, code);
        System.out.println("Report written: " + reportName);

        int total = plag + code;
        String result = (total >= 90) ? "PASS" : "FAIL";
        System.out.println("FINAL: " + result + " (total=" + total + ")");
    }
}
