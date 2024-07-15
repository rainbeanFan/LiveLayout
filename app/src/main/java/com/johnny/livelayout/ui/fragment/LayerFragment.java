package com.johnny.livelayout.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.johnny.livelayout.R;
import com.johnny.livelayout.adapter.AudienceAdapter;
import com.johnny.livelayout.adapter.MessageAdapter;
import com.johnny.livelayout.bean.GiftBean;
import com.johnny.livelayout.databinding.FragmentLayerBinding;
import com.johnny.livelayout.tools.DisplayUtil;
import com.johnny.livelayout.tools.SoftKeyBoardListener;
import com.johnny.livelayout.view.GiftRootLayout;
import com.johnny.livelayout.view.HorizontalListView;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 该Fragment是用于dialogFragment中的pager，为了实现滑动隐藏交互Fragment的
 * 交互的操作都在这个界面实现的，如果大家要改交互主要修改这个界面就可以了
 * <p>
 * Success is the sum of small efforts, repeated day in and day out.
 * 成功就是日复一日那一点点小小努力的积累。
 * AndroidGroup：158423375
 * Author：Johnny
 * AuthorQQ：956595454
 * AuthorWX：Qiang_it
 * AuthorPhone：nothing
 * Created by 2016/9/22.
 */
public class LayerFragment extends Fragment {

    /**
     * 标示判断
     */
    private boolean isOpen;

    /**
     * 界面相关
     */
    private FragmentLayerBinding mFragmentLayerBinding;

    /**
     * 动画相关
     */
    private AnimatorSet animatorSetHide = new AnimatorSet();
    private AnimatorSet animatorSetShow = new AnimatorSet();

