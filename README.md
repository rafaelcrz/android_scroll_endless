# Android Endless Scroll

This project is a EndlessScroll for using on RecyclerView

## Usage

* Config your RecyclerView, setting the Adapter and LayoutManager

```java

int page = 1;

//Your adapter
adapter = new YourAdapter(this, new ArrayList<String>());
recyclerView.setAdapter(adapter);

//Create a new ScrollEndless. Set the a recyclerView and layoutManager
endless = new ScrollEndless(this, recyclerView, layoutManager);

//Before get the ScrollEndless, make your request
makeCallSample();

// Get the ScrollEndless listener
endless.addScrollEndless(new EndlessListener() {
    @Override
    public void onLoadMore() {
        //load more itens/pages
        makeCallSample();
    }

    @Override
    public void onLoadAllFinish() {
        //Is the last page. Load all itens
        Toast.makeText(MainActivity.this, "Load all " + endless.getTotalPage(), Toast.LENGTH_SHORT).show();
	}
});

    //Simule a request call
    private void makeCallSample() {
        //Before the call setting the loading in True
        endless.isLoading(true);
        //Show progress dialog
        endless.showProgressDialog("Loading data...", "Wating", false);
        //Make the call
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Add the items to Adapter
                for (int i = 0; i < 20; i++) {
                    adapter.addItem("Item: " + i + "; Page: " + page, adapter.getItemCount() - 1);
                }
                //CLose the ProgressDialog
                endless.closeProgressDialog();
                //Setting the Loading in false. The call is complete.
                endless.isLoading(false);
                //First set the next Page
                endless.setPage(page);
                //After increment the page
                page = endless.getPage() + 1;
            }
        }, REQUEST_CALL_TIME);
    }
```