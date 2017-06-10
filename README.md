# Android Endless Scroll

This project is a EndlessScroll for using on RecyclerView

## Usage

* Config your RecyclerView, setting the Adapter and LayoutManager

```java
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
