# Blurred

An android library that allows the developer to blur images. The blur action can be done either on Bitmaps or on the text content of TextView.  

An example of performing the blur on an ImageView is:  
```
ImageView iv = findViewById(R.id.iv_blur_image);  
Blurry.with(getApplicationContext())  
    .load(R.drawable.sample)  
    .fogginess((int) l)  
    .in(iv);  
```

An example to perform blur action on the text content of a TextView is:  

```
TextView tv = findViewById(R.id.tv_sample_text);
Blurry.with(getApplicationContext())  
    .load(tv)  
    .blur(TextViewBlur.BlurMode.OUTSIDE, 20);  
```  

You can include this in your code:

```   
dependencies {
    implementation 'com.github.wwdablu:Blurred:x.y.z'
}
```   

![Sample](https://github.com/wwdablu/Blurred/blob/master/screenshot/blurry.gif)
