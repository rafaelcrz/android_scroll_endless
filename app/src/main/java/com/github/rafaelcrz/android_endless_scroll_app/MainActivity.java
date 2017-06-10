package com.github.rafaelcrz.android_endless_scroll_app;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.github.rafaelcrz.android_endless_scroll_lib.EndlessListener;
import com.github.rafaelcrz.android_endless_scroll_lib.ScrollEndless;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final long REQUEST_CALL_TIME = 2000;
    private EditText edTotalPages;
    private Button btOk;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    private ListItensAdapter adapter;

    private ScrollEndless endless;
    private int page =1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edTotalPages = (EditText) findViewById(R.id.edPages);
        btOk = (Button) findViewById(R.id.btOk);
        btOk.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        //Adapter
        adapter = new ListItensAdapter(this, new ArrayList<String>());
        recyclerView.setAdapter(adapter);

        //Endless instance
        endless = new ScrollEndless(this, recyclerView, layoutManager);


        //before the EndlessListener make your call
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

    }


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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btOk) {
            //Make a new call clicling button
            if (!edTotalPages.getText().toString().equals("")) {
                int total = Integer.parseInt(edTotalPages.getText().toString());
                //Set the total page (geting from edittext)
                endless.setTotalPage(total);
                //Set the start page in 1
                page = 1;
                //Clear the list
                adapter.clear();
                //Make the call
                makeCallSample();
            }
        }
    }
}
