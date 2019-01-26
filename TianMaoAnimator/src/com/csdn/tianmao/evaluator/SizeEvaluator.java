package com.csdn.tianmao.evaluator;

import android.animation.TypeEvaluator;

import com.csdn.tianmao.bean.Size;

// 实现TypeEvaluator接口
public class SizeEvaluator implements TypeEvaluator {

    // 复写evaluate（）
    // 在evaluate（）里写入对象动画过渡的逻辑
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {

        // 将动画初始值startValue 和 动画结束值endValue 强制类型转换成Point对象
        Size startPoint = (Size) startValue;
        Size endPoint = (Size) endValue;

        // 根据fraction来计算当前动画的x和y的值
        float width = startPoint.getWidth() + fraction * (endPoint.getWidth() - startPoint.getWidth());
        float height = startPoint.getHeight() + fraction * (endPoint.getHeight() - startPoint.getHeight());
        
        // 将计算后的坐标封装到一个新的Point对象中并返回
        Size point = new Size(width, height);
        return point;
    }

}