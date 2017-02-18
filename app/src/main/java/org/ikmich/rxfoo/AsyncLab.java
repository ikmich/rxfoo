package org.ikmich.rxfoo;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Class to test asynchronous jobs.
 */

class AsyncLab {

    static class LoadFromNetwork {

        interface InteractionListener {
            void onBeforeInteraction();

            void onInteractionComplete(String data);
        }

        static void run(final InteractionListener listener) {
            listener.onBeforeInteraction();

            Observable<String> fetchFromGoogle = Observable.create(new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> subscriber) {
                    // This should be run on a separate thread.
                    try {
                        // Simulate mock blocking network activity.
                        Thread.sleep(5000);
                        String data = "Google search results";
                        subscriber.onNext(data); // Emit contents of the url
                        subscriber.onCompleted(); // Nothing more to emit.
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        // In case of any error.
                        subscriber.onError(e);
                    }
                }
            });

            fetchFromGoogle.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            listener.onInteractionComplete(s);
                        }
                    });
        }
    }

    static class LoadMultipleFromNetwork {

        interface InteractionListener {
            void onBefore();

            void onComplete1(String data1);

            void onComplete2(String data2);

            void onComplete(String data);
        }

        static void run(final InteractionListener listener) {
            listener.onBefore();

            Observable<String> fetchFromNetflix = Observable.create(new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> subscriber) {
                    // Simulate network activity
                    try {
                        Thread.sleep(6000);
                        String data = "Netflix data!";
                        subscriber.onNext(data);
                        subscriber.onCompleted();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        subscriber.onError(e);
                    }
                }
            }).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread());

            fetchFromNetflix.subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    listener.onComplete1(s);
                }
            });

            Observable<String> fetchFromYahoo = Observable.create(new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> subscriber) {
                    // Simulate network activity
                    try {
                        Thread.sleep(3000);
                        String data = "Yahoo data!";
                        subscriber.onNext(data);
                        subscriber.onCompleted();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        subscriber.onError(e);
                    }
                }
            }).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread());

            fetchFromYahoo.subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    listener.onComplete2(s);
                }
            });

            // Create zipped observable to run both Observables in parallel
            Observable<String> zippedObservable = Observable.zip(fetchFromNetflix, fetchFromYahoo, new Func2<String, String, String>() {
                @Override
                public String call(String data1, String data2) {
                    return "Combined data: " + data1 + " | " + data2;
                }
            }).observeOn(AndroidSchedulers.mainThread());

            zippedObservable.subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    listener.onComplete(s);
                }
            });
        }
    }
}
