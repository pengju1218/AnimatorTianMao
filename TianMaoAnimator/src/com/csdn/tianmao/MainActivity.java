package com.csdn.tianmao;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.csdn.tianmao.anim.ViewSizeChangeAnimation;
import com.csdn.tianmao.util.DensityUtil;
import com.csdn.tianmao.view.SearchEditText;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends Activity implements View.OnClickListener {

    private ImageView ids_left_back;
    private ImageView ids_sort;
    private TextView id_title;
    private ImageView id_img_rihgt;
    private Banner banner;
    private NestedScrollView ids_scoller;
    private SearchEditText query;
    private boolean isShow = true;
    private RelativeLayout ids_title_line;
    private String imags = "https://gw.alicdn.com/tps/TB1sOpSKFXXXXXwXVXXXXXXXXXX-1680-400.jpg,http://img.alicdn.com/tps/TB1dGh3KFXXXXbeXpXXXXXXXXXX-1680-400.jpg," +
            "http://gtms04.alicdn.com/tps/i4/TB1dTXUKFXXXXaMXFXXWXkEMFXX-1680-400.png,http://img.alicdn.com/tps/TB1dGh3KFXXXXbeXpXXXXXXXXXX-1680-400.jpg" +
            "http://gtms04.alicdn.com/tps/i4/TB1dTXUKFXXXXaMXFXXWXkEMFXX-1680-400.png,http://gtms01.alicdn.com/tps/i1/TB1pY0VKFXXXXb6XFXXgoUDMFXX-1680-400.jpg";
    private float startY, startY2;
    private ImageView ids_first_img;
    private Button show1;
    private Button hide;

    //View是否显示的标志
    boolean show = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_world);
        Fresco.initialize(this);
        initView();

    }

    private void initView() {
        ids_left_back = (ImageView) findViewById(R.id.ids_left_back);
        ids_sort = (ImageView) findViewById(R.id.ids_sort);
        id_title = (TextView) findViewById(R.id.id_title);
        id_img_rihgt = (ImageView) findViewById(R.id.id_img_rihgt);
        banner = (Banner) findViewById(R.id.banner);
        ids_scoller = (NestedScrollView) findViewById(R.id.ids_scoller);
        query = (SearchEditText) findViewById(R.id.query);

        startY = query.getY();
        startY2 = DensityUtil.dip2px(this, 40);
        ids_scoller = (NestedScrollView) findViewById(R.id.ids_scoller);
        ids_title_line = (RelativeLayout) findViewById(R.id.ids_first_include);
        ArrayList list = new ArrayList(Arrays.asList(imags.split(",")));
        banner.setImages(list).setImageLoader(new GlideImageLoader()).start();
        ids_scoller.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                //上滑 并且 正在显示底部栏
                if (scrollY - oldScrollY > 0 && isShow) {
                    PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("translationY", -50f);
                    //ObjectAnimator.ofPropertyValuesHolder(query, pvhY).setDuration(1000).start();
                    isShow = false;
                    //将Y属性变为底部栏高度  (相当于隐藏了)
                    initIcon();
                    query.animate().translationY(-startY2).translationX(120);

                    Drawable drawable = DensityUtil.getDrawable(MainActivity.this, R.drawable.search_edit_bg2);
                    query.setBackground(drawable);

                    Animation animation = new ViewSizeChangeAnimation(query, query.getHeight(), query.getWidth() * 7 / 10);
                    animation.setDuration(500);
                    query.startAnimation(animation);

                } else if (scrollY - oldScrollY < 0 && !isShow) {
                    initIcon2();
                    isShow = true;
                    query.animate().translationY(startY).translationX(0);
                    Drawable drawable = DensityUtil.getDrawable(MainActivity.this, R.drawable.search_edit_bg);
                    query.setBackground(drawable);


                    Animation animation = new ViewSizeChangeAnimation(query, query.getHeight(), query.getWidth() * 10 / 7);
                    animation.setDuration(500);
                    query.startAnimation(animation);
                }


            }

        });
        ids_first_img = (ImageView) findViewById(R.id.ids_first_img);
        //Uri uri = Uri.parse((String) "http://img.alicdn.com/tps/TB1dGh3KFXXXXbeXpXXXXXXXXXX-1680-400.jpg");

        Glide.with(this).load("http://img.alicdn.com/tps/TB1dGh3KFXXXXbeXpXXXXXXXXXX-1680-400.jpg").into(ids_first_img);



        show1 = (Button) findViewById(R.id.show1);
        show1.setOnClickListener(this);
        hide = (Button) findViewById(R.id.hide);
        hide.setOnClickListener(this);

    }


    public void show(){

        //属性动画对象
        ValueAnimator va;
        if (show) {
            //显示view，高度从0变到height值
            va = ValueAnimator.ofInt(0, 300);
            show=false;
        } else {
            //隐藏view，高度从height变为0
            va = ValueAnimator.ofInt(300, 0);
            show=true;
        }
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //获取当前的height值
                int h = (Integer) valueAnimator.getAnimatedValue();
                //动态更新view的高度
                ids_first_img.getLayoutParams().height = h;
                ids_first_img.requestLayout();
            }
        });
        va.setDuration(1000);
        //开始动画
        va.start();

    }
    public void initIcon() {
        ids_left_back.setImageResource(R.drawable.back_black);
        ids_sort.setImageResource(R.drawable.sort_black);
        id_img_rihgt.setImageResource(R.drawable.shop_black);
        ids_title_line.setBackgroundColor(Color.parseColor("#ffffff"));
    }

    public void initIcon2() {
        ids_left_back.setImageResource(R.drawable.back_white);
        ids_sort.setImageResource(R.drawable.sort_white);
        id_img_rihgt.setImageResource(R.drawable.shop_white);
        ids_title_line.setBackgroundResource(R.drawable.head_bg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show1:
                show();
                break;
            case R.id.hide:
                show();
                break;
        }
    }

    class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */


            //Glide 加载图片简单用法
            //Glide.with(context).load(path).into(imageView);

            //Picasso 加载图片简单用法
            //Picasso.with(context).load(path).into(imageView);

            //用fresco加载图片简单用法，记得要写下面的createImageView方法
            Uri uri = Uri.parse((String) path);
            imageView.setImageURI(uri);
        }

        //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
        @Override
        public ImageView createImageView(Context context) {
            //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
            SimpleDraweeView simpleDraweeView = new SimpleDraweeView(context);
            return simpleDraweeView;
        }
    }
}
