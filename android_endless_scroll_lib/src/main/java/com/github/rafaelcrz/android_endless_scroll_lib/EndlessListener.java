package com.github.rafaelcrz.android_endless_scroll_lib;

/**
 * Created by Rafael Felipe on 10/06/2017.
 */

public interface EndlessListener {


    void onLoadMore(); //Load the next page

    void onLoadAllFinish(); //Finish load data. All pages are loaded

}
