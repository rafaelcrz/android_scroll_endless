# Android Endless Scroll

This project is a EndlessScroll for using on RecyclerView

Execute the sample in app folder

## Preview

*The ProgressDialog is optional.
![Sample](screen_gif.gif)

## Integrating into your project

This project is available in [JitPack.io](https://jitpack.io/) repository.

## Add into build.gradle
```java
allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
```
## Add into app/build.gradle
```java
dependencies {
  compile 'com.github.rafaelcrz:android_scroll_endless:-SNAPSHOT'
}
```

## Usage

* Configure the RecyclerView with the Adapter and LayoutManager before use the ScrollEndeless
```java
recyclerView.setAdapter(adapter);
recyclerView.setLayoutManager(layoutManager);
```
* Declare ScrollEndless like this
```java
endless = new ScrollEndless(mContext, recyclerView, layoutManager);
```
* This is importante. Make your requestCall before get the EndlessListener. For popule the adapter.
```java
yourRequestCall();
```
* Get the ScrollEndless listener. 
``` java onLoadMore() ``` For call the next page when is available
```java onLoadAllFinish() ``` All pages are load. Last page is called

```java
endless.addScrollEndless(new EndlessListener() {
    @Override
    public void onLoadMore() {
        yourRequestMethod();
    }

    @Override
    public void onLoadAllFinish() {
        //Is the last page. Load all itens
    }
});
```
* In your requestMetohd, is very important set the following methods.
In your requestMethod, before 'response', use it: ```java endless.isLoading(true); ``` The ScrollEndless needs know when the request is executing.
If you want, use it ```java endless.showProgressDialog("title, "message", cancelable: false); ``` for show a simple ProgressDialog, and for close it, use ```java endless.closeProgressDialog() ```

In the onResponse or when the data item are complete in adapter, use 
```java endless.isLoading(false); ``` After the response.
If you want, use it ```java endless.closeProgressDialog(); ``` for close the dialog

Set the next Page (before the increment)
```java
endless.setPage(page);
```
Increment the page
```java
page = endless.getPage() + 1;
```
