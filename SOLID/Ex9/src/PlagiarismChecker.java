public class PlagiarismChecker implements PlagiarismCheckable {
    @Override
    public int check(Submission s) {
        return (s.code.contains("class") ? 12 : 40);
    }
}
