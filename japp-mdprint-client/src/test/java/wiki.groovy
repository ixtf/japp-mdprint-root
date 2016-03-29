def fetchWikipediaArticleAsynchronously(String... wikipediaArticleNames) {
    return Observable.create { subscriber ->
        Thread.start {
            for (articleName in wikipediaArticleNames) {
                if (subscriber.unsubscribed) {
                    return
                }
                subscriber.onNext(new URL("http://en.wikipedia.org/wiki/${articleName}").text)
            }
            if (!subscriber.unsubscribed) {
                subscriber.onCompleted()
            }
        }
        return subscriber
    }
}

fetchWikipediaArticleAsynchronously("Tiger", "Elephant")
        .subscribe { println "--- Article ---\n${it.substring(0, 125)}" }