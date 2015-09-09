package com.gms.moongoon.choice_moongoon.Libraries.MatchingSecretTextView_Package;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * Created by user on 2015-09-04.
 */
public class SecretMatchingTextView extends TextView implements AutofitHelper.OnTextSizeChangeListener {

    private AutofitHelper mHelper;

    private String textString;
    private SpannableString spannableString;

    private double[] alphas;
    SecretMatchingTextViewHelper[] secretMatchingTextViewHelpers;
    boolean isVisible;
    boolean isTextResetting;
    int duration = 3000;

    ValueAnimator valueAnimator;
    ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            Float per = (Float) valueAnimator.getAnimatedValue();
            resetSpanableString(isVisible ? per : 2.0f - per);
        }
    };

    public SecretMatchingTextView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public SecretMatchingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public SecretMatchingTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    void init(Context context, AttributeSet attrs, int defStyle) {

        mHelper = AutofitHelper.create(this, attrs, defStyle).addOnTextSizeChangeListener(this);

        isVisible = false;
        valueAnimator = ValueAnimator.ofFloat(0.0f, 2.0f);
        valueAnimator.addUpdateListener(animatorUpdateListener);
        valueAnimator.setDuration(duration);
    }

    public void toggle() {
        if (isVisible) {
            hide();
        } else {
            show();
        }
    }

    void show() {
        isVisible = true;
        valueAnimator.start();
    }

    void hide() {
        isVisible = false;
        valueAnimator.start();
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
        resetSpanableString(isVisible == true ? 2.0f : 0.0f);
    }

    public boolean getIsVisible() {
        return isVisible;
    }

    void resetSpanableString(double per) {
        isTextResetting = true;

        int color = getCurrentTextColor();

        for (int i = 0; i < textString.length(); i++) {
            SecretMatchingTextViewHelper secretMatchingTextViewHelper = secretMatchingTextViewHelpers[i];
            secretMatchingTextViewHelper.setColor(Color.argb(clamp(alphas[i] + per), Color.red(color), Color.green(color), Color.blue(color)));
        }
        setText(spannableString);
        isTextResetting = false;
    }

    void resetIfNeeded() {
        if (!isTextResetting) {
            textString = getText().toString();
            spannableString = new SpannableString(textString);
            secretMatchingTextViewHelpers = new SecretMatchingTextViewHelper[textString.length()];

            for (int i = 0; i < textString.length(); i++) {
                SecretMatchingTextViewHelper demoHelper = new SecretMatchingTextViewHelper();
                spannableString.setSpan(demoHelper, i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                secretMatchingTextViewHelpers[i] = demoHelper;
            }
            resetAlphas(textString.length());
            resetSpanableString(isVisible ? 2.0f : 0);
        }
    }

    void resetAlphas(int length) {
        alphas = new double[length];
        for (int i = 0; i < alphas.length; i++) {
            alphas[i] = Math.random() - 1;
        }
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        resetIfNeeded();
    }

    public void setText(String text) {
        super.setText(text);
        resetIfNeeded();
    }

    @Override
    public void setTextSize(int unit, float size) {
        super.setTextSize(unit, size);
        if (mHelper != null) {
            mHelper.setTextSize(unit, size);
        }
    }

    @Override
    public void setLines(int lines) {
        super.setLines(lines);
        if (mHelper != null) {
            mHelper.setMaxLines(lines);
        }
    }

    @Override
    public void setMaxLines(int maxlines) {
        super.setMaxLines(maxlines);
        if (mHelper != null) {
            mHelper.setMaxLines(maxlines);
        }
    }

    AutofitHelper getAutofitHelper() {
        return mHelper;
    }

    boolean isSizeToFit() {
        return mHelper.isEnabled();
    }

    void setSizeToFit() {
        setSizeToFit(true);
    }

    void setSizeToFit(boolean sizeToFit) {
        mHelper.setEnabled(sizeToFit);
    }

    public float getMaxTextSize() {
        return mHelper.getMaxTextSize();
    }

    public void setMaxTextSize(float size) {
        mHelper.setMaxTextSize(size);
    }

    public void setMaxTextSize(int unit, float size) {
        mHelper.setMaxTextSize(unit, size);
    }

    public float getMinTextSize() {
        return mHelper.getMinTextSize();
    }

    public void setMinTextSize(int minSize) {
        mHelper.setMinTextSize(TypedValue.COMPLEX_UNIT_SP, minSize);
    }

    public void setMinTextSize(int unit, float minSize) {
        mHelper.setMinTextSize(unit, minSize);
    }

    public float getPrecision() {
        return mHelper.getPrecision();
    }

    public void setPrecision(float precision) {
        mHelper.setPrecision(precision);
    }

    @Override
    public void onTextSizeChange(float textSize, float oldTextSize) {
        // do nothing

    }

    int clamp(double f) {
        return (int) (255 * Math.min(Math.max(f, 0), 1));
    }


    class SecretMatchingTextViewHelper extends CharacterStyle implements UpdateAppearance {

        private int color;

        @Override
        public void updateDrawState(TextPaint tp) {
            tp.setColor(color);
        }

        void setColor(int color) {
            this.color = color;
        }

        public int getColor() {
            return color;
        }
    }
}
