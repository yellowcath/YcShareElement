[原理分析](https://www.jianshu.com/p/fa1c8deeaa57)  

[README（中文）](https://github.com/yellowcath/YcShareElement/blob/master/README_CN.md)
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
>In demo,  
Show the videos with[GSYVideoPlayer](https://github.com/CarGuo/GSYVideoPlayer)  
Show the images with [Fresco](https://github.com/facebook/fresco) and [Glide](https://github.com/bumptech/glide)

YcShareElement provides two demos, one is the contact demo above，another implements a complex ShareElement animation of images and video mixes，see image below

![image](https://github.com/yellowcath/YcShareElement/raw/master/readme/se.gif)

The key points here are as follows：  
1、ShareElement animation of Glide pictures 
In the animation, imageView will go through the default background color -> small thumbnail -> big picture three stages. How to achieve a seamless switch in these three stages  
Reference:[ChangeOnlineImageTransition](https://github.com/yellowcath/YcShareElement/blob/master/ycshareelement/src/main/java/com/hw/ycshareelement/transition/ChangeOnlineImageTransition.java)  
2、ShareElement animation of Fresco pictures  
Fresco provides the built-in DraweeTransition, but if you set the thumbnail, the image will be distorted, and must provide animation-starting ScaleType information in the constructor, which isn't very nice in complex situations.  
Reference:[AdvancedDraweeTransition](https://github.com/yellowcath/YcShareElement/blob/master/ycshareelement/src/main/java/com/hw/ycshareelement/transition/AdvancedDraweeTransition.java)  
3、ShareElement animation of videos  
And this is actually pretty simple after we've done the first two points, we're actually animating the cover of video.

### Simple Usage

#### 1、Turn on the switch of WindowContentTransition
``` java
YcShareElement.enableContentTransition(getApplication());  
``` 
The switch is turned on by default
#### 2、Generate a bundle, and then startActivity
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
#### 3、Set up and start transition on the new page
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
>YcShareElement.setEnterTransition() will suspend activity transtion animation by default,until the call of YcShareElement.startTransition(),in this simple page where you don't have to wait for ShareElement to load, you can pass the third parameter to false, without pausing ActivityB's transition animation

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

Here's the result：  
![image](https://github.com/yellowcath/YcShareElement/raw/master/readme/contacts2.gif)

### Advanced Usage(Complex page with pictures and videos)

#### 1、Turn on the switch of WindowContentTransition
``` java
    YcShareElement.enableContentTransition(getApplication());  
``` 
#### 2、Generate a bundle, and then startActivity
``` java
    Bundle options = YcShareElement.buildOptionsBundle(getActivity(), this);
    startActivityForResult(intent, REQUEST_CONTENT, options);
```
#### 3、Set up transition in Activity B
``` java
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        YcShareElement.setEnterTransition(this, this);
        ...
    }
```
#### 4、start transition after the ViewpPager is loaded
``` java
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ...加载数据...
        YcShareElement.postStartTransition(getActivity());
    }
```

The next step is to handle the return animation of the list page

#### 5、Activity B实现finishAfterTransition()函数
``` java
    @Override
    public void finishAfterTransition() {
        YcShareElement.finishAfterTransition(this, this);
        super.finishAfterTransition();
    }
```
#### 6、Activity B implements the finishAfterTransition() function
``` java
    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        YcShareElement.onActivityReenter(this, resultCode, data, new IShareElementSelector() {
            @Override
            public void selectShareElements(List<ShareElementInfo> list) {
                //Slide the list page to the location of the changed ShareElement
                mFragment.selectShareElement(list.get(0));
            }
        });
    }
``` 

### How to implement Transition animation that supports custom views
Here is an example of how to extend Transition to support Fresco

#### 1、Determine required parameters
First, the parameters required for the Transtion animation, namely the ActualImageScaleType, are determined for SimpleDraweeView
#### 2、Inherit the ViewStateSaver to get the required parameters
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
#### 3、Customize the Transition
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
#### 4、Add the custom Transition to YcShareElement
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
        YcShareElement.setShareElementTransitionFactory(new FrescoShareElementTransitionfactory());
        ...
    }
```
