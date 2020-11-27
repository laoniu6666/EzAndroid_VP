 package com.laoniu.ezandroid.base;

import androidx.annotation.CallSuper;
import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Lifecycle;

import android.os.Bundle;

import com.laoniu.ezandroid.utils.view.dialog.WKDialog;

import org.jetbrains.annotations.NotNull;

 public abstract class BaseActivity<V extends BaseView,P extends BasePresenter, B extends ViewDataBinding>
         extends AppCompatActivity implements BaseView{

     protected B binding;
     protected P presenter;
     protected V mBaseView;

     @Override
     protected void onCreate(@Nullable Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(getLayoutId());
         binding = DataBindingUtil.setContentView(this, getLayoutId());
         initPresenter();
         initLifecycleObserver(getLifecycle());
         initData();
     }

     protected void initPresenter(){
         try {
             this.mBaseView = this.getBaseView();
             this.presenter = (P)this.getPresenter().newInstance();
             if (this.presenter != null) {
                 this.presenter.init(mBaseView);
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
     };


     @MainThread
     protected abstract int getLayoutId();

     @MainThread
     protected abstract Class<P> getPresenter();
     @MainThread
     protected abstract V getBaseView();

     @CallSuper
     @MainThread
     protected void initLifecycleObserver(@NotNull Lifecycle lifecycle) {
//         lifecycle.addObserver(presenter);
     }

     @MainThread
     protected abstract void initData();

     @MainThread
     public void showLoading() {
         WKDialog.showProgressDialog();
     }

     @MainThread
     public void dismissLoading() {
         WKDialog.dissmissProgressDialog();
     }
 }