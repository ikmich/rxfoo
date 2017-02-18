package org.ikmich.rxfoo;

import android.util.Log;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Class to use to test out the rx works. :)
 */
class Lab {

    public void lab1() {
        // Observable to emit a simple string
        Observable<String> myObservable = Observable.just("Hello");

        // Observer to subscribe to the observable
        Observer<String> myObserver = new Observer<String>() {
            @Override
            public void onCompleted() {
                // Called when the observable has no more data to emit.
            }

            @Override
            public void onError(Throwable e) {
                // Called when the observable encounters an error.
            }

            @Override
            public void onNext(String s) {
                // Called each time the observable emits data.
                Log.d(">>> OBSERVER", s);
            }
        };

        // You can use 'Action1' instead of Observer
        Action1<String> myAction = new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d(">>> ACTION1", s);
            }
        };

        // Subscribe observer
        Subscription mySubscription = myObservable.subscribe(myObserver);

        // Subscribe Action1
        Subscription subscription1 = myObservable.subscribe(myAction);
    }

    public void lab2() {
        Observable<Integer> myArrayObservable = Observable.from(new Integer[]{1, 2, 3, 4, 5, 6});

        myArrayObservable.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.d(">>> Action1", String.valueOf(integer));
            }
        });
    }

    public void map() {
        Observable<Integer> myArrayObservable = Observable.from(new Integer[]{1, 2, 3, 4, 5, 6});
        myArrayObservable = myArrayObservable.map(new Func1<Integer, Integer>() {
            @Override
            public Integer call(Integer integer) {
                return integer * integer;
            }
        });

        myArrayObservable.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.d(">>> Action1", String.valueOf(integer));
            }
        });
    }

    void map2() {
        Observable<String> stringObservable = Observable.just("SMA Gold");
        stringObservable = stringObservable.map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return "Foo [" + s + "]";
            }
        });

        // Subscribe
        stringObservable.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d(">>> Map2", s);
            }
        });
    }

    void chainSkipFilter() {
        Observable<Integer> integerObservable = Observable.from(new Integer[]{1, 2, 3, 4, 5, 6});
        integerObservable = integerObservable.skip(2)
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer % 2 == 0;
                    }
                });

        // Subscribe
        integerObservable.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.d(">>> IntegerObservable", String.valueOf(integer));
            }
        });
    }
}
