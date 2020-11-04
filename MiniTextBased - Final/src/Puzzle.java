public class Puzzle {
    private int puzzleID;
    private String question;
    private String answer;
    private int numAttempts;
    private String hint;
    private boolean isSolved;
    private int roomID;

    public Puzzle(int puzzleID, String question, String answer, int numAttempts, String hint, boolean isSolved, int roomID) {
        this.puzzleID = puzzleID;
        this.question = question;
        this.answer = answer;
        this.numAttempts = numAttempts;
        this.hint = hint;
        this.isSolved = isSolved;
        this.roomID = roomID;
    }

    public Puzzle() {
        this.puzzleID = puzzleID;
        this.question = question;
        this.answer = answer;
        this.numAttempts = numAttempts;
        this.hint = hint;
        this.isSolved = isSolved;
        this.roomID = roomID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getPuzzleID() {
        return puzzleID;
    }

    public void setPuzzleID(int puzzleID) {
        this.puzzleID = puzzleID;
    }

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

    public int getNumAttempts() {
        return numAttempts;
    }

    public void setNumAttempts(int numAttempts) {
        this.numAttempts = numAttempts;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public void setSolved(boolean solved) {
        isSolved = solved;
    }
}
