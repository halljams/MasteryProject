package learn.ui;

public enum MainMenuOption {
    EXIT(0,"Exit",false),

    VIEW_RESERVATIONS_FOR_HOST(1,"Host by Email",false),

    MAKE_RESERVATION(2,"Make a reservation",false),

    EDIT_RESERVATION(3,"Change an existing reservation",false),

    CANCEL_RESERVATION(4,"Cancel a reservation",false);

    private int value;
    private String message;
    private boolean hidden;

    private MainMenuOption(int value, String message, boolean hidden) {
        this.value = value;
        this.message = message;
        this.hidden = hidden;
    }

    public static MainMenuOption fromValue(int value) {
        for (MainMenuOption option : MainMenuOption.values()) {
            if (option.getValue() == value) {
                return option;
            }
        }
        return EXIT;
    }

    public int getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public boolean isHidden() {
        return hidden;
    }
}
