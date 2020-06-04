package ru.rsue.borisov.work;

import java.util.UUID;

public class Work {
    private UUID mId;
    private String mFio;

    private String mRate;
    private String mNumber;
    private String mHour;


    Work() {
        this(UUID.randomUUID());
    }

    public Work(UUID id) {
        mId = id;
    }

    public UUID getId() {
        return mId;
    }

    String getRate() {
        return mRate;
    }

    public void setRate(String rate) {
        mRate = rate;
    }

    String getNumber() {
        return mNumber;
    }

    public void setNumber(String number) {
        mNumber = number;
    }

    String getHour() {
        return mHour;
    }

    public void setHour(String hour) {
        mHour = hour;
    }

    String getFio() {
        return mFio;
    }

    public void setFio(String fio) {
        mFio = fio;
    }
}
