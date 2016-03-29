package rxjava;

import rx.Observable;
import rx.schedulers.Schedulers;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Administrator on 2016/3/26.
 */
public class Wikipedia {
    public static Observable fetchWikipediaArticleAsynchronously(String... strings) {
        return Observable.create(subscriber -> {
            new Thread(() -> {
                for (String articleName : strings) {
                    if (subscriber.isUnsubscribed()) {
                        return;
                    }
                    try {
                        subscriber.onNext(new URL("http://en.wikipedia.org/wiki/" + articleName).getContent());
                    } catch (IOException e) {
                        subscriber.onError(e);
                    }
                }
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }).start();
        });
    }

    public static void main(String[] args) {
        Observable.just("test1").subscribe(System.out::println);

        Observable.just("observeOn").observeOn(Schedulers.computation()).subscribe(System.out::println);
//        Observable.just("subscribeOn").subscribeOn(Schedulers.computation()).subscribe(System.out::println);
//
//        Observable.fromCallable(()->"testCallable").subscribe(System.out::println);
//
//        Observable.create(subscriber -> {
//            for (int i = 0; i < 2; i++) {
//                subscriber.onNext("create"+i);
//            }
//        }).subscribe(System.out::println);
//
//        Observable.timer(5, TimeUnit.SECONDS).subscribe(o -> {
//            System.out.println("test5");
//        });

////        fetchWikipediaArticleAsynchronously("Tiger", "Elephant").subscribe(System.out::println);
        System.out.println("test");
//        System.out.println();
    }
}
