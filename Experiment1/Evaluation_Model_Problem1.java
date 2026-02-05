interface ModerationRule {
    int apply(int marks);
}

class AttendanceModeration implements ModerationRule {
    public int apply(int marks) {
        return marks + 5;
    }
}

class DifficultyModeration implements ModerationRule {
    public int apply(int marks) {
        return marks + 10;
    }
}

class ManualModeration implements ModerationRule {
    public int apply(int marks) {
        return marks + 3;
    }
}

abstract class EvaluationProcess {

    protected ModerationRule moderationRule;

    public EvaluationProcess(ModerationRule moderationRule) {
        this.moderationRule = moderationRule;
    }

    public final void evaluate() {
        int theory = collectTheoryMarks();
        int lab = collectLabMarks();
        int total = calculateFinalScore(theory, lab);
        total = moderationRule.apply(total);
        generateGrade(total);
    }

    protected abstract int collectTheoryMarks();
    protected abstract int collectLabMarks();
    protected abstract int calculateFinalScore(int theory, int lab);
    protected abstract void generateGrade(int finalScore);
}

class BTechEvaluation extends EvaluationProcess {

    public BTechEvaluation(ModerationRule rule) {
        super(rule);
    }

    protected int collectTheoryMarks() {
        return 70;
    }

    protected int collectLabMarks() {
        return 80;
    }

    protected int calculateFinalScore(int theory, int lab) {
        return (theory * 60 / 100) + (lab * 40 / 100);
    }

    protected void generateGrade(int score) {
        System.out.println("B.Tech Grade: " + (score >= 60 ? "PASS" : "FAIL"));
    }
}

class MCAEvaluation extends EvaluationProcess {

    public MCAEvaluation(ModerationRule rule) {
        super(rule);
    }

    protected int collectTheoryMarks() {
        return 65;
    }

    protected int collectLabMarks() {
        return 35;
    }

    protected int calculateFinalScore(int theory, int lab) {
        return (theory + lab) / 2;
    }

    protected void generateGrade(int score) {
        System.out.println("MCA Grade: " + (score >= 55 ? "PASS" : "FAIL"));
    }
}

class PhDEvaluation extends EvaluationProcess {

    public PhDEvaluation(ModerationRule rule) {
        super(rule);
    }

    protected int collectTheoryMarks() {
        return 80;
    }

    protected int collectLabMarks() {
        return 20;
    }

    protected int calculateFinalScore(int theory, int lab) {
        return theory + lab;
    }

    protected void generateGrade(int score) {
        System.out.println("PhD Result: " + (score >= 70 ? "APPROVED" : "REJECTED"));
    }
}

public class Main {
    public static void main(String[] args) {

        EvaluationProcess eval1 =
                new BTechEvaluation(new AttendanceModeration());

        EvaluationProcess eval2 =
                new MCAEvaluation(new DifficultyModeration());

        EvaluationProcess eval3 =
                new PhDEvaluation(new ManualModeration());

        eval1.evaluate();
        eval2.evaluate();
        eval3.evaluate();
    }
}
