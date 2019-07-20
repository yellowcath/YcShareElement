[原理分析](https://www.jianshu.com/p/fa1c8deeaa57)

## Gradle

add Maven
``` groovy
 allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
```
add implementation
``` groovy
dependencies {
    implementation 'com.github.yellowcath:YcShareElement:1.3.2'
}
```

## Usage
>demo里用了  
[GSYVideoPlayer](https://github.com/CarGuo/GSYVideoPlayer)展示视频  
[Fresco](https://github.com/facebook/fresco)、[Glide](https://github.com/bumptech/glide)展示图片

YcShareElement提供了两个demo，一个是上面的联系人demo，另一个实现了图片、视频混合的列表页与详情页之间的ShareElement动画，如下图

![image](https://github.com/yellowcath/YcShareElement/raw/master/readme/se.gif)

这里面的关键点如下：  
1、Glide图片的ShareElement动画  
ImageView在动画过程中要经历默认背景色->小缩略图->大图三个阶段，如何在这三个阶段里做到无缝切换  
参考:[ChangeOnlineImageTransition](https://github.com/yellowcath/YcShareElement/blob/master/ycshareelement/src/main/java/com/hw/ycshareelement/transition/ChangeOnlineImageTransition.java)  
2、Fresco图片的ShareElement动画  
Fresco提供了内置的DraweeTransition，但是如果设置了缩略图，图片就会变形，并且必须在构造函数里提供动画起始的ScaleType信息，简单的情况很好用，在复杂的情况下不太友好  
参考:[AdvancedDraweeTransition](https://github.com/yellowcath/YcShareElement/blob/master/app/src/main/java/us/pinguo/shareelementdemo/advanced/list/AdvancedDraweeTransition.java)  
3、从列表的Webp动图到详情页的视频ShareElement动画  
这个在实现了以上两点之后其实就很简单了,实际上就是视频的封面图做动画

### 普通页面使用步骤

#### 1、打开WindowContentTransition开关
``` java
YcShareElement.enableContentTransition(getApplication());  
``` 
由于这个开关默认是打开的，因此这一句是可选的，担心遇到奇葩手机关掉这个开关的可以调用
#### 2、生成Bundle，然后startActivity
``` java
    private void gotoDetailActivity(){
        Intent intent = new Intent(this, DetailActivity.class);
        Bundle bundle = YcShareElement.buildOptionsBundle(ContactActivity.this, new IShareElements() {
            @Override
            public ShareElementInfo[] getShareElements() {
                return new ShareElementInfo[]{new ShareElementInfo(mAvatarImg),
                        new ShareElementInfo(mNameTxt, new TextViewStateSaver())};
            }
        });
        ActivityCompat.startActivity(ContactActivity.this, intent, bundle);
    }
```
#### 3、新的页面里设置并启动Transition
``` java
public class DetailActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ...
        YcShareElement.setEnterTransition(this, new IShareElements() {
            @Override
            public ShareElementInfo[] getShareElements() {
                return new ShareElementInfo[]{new ShareElementInfo(avatarImg),
                        new ShareElementInfo(nameTxt, new TextViewStateSaver())};
            }
        });
        YcShareElement.startTransition(this);
    }
}
```
>YcShareElement.setEnterTransition()默认会暂停Activity的Transtion动画，直到调用YcShareElement.startTransition(),
在这种不需要等待ShareElement加载的简单页面，可以将第三个参数传false，就不会暂停ActivityB的Transition动画了,如下

``` java
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ...
        YcShareElement.setEnterTransition(this, new IShareElements() {
            @Override
            public ShareElementInfo[] getShareElements() {
                return new ShareElementInfo[]{new ShareElementInfo(avatarImg),
                        new ShareElementInfo(nameTxt, new TextViewStateSaver())};
            }
        },false);
    }
```

效果如下：  
![image](https://github.com/yellowcath/YcShareElement/raw/master/readme/contacts2.gif)

### 图片&视频页面使用步骤

#### 1、打开WindowContentTransition开关
``` java
    YcShareElement.enableContentTransition(getApplication());  
``` 
#### 2、生成Bundle，然后startActivity
``` java
    Bundle options = YcShareElement.buildOptionsBundle(getActivity(), this);
    startActivityForResult(intent, REQUEST_CONTENT, options);
```
#### 3、Activity B设置Transtion动画
``` java
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        YcShareElement.setEnterTransition(this, this);
        ...
    }
```
#### 4、Activity B的ViewPager加载好之后启动Transition
``` java
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ...加载数据...
        YcShareElement.postStartTransition(getActivity());
    }
```
这时候进入动画就执行完毕了，接下来要处理滑动若干页之后返回列表页的情况

#### 5、Activity B实现finishAfterTransition()函数
``` java
    @Override
    public void finishAfterTransition() {
        YcShareElement.finishAfterTransition(this, this);
        super.finishAfterTransition();
    }
```
#### 6、Activity A实现onActivityReenter()函数
``` java
    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        YcShareElement.onActivityReenter(this, resultCode, data, new IShareElementSelector() {
            @Override
            public void selectShareElements(List<ShareElementInfo> list) {
                //将列表页滑动到变更后的ShareElement的位置
                mFragment.selectShareElement(list.get(0));
            }
        });
    }
``` 

### 如何扩展支持自定义View的Transition动画
这里以Fresco为例介绍如何进行扩展

#### 1、确定所需参数
首先确定SimpleDraweeView做Transtion动画需要的参数，即ActualImageScaleType
#### 2、继承ViewStateSaver，获取所需参数
``` java
public class FrescoViewStateSaver extends ViewStateSaver {

    @Override
    protected void captureViewInfo(View view, Bundle bundle) {
        if (view instanceof GenericDraweeView) {
            int actualScaleTypeInt = scaleTypeToInt(((GenericDraweeView)view).getHierarchy().getActualImageScaleType())
            bundle.putInt("scaleType",actualScaleTypeInt);
        }
    }
    
    public ScalingUtils.ScaleType getScaleType(Bundle bundle) {
        int scaleType = bundle.getInt("scaleType", 0);
        return intToScaleType(scaleType);
    }
}
``` 
#### 3、自定义Transition
``` java
public class AdvancedDraweeTransition extends Transition {
    private ScalingUtils.ScaleType mFromScale;
    private ScalingUtils.ScaleType mToScale;

    public AdvancedDraweeTransition() {
        addTarget(GenericDraweeView.class);
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        ...
        ShareElementInfo shareElementInfo = ShareElementInfo.getFromView(transitionValues.view);
        mFromScale = ((FrescoViewStateSaver) shareElementInfo.getViewStateSaver()).getScaleType(viewInfo);
        ...
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        ...
        ShareElementInfo shareElementInfo = ShareElementInfo.getFromView(transitionValues.view);
        mToScale = ((FrescoViewStateSaver) shareElementInfo.getViewStateSaver()).getScaleType(viewInfo);
        ...
    }

    @Override
    public Animator createAnimator(
            ViewGroup sceneRoot,
            TransitionValues startValues,
            TransitionValues endValues) {
        ..
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = (float) animation.getAnimatedValue();
                scaleType.setValue(fraction);
                if (draweeView.getHierarchy().getActualImageScaleType() != scaleType) {
                    draweeView.getHierarchy().setActualImageScaleType(scaleType);
                }
            }
        });
        ...
        return animator;
    }
}
```
#### 4、使用自定义的Transition
``` java
public class FrescoShareElementTransitionfactory extends DefaultShareElementTransitionFactory {
    @Override
    protected TransitionSet buildShareElementsTransition(List<View> shareViewList) {
        TransitionSet transitionSet =  super.buildShareElementsTransition(shareViewList);
        transitionSet.addTransition(new AdvancedDraweeTransition());
        return transitionSet;
    }
}
```
``` java
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ...
        YcShareElement.setEnterTransitions(this, this,true,new FrescoShareElementTransitionfactory());
        ...
    }
```
