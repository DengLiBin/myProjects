package com.denglibin.googleplay.fragment;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.denglibin.googleplay.Manager.ThreadManager;
import com.denglibin.googleplay.R;
import com.denglibin.googleplay.domain.AppInfo;
import com.denglibin.googleplay.utils.ViewUtils;

import java.util.List;

/**
 * Created by DengLibin on 2016/3/15 0015.
 */
public abstract class BaseFragment extends Fragment {
    protected FrameLayout frameLayout;
    //这几个view不能写静态，因为需要根据情况判断是否需要显示和隐藏
    public  View loadingView;
    public  View errorView;
    public  View emptyView;
    public  View successView;
    public static final int STATE_UNKOWN = 0;
    public static final int STATE_LOADING = 1;
    public static final int STATE_ERROR = 2;
    public static final int STATE_EMPTY = 3;
    public static final int STATE_SUCCESS = 4;
    public   int state_current = 0;

    /**
     * 枚举类（也可以不用枚举，用常量也行）
     */
    public enum LoadResult {
        error(2), empty(3), success(4);
        int value;

        LoadResult(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (frameLayout == null) {
            frameLayout = new FrameLayout(getActivity());

        } else {
            ViewUtils.removeFromParent(frameLayout);//从父控件中将原来的framLayout移除。
        }
        init();
        showPager();//根据不同的状态显示不同的界面
        show();//根服务器的数据切换状态
        return frameLayout;
    }


    //根服务器的数据切换状态
    protected void show() {
        if (state_current == STATE_ERROR || state_current == STATE_EMPTY) {
            state_current = STATE_UNKOWN;
        }
        //请求服务器获取服务器上的数据，进行判断
        //请求服务器返回一个结果
        //用线程池技术
        ThreadManager.getInstance().createLongPool().execute(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);//睡两秒

                final LoadResult result = load();//请求服务器放到子线程中进行

                if (getActivity() != null) {//如果主线程退出了，activity已经销毁，这时子线程还在运行，就会报错
                    getActivity().runOnUiThread(new Runnable() {//更新UI在主线程进行
                        @Override
                        public void run() {
                            if (result != null) {
                                state_current = result.getValue();
                                if (state_current == STATE_SUCCESS) {
                                    if (successView == null) {
                                        successView = createSuccessView();//因为创建successView需要服务器的数据，所以要在访问服务器后才能创建
                                    } else {
                                        ViewUtils.removeFromParent(successView);//从父控件中将原来的successView移除
                                    }
                                    if (successView != null) {//页面创建后要添加到frameLayout中
                                        frameLayout.addView(successView, new FrameLayout.LayoutParams(
                                                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT
                                        ));
                                    }
                                }
                                System.out.println("子线程访问服务器返回的状态：" + state_current);
                                //state_current = 2 + new Random().nextInt(3);//随机一个值，0-3，包括前面不包括后面，加2就是0-4；
                                showPager();//状态改变了，重新判断当前应该显示哪个界面
                            }
                        }
                    });
                }
            }
        });
        showPager();
    }

    protected abstract LoadResult load();


    //根据不同的状态显示不同的界面（只能同时显示一种界面）
    public void showPager() {

        if (loadingView != null) {//根据当前状态判断loadingView是否显示
            loadingView.setVisibility((state_current == STATE_UNKOWN || state_current == STATE_LOADING) ? View.VISIBLE : View.INVISIBLE);
        }
        if (errorView != null) {
            errorView.setVisibility(state_current == STATE_ERROR ? View.VISIBLE : View.INVISIBLE);
        }
        if (emptyView != null) {
            emptyView.setVisibility(state_current == STATE_EMPTY ? View.VISIBLE : View.INVISIBLE);
        }
        if (successView != null) {
            successView.setVisibility(state_current == STATE_SUCCESS ? View.VISIBLE : View.INVISIBLE);

        }
        System.out.println("执行完showPager(),stateCurrent:"+state_current);
    }

    //在FrameLayout中添加四种不同的界面
    public void init() {
        loadingView=createLoadingView();//创建了加载中的界面
        if(loadingView!=null){
            frameLayout.addView(loadingView, new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT
            ));
        }
        errorView=createErrorView();//创建了错误界面
        if(errorView!=null){
            frameLayout.addView(errorView, new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT
            ));
        }
        emptyView=createEmptyView();//创建空页面
        if(emptyView!=null){
            frameLayout.addView(emptyView, new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT
            ));
        }
       /* 不在这里创建，访问服务器后拿到返回数据才创建
        successView=createSuccessView();
        if(successView!=null){
            frameLayout.addView(successView, new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT
            ));
        }
        */
    }

    // 返回加载中的界面
    public View createLoadingView() {
        View view = View.inflate(getActivity(), R.layout.loading_page, null);
        return view;
    }

    // 返回错误页面
    public View createErrorView() {
        View view = View.inflate(getActivity(), R.layout.error_page, null);
        Button btn_error = (Button) view.findViewById(R.id.btn_error);
        btn_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });
        return view;
    }

    //返回空页面
    public View createEmptyView() {
        View view = View.inflate(getActivity(), R.layout.empty_page, null);
        return view;
    }
    protected abstract View createSuccessView();


    //检查加载的数据,相同的方法放到基类中
    public LoadResult checkData(List load){
        if(load == null){
            return  LoadResult.error;
        }else{
            if(load.size()==0){
                return  LoadResult.empty;
            }else{
                //System.out.println(load);
                return LoadResult.success;
            }
        }
    }
}

