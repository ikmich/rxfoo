package org.ikmich.rxfoo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.view.OnClickEvent;
import rx.android.view.ViewObservable;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.label_output_1)
    TextView output;

    @BindView(R.id.label_output_2)
    TextView output2;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.btn1)
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize ButterKnife binding.
        ButterKnife.bind(this);

        Lab lab = new Lab();
//        lab.lab1();
//        lab.lab2();
//        lab.map();
//        lab.map2();
//        lab.chainSkipFilter();

//        async1();
        async2();

        viewLab1();
    }

    void async1() {
        AsyncLab.LoadFromNetwork.run(new AsyncLab.LoadFromNetwork.InteractionListener() {
            @Override
            public void onInteractionComplete(String data) {
                progressBar.setVisibility(View.GONE);
                output.setText(data);
            }

            @Override
            public void onBeforeInteraction() {
                progressBar.setVisibility(View.VISIBLE);
                output.setText(R.string.loading_from_network);
            }
        });
    }

    void async2() {
        AsyncLab.LoadMultipleFromNetwork.run(new AsyncLab.LoadMultipleFromNetwork.InteractionListener() {
            @Override
            public void onBefore() {
                progressBar.setVisibility(View.VISIBLE);
                output2.setText(R.string.loading_multiple_from_network);
            }

            @Override
            public void onComplete(String data) {
                progressBar.setVisibility(View.GONE);
                output2.setText(data);
            }

            @Override
            public void onComplete1(String data1) {
                output2.setText(String.format(getString(R.string.loaded_data_1), data1));
            }

            @Override
            public void onComplete2(String data2) {
                output2.setText(String.format(getString(R.string.loaded_data_2), data2));
            }
        });
    }

    void viewLab1() {
        Observable<OnClickEvent> clickEventObservable = ViewObservable.clicks(btn1);
        clickEventObservable.subscribe(new Action1<OnClickEvent>() {
            @Override
            public void call(OnClickEvent onClickEvent) {
                App.toast("Clicked.");
            }
        });
    }
}
