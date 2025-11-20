package com.example.todoapp.constant;

public enum TodoStatus {
    NORMAL(0),
    DANGER(1),
    WARNING(2);

    private final int code;

    TodoStatus(int code) {
        this.code = code;
    }

    public int getCode() {return code;}

    public static TodoStatus fromCode(int code) {
        for (TodoStatus s : values()) {
            if (s.code == code) return s;
        }
        throw new IllegalArgumentException("Unknown status: " + code);
    }
}
