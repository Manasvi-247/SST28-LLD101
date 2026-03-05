interface PlagiarismCheckable {
    int check(Submission s);
}

interface Gradable {
    int grade(Submission s, Rubric r);
}

interface ReportWritable {
    String write(Submission s, int plag, int code);
}
