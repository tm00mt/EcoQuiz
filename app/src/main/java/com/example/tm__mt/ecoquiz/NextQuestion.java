package com.example.tm__mt.ecoquiz;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class NextQuestion implements Parcelable {
    private String DEBUG_TAG = "NextQuestion";
    private static final int LINKS_NUM = 6;

    private String[] logoLinks = new String[LINKS_NUM];
    private String[] bitmapPaths = new String[LINKS_NUM];
    private String question = "";
    private int questionNumber = 0;
    private int logoSize = 0;
    private int category = 0;
    private int lang = 0;
    private int correctAnswer = 0;

    public NextQuestion(int questionNumber, int category, int lang, int logoSize) {
        this.setQuestionNumber(questionNumber);
        this.setCategory(category);
        this.setLang(lang);
        this.setLogoSize(logoSize);
    }

    private NextQuestion(Parcel in) {
        this.questionNumber = in.readInt();
        this.category = in.readInt();
        this.lang = in.readInt();
        this.logoSize = in.readInt();
        this.correctAnswer = in.readInt();
        this.question = in.readString();

        this.logoLinks[0] = in.readString();
        this.logoLinks[1] = in.readString();
        this.logoLinks[2] = in.readString();
        this.logoLinks[3] = in.readString();
        this.logoLinks[4] = in.readString();
        this.logoLinks[5] = in.readString();

        this.bitmapPaths[0] = in.readString();
        this.bitmapPaths[1] = in.readString();
        this.bitmapPaths[2] = in.readString();
        this.bitmapPaths[3] = in.readString();
        this.bitmapPaths[4] = in.readString();
        this.bitmapPaths[5] = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.questionNumber);
        dest.writeInt(this.category);
        dest.writeInt(this.lang);
        dest.writeInt(this.logoSize );
        dest.writeInt(this.correctAnswer);
        dest.writeString(this.question);

        dest.writeString(this.logoLinks[0]);
        dest.writeString(this.logoLinks[1]);
        dest.writeString(this.logoLinks[2]);
        dest.writeString(this.logoLinks[3]);
        dest.writeString(this.logoLinks[4]);
        dest.writeString(this.logoLinks[5]);

        dest.writeString(this.bitmapPaths[0]);
        dest.writeString(this.bitmapPaths[1]);
        dest.writeString(this.bitmapPaths[2]);
        dest.writeString(this.bitmapPaths[3]);
        dest.writeString(this.bitmapPaths[4]);
        dest.writeString(this.bitmapPaths[5]);
    }

    public static final Parcelable.Creator<NextQuestion> CREATOR
            = new Parcelable.Creator<NextQuestion>() {
        public NextQuestion createFromParcel(Parcel in) {
            return new NextQuestion(in);
        }

        public NextQuestion[] newArray(int size) {
            return new NextQuestion[size];
        }
    };

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getLang() {
        return lang;
    }

    public void setLang(int lang) {
        this.lang = lang;
    }

    public int getLogoSize() {
        return logoSize;
    }

    public void setLogoSize(int logoSize) {
        this.logoSize = logoSize;
    }

    public String getLogoLink(int i) {
        if (i >= 0 && i < LINKS_NUM)
            return this.logoLinks[i];
        else
            return "";
    }

    public void setBitmapPath(String path, int i) {
        if (i >= 0 && i < LINKS_NUM)
            this.bitmapPaths[i] = path;
    }

    public String getBitmapPath(int i) {
        String path = "";
        if (i >= 0 && i < LINKS_NUM)
            path = this.bitmapPaths[i];
        return path;
    }

    public boolean prepare(Context context) {
        Log.d(DEBUG_TAG, "Preparing data from DB...");

        if (this.category > 0 && this.questionNumber > 0 && this.getLogoSize() > 0 && this.lang > 0) {
            EcoQuizDBHelper DBHelper = new EcoQuizDBHelper(context);

            this.question = DBHelper.getQuestion(this.getCategory(), this.getQuestionNumber(), this.getLang());
            if (this.getQuestion().isEmpty()) {
                Log.d(DEBUG_TAG, "Query result is empty. Last question was reached or there are no questions that meet the conditions!");
                Log.d(DEBUG_TAG, this.getCategory() + " " + this.getQuestionNumber() + " " + this.getLang());
                return false;
            }

            this.logoLinks = DBHelper.getLogoLinks(this.getCategory(), this.getQuestionNumber(), this.getLang());
            this.correctAnswer = DBHelper.getCorrectAnswer(this.getCategory(), this.getQuestionNumber());

            return true;
        } else {
            return false;
        }
    }

    public String getQuestion() {
        Log.d(DEBUG_TAG, "Getting Question...");
        Log.d(DEBUG_TAG, "The question is " + this.question);

        return this.question;
    }

    public String toString() {
        String r;

        r = " QN: " + this.getQuestionNumber()
          + " CT: " + this.getCategory()
          + " LN: " + this.getLang()
          + " LS: " + this.getLogoSize()
          + " QT: " + this.getQuestion();

        return r;
    }
}
