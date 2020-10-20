public class Puzzle {
    //---------------------------------Fields------------------------------------
    private String question;
    private String answer;
    private Boolean isAnswered;
    private int attends;
    //---------------------------------Methods------------------------------------

    //---------------------------------constructor------------------------------------


    public Puzzle( String question, String answer, Boolean isAnswered, int attends) {
        this.question = question;
        this.answer = answer;
        this.isAnswered = isAnswered;
        this.attends = attends;
    }

    //---------------------------------Getter and setters------------------------------------


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean getAnswered() {
        return isAnswered;
    }

    public void setAnswered(Boolean answered) {
        isAnswered = answered;
    }

    public int getAttends() {
        return attends;
    }

    public void setAttends(int attends) {
        this.attends = attends;
    }
}
