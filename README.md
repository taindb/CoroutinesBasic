# CoroutinesBasic
### Introduction to Coroutines on Android and Structured Concurency
Recently, Kotlin is widely adopted by Android Developers. Besides the clean and concise syntax, one of the most important features was introduced by Kotlin is Coroutine.

This topic intends to introduce you to how Kotlin Coroutines on Android solve two primary problems (long-running tasks management and main-safety) and how it makes the concurrency code in Kotlin easier to read, write, and understand.

Furthermore, you will know how Structured Concurrency helps you keep track of the work that's being done.

View the topic's slides

The first example was created to demonstrate: ```Coroutines are like "light-weight" threads```
ThreadAndCoroutinesFragment.kt
```kotlin
     /**
     * Create 10k coroutines and each one print a number after 0.1 second delay
     */
    private fun coroutines() = runBlocking {
        val measureTimeMillis = measureTimeMillis {

            val jobs = List(10_000) {number->
                launch {
                    delay(100)
                    println(number)
                }
            }
            jobs.forEach { it.join() }
        }

        timeDisplayTv.text = "$measureTimeMillis ms"
        println("Total Time = $measureTimeMillis")

    }


    /**
     * Create 10k threads and each one print a number after 0.1 second delay
     */
    private fun threads() = runBlocking {
        val measureTimeMillis = measureTimeMillis {
            val jobs = List(10_000) {number ->
                thread {
                    Thread.sleep(100)
                    println(number)
                }
            }

            jobs.forEach { it.join() }
        }
        timeDisplayTv.text = "$measureTimeMillis ms"
        println("Total Time = $measureTimeMillis")
    }
```
The second example was created to demonstrate: ```Suspend and resume work together to replace the callback```
DocViewModel.kt
```kotlin
    fun userNeedDoc() {
        viewModelScope.launch(coroutineExceptionHandler) {
            fetchDoc()
        }
    }

    private suspend fun fetchDoc() {
        coroutineScope {
            val doc = get("https://developer.android.com")
            docLiveData.postValue(doc)
        }
    }

    private suspend fun get(url: String): String = withContext(Dispatchers.IO) {
        val deferred = network.getDocsWithCoroutineAsync(url)
        deferred.await()
    }
```
