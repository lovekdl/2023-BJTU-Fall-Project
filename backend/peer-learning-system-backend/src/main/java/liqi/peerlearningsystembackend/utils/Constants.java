package liqi.peerlearningsystembackend.utils;

public final class Constants {

    private Constants() {
        throw new AssertionError("Cannot instantiate the constants class");
    }

    /**
     * Constants for authority
     */
    public static final int AUTHORITY_ADMIN = 1;
    public static final int AUTHORITY_TEACHER = 2;
    public static final int AUTHORITY_STUDENT = 3;

    /**
     * Constants for course
     */
    public static final int COURSE_COUNTER = 4;

    /**
     * Constants for assignment
     */
    public static final int ASSIGNMENT_COUNTER = 5;

    /**
     * Constants for homework
     */
    public static final int HOMEWORK_COUNTER = 6;

    /**
     * Constants for peer
     */
    public static final int PEER_COUNTER = 7;

    /**
     * Constants for email code expire time
     */
    public static final int EMAIL_CODE_EXPIRE_TIME = 60 * 5;

}