    private List<String> messageData = new LinkedList<>();
    private MessageAdapter messageAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentLayerBinding = FragmentLayerBinding.inflate(inflater, container, false);
        return mFragmentLayerBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFragmentLayerBinding.viewLayer.llinputparent.getVisibility() == View.VISIBLE) {
                    mFragmentLayerBinding.viewLayer.ivPublicchat.setVisibility(View.VISIBLE);
                    mFragmentLayerBinding.viewLayer.llinputparent.setVisibility(View.GONE);
                    hideKeyboard();
                }
            }
        });
        softKeyboardListnenr();
        for (int x = 0; x < 20; x++) {
            messageData.add("Johnny: 默认聊天内容" + x);
        }
        messageAdapter = new MessageAdapter(getActivity(), messageData);
        mFragmentLayerBinding.viewLayer.lvmessage.setAdapter(messageAdapter);
        mFragmentLayerBinding.viewLayer.lvmessage.setSelection(messageData.size());
        mFragmentLayerBinding.viewLayer.hlvaudience.setAdapter(new AudienceAdapter(getActivity()));

        initViews();

    }

    private void initViews() {
        mFragmentLayerBinding.viewLayer.ivPrivatechat.setOnClickListener(v -> {
            GiftBean bean = new GiftBean();
            bean.setGroup(1);
            bean.setSortNum(11);
            bean.setGiftImage(R.mipmap.ic_launcher);
            bean.setGiftName("送出了一个礼物");
            bean.setUserName("A");
            bean.setUserAvatar(R.mipmap.ic_launcher);
            mFragmentLayerBinding.viewLayer.giftRoot.loadGift(bean);
        });

        mFragmentLayerBinding.viewLayer.ivGift.setOnClickListener(v -> {
            GiftBean bean = new GiftBean();
            bean.setGroup(1);
            bean.setSortNum(22);
            bean.setGiftImage(R.mipmap.ic_launcher);
            bean.setGiftName("送出了一个礼物");
            bean.setUserName("B");
            bean.setUserAvatar(R.mipmap.ic_launcher);
            mFragmentLayerBinding.viewLayer.giftRoot.loadGift(bean);
        });

        mFragmentLayerBinding.viewLayer.ivShare.setOnClickListener(v -> {
            GiftBean bean = new GiftBean();
            bean.setGroup(1);
            bean.setSortNum(33);
            bean.setGiftImage(R.mipmap.ic_launcher);
            bean.setGiftName("送出了一个礼物");
            bean.setUserName("C");
            bean.setUserAvatar(R.mipmap.ic_launcher);
            mFragmentLayerBinding.viewLayer.giftRoot.loadGift(bean);
        });

        mFragmentLayerBinding.viewLayer.ivPublicchat.setOnClickListener(v -> {
            showChat();
        });

        mFragmentLayerBinding.viewLayer.ivClose.setOnClickListener(v -> {
            getActivity().finish();
        });

        mFragmentLayerBinding.viewLayer.sendInput.setOnClickListener(v -> {
            sendText();
        });

    }

    /**
     * 显示聊天布局
     */
    private void showChat() {
        mFragmentLayerBinding.viewLayer.ivPublicchat.setVisibility(View.GONE);
        mFragmentLayerBinding.viewLayer.llinputparent.setVisibility(View.VISIBLE);
        mFragmentLayerBinding.viewLayer.llinputparent.requestFocus();
        showKeyboard();
    }

    /**
     * 发送消息
     */
    private void sendText() {
        if (!mFragmentLayerBinding.viewLayer.etInput.getText().toString().trim().isEmpty()) {
            messageData.add("Johnny: " + mFragmentLayerBinding.viewLayer.etInput.getText().toString().trim());
            mFragmentLayerBinding.viewLayer.etInput.setText("");
            messageAdapter.NotifyAdapter(messageData);
            mFragmentLayerBinding.viewLayer.lvmessage.setSelection(messageData.size());
            hideKeyboard();
        } else
            hideKeyboard();
    }

    /**
     * 显示软键盘并因此头布局
     */
    private void showKeyboard() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mFragmentLayerBinding.viewLayer.etInput, InputMethodManager.SHOW_FORCED);
            }
        }, 100);
    }

    /**
     * 隐藏软键盘并显示头布局
     */
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mFragmentLayerBinding.viewLayer.etInput.getWindowToken(), 0);
    }

    /**
     * 软键盘显示与隐藏的监听
     */
    private void softKeyboardListnenr() {
        SoftKeyBoardListener.setListener(getActivity(), new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {/*软键盘显示：执行隐藏title动画，并修改listview高度和装载礼物容器的高度*/
                animateToHide();
                dynamicChangeListviewH(100);
            }

            @Override
            public void keyBoardHide(int height) {/*软键盘隐藏：隐藏聊天输入框并显示聊天按钮，执行显示title动画，并修改listview高度和装载礼物容器的高度*/
                mFragmentLayerBinding.viewLayer.ivPublicchat.setVisibility(View.VISIBLE);
                mFragmentLayerBinding.viewLayer.llinputparent.setVisibility(View.GONE);
                animateToShow();
                dynamicChangeListviewH(150);
            }
        });
    }

    /**
     * 动态的修改listview的高度
     *
     * @param heightPX
     */
    private void dynamicChangeListviewH(int heightPX) {
        ViewGroup.LayoutParams layoutParams = mFragmentLayerBinding.viewLayer.lvmessage.getLayoutParams();
        layoutParams.height = DisplayUtil.dip2px(getActivity(), heightPX);
        mFragmentLayerBinding.viewLayer.lvmessage.setLayoutParams(layoutParams);
    }

    /**
     * 头部布局执行显示的动画
     */
    private void animateToShow() {
        ObjectAnimator leftAnim = ObjectAnimator.ofFloat(mFragmentLayerBinding.viewLayer.rlsentimenttime, "translationX",
                -mFragmentLayerBinding.viewLayer.rlsentimenttime.getWidth(), 0);
        ObjectAnimator topAnim = ObjectAnimator.ofFloat(mFragmentLayerBinding.viewLayer.llpicimage, "translationY",
                -mFragmentLayerBinding.viewLayer.llpicimage.getHeight(), 0);
        animatorSetShow.playTogether(leftAnim, topAnim);
        animatorSetShow.setDuration(300);
        animatorSetShow.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isOpen = false;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isOpen = true;
            }
        });
        if (!isOpen) {
            animatorSetShow.start();
        }
    }

    /**
     * 头部布局执行退出的动画
     */
    private void animateToHide() {
        ObjectAnimator leftAnim = ObjectAnimator.ofFloat(mFragmentLayerBinding.viewLayer.rlsentimenttime, "translationX", 0, -mFragmentLayerBinding.viewLayer.rlsentimenttime.getWidth());
        ObjectAnimator topAnim = ObjectAnimator.ofFloat(mFragmentLayerBinding.viewLayer.llpicimage, "translationY", 0,
                -mFragmentLayerBinding.viewLayer.llpicimage.getHeight());
        animatorSetHide.playTogether(leftAnim, topAnim);
        animatorSetHide.setDuration(300);
        animatorSetHide.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isOpen = false;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isOpen = true;
            }
        });
        if (!isOpen) {
            animatorSetHide.start();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}