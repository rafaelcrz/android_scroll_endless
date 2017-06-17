package com.github.rafaelcrz.android_endless_scroll_lib;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Rafael Felipe on 10/06/2017.
 */

public class ScrollEndless {

    private Context context;
    private RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;

    private int visibleItemCount = 0;
    private int totalItemCount = 0;
    private int pastItemVisible = 0;

    private int totalPage = 1;
    private int nextPage = 1;

    private ProgressDialog progressDialog;
    private boolean isLoading = false;

    /**
     * Endless construct
     *
     * @param context       Application context
     * @param recyclerView  The recyclerView.
     * @param layoutManager The LayoutManager
     */
    public ScrollEndless(Context context, RecyclerView recyclerView, LinearLayoutManager layoutManager) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.layoutManager = layoutManager;
    }

    /**
     * Call the scroll endless
     *
     * @param endlessScrollListener EndlessListener call the onloadMore and onLoadAllFinsih
     */
    public void addScrollEndless(final EndlessScrollListener endlessScrollListener) {
        this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {//scrollDown
                    visibleItemCount = layoutManager.getChildCount(); //Total visivle intens
                    totalItemCount = layoutManager.getItemCount(); //Total list item
                    pastItemVisible = layoutManager.findFirstVisibleItemPosition(); //Next/First visible item
                    if (!isLoading) {
                        if ((visibleItemCount + pastItemVisible) >= totalItemCount) {
                            if (nextPage < totalPage) {
                                endlessScrollListener.onLoadMore();
                            } else if (nextPage == totalPage) {
                                endlessScrollListener.onLoadAllFinish();
                            }
                        }
                    }
                }
            }
        });
    }

    public void addScrollManagerDirection(final ScrollManagerDirectionListener managerScrollListenerDirectionListener) {
        this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    managerScrollListenerDirectionListener.onScrollUp();
                } else {
                    managerScrollListenerDirectionListener.onScrollDown();
                }
            }
        });

    }

    /**
     * Set the total Page. Default is 1
     *
     * @param totalPage number total page
     */
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    /**
     * Show a ProgressDialog
     *
     * @param title      Progress title
     * @param message    Progress Message
     * @param cancelable Cancelable
     */
    public void showProgressDialog(String title, String message, boolean cancelable) {
        progressDialog = new ProgressDialog(this.context);
        progressDialog.setCancelable(cancelable);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    /**
     * CLose the ProgressDialog
     */
    public void closeProgressDialog() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    /**
     * Loading status
     *
     * @param loading - if is loading true or not false
     */
    public void isLoading(boolean loading) {
        this.isLoading = loading;
    }

    /**
     * Set the page
     *
     * @param pagina page
     */
    public void setPage(int pagina) {
        this.nextPage = pagina;
    }

    /**
     * Get the page
     *
     * @return page
     */
    public int getPage() {
        return this.nextPage;
    }

    /**
     * Get total page
     *
     * @return total page - default is 1
     */
    public int getTotalPage() {
        return this.totalPage;
    }


    /**
     * Endless Scroll, for using with pagination
     */
    public interface EndlessScrollListener {

        void onLoadMore(); //Load the next page

        void onLoadAllFinish(); //Finish load data. All pages are loaded

    }

    /**
     * Manager the Scroll Direction - up;down
     */
    public interface ScrollManagerDirectionListener {

        void onScrollUp(); //do something when is to up

        void onScrollDown(); //do something when is to down

    }

}
