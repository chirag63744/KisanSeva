package com.example.kissanseva;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class AreaSelectionView extends View {
    private List<PointF> points;
    private int selectedPointIndex = -1;
    private Paint pointPaint;
    private Paint linePaint;

    public AreaSelectionView(Context context) {
        super(context);
        init();
    }

    public AreaSelectionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        points = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            points.add(new PointF(100,100));
        }

        pointPaint = new Paint();
        pointPaint.setColor(Color.BLUE);
        pointPaint.setStyle(Paint.Style.FILL);

        linePaint = new Paint();
        linePaint.setColor(Color.RED);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(3);
    }

    public void setPoints(List<PointF> newPoints) {
        for (int i = 0; i < 2 && i < newPoints.size(); i++) {
            points.set(i, newPoints.get(i));
        }
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                selectedPointIndex = getSelectedPointIndex(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                if (selectedPointIndex != -1) {
                    points.get(selectedPointIndex).set(x, y);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                selectedPointIndex = -1;
                break;
        }
        return true;
    }

    private int getSelectedPointIndex(float x, float y) {
        for (int i = 0; i < points.size(); i++) {
            PointF point = points.get(i);
            if (Math.abs(point.x - x) < 30 && Math.abs(point.y - y) < 30) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw lines connecting the points
        for (int i = 0; i < points.size(); i++) {
            PointF pointA = points.get(i);
            PointF pointB = points.get((i + 1) % points.size());
            canvas.drawLine(pointA.x, pointA.y, pointB.x, pointB.y, linePaint);
        }

        // Draw draggable points
        for (PointF point : points) {
            canvas.drawCircle(point.x, point.y, 10, pointPaint);
        }
    }

    public List<PointF> getPoints() {
        return points;
    }
}

