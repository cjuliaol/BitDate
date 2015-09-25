package com.example.thewizard.cjuliaol.bitdate;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

/**
 * Created by cjuliaol on 12-Sep-15.
 */
public class CardStackContainer extends RelativeLayout implements View.OnTouchListener {

    private static final String TAG = "CardStackContainerLog";
    private CardAdapter mCardAdapter;
    private float mLastTouchX;
    private float mLastTouchY;
    private float mPositionX;
    private float mPositionY;
    private float mOriginX;
    private float mOriginY;
    private GestureDetector mGestureDetector;
    private CardView mFrontCard;
    private CardView mBackCard;
    private int mNextPosition;
    private SwipeCallbacks mSwipeCallbacks;

    public void setSwipeCallbacks(SwipeCallbacks swipeCallbacks) {
        mSwipeCallbacks = swipeCallbacks;
    }

    public void setCardAdapter(CardAdapter cardAdapter) {
        mCardAdapter = cardAdapter;
        DataSetObserver dataSetObserver = new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                addFrontCard();
                addBackCard();
            }
        };
        mCardAdapter.registerDataSetObserver(dataSetObserver);
        addFrontCard();
        addBackCard();
    }

    private void addFrontCard() {
        if (mCardAdapter.getCount() > 0 && mFrontCard == null) {
            CardView cardView = mCardAdapter.getView(0, null, this);
            cardView.setOnTouchListener(this);
            cardView.setCardElevation(8);
            cardView.setTranslationY(30);
            mFrontCard = cardView;
            addView(cardView);
            mNextPosition++;
        }
    }

    private void addBackCard() {
        if (mCardAdapter.getCount() > mNextPosition && mBackCard == null ) {
            CardView cardView = mCardAdapter.getView(mNextPosition, null, this);
            mBackCard = cardView;
            mBackCard.setCardElevation(8);
            addView(cardView);
            mNextPosition++;
        }

        bringChildToFront(mFrontCard);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (mGestureDetector.onTouchEvent(event)) {

            if (mOriginX - v.getX() < 0) {
                // To the right
               swipeCard(true);
            } else if (mOriginX - v.getX() > 0) {
                // To the left
                swipeCard(false);
            }


            removeView(v);
            return true;
        }


        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastTouchX = event.getX();
                mLastTouchY = event.getY();

                mPositionX = v.getX();
                mPositionY = v.getY();
                mOriginX = v.getX();
                mOriginY = v.getY();

                break;
            case MotionEvent.ACTION_UP:
                reset(v);
                break;
            case MotionEvent.ACTION_MOVE:
                float xMove = event.getX();
                float yMove = event.getY();
                float xChange = xMove - mLastTouchX;
                float yChange = yMove - mLastTouchY;

                mPositionX += xChange;
                mPositionY += yChange;

                v.setX(mPositionX);
                v.setY(mPositionY);

                break;

        }

        return true;
    }

    public void swipeRight() {
        int position;
       position = getSwipedPosition();
        swipeCard(true);
        if (mSwipeCallbacks != null) {
            mSwipeCallbacks.onSwipedRight(mCardAdapter.getItem(position));
        }
    }

    public void swipeLeft() {
        int position;
        position = getSwipedPosition();
        swipeCard(false);
        if (mSwipeCallbacks != null) {
            mSwipeCallbacks.onSwipedLeft(mCardAdapter.getItem(position));
        }
    }

    private int getSwipedPosition() {
        int position;
        if(mBackCard !=null) {
            position = mNextPosition-2;
        }
        else
        {
            position = mNextPosition-1;
        }
        return position;
    }



    public void swipeCard(boolean swipeRight) {
        if (swipeRight == true) {
            mFrontCard.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_right));
        } else {
            mFrontCard.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_left));
        }
        removeView(mFrontCard);
        mFrontCard  = null;
        if (mBackCard != null) {
            mBackCard.animate()
                    .translationY(0)
                    .setDuration(200);
            mBackCard.setOnTouchListener(this);
            mFrontCard = mBackCard;
            mBackCard = null;
            addBackCard();

        }

    }

    private void reset(View v) {
        mPositionX = mOriginX;
        mPositionY = mOriginY;
        v.animate()
                .setDuration(200)
                .setInterpolator(new AccelerateInterpolator())
                .x(mPositionX)
                .y(mPositionY);

    }

    public CardStackContainer(Context context) {
        this(context, null, 0);
    }

    public CardStackContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardStackContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mGestureDetector = new GestureDetector(context, new FlingListener());
    }

    private class FlingListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d(TAG, "Fling happened");
            return true;
        }
    }
    public interface SwipeCallbacks {
        public void onSwipedRight(User user);
        public void onSwipedLeft(User user);
    }

}
