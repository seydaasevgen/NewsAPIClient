package com.seyda.newsapiclient.data.api

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.Charset

//We need to create object reference variables for NewsAPIService and MockWebServer
class NewsAPIServiceTest {
    private lateinit var service: NewsAPIService
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()

        //Then let's construct the service using Retrofit Builder
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsAPIService::class.java)
    }

    //The first request to the MockWebServer will be replied with the first enqueued response,
    //Second with the second response

    //This function would have The file name as a parameter
    private fun enqueueMockServer(
        fileName: String
    ) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        //Now we need to get the data source from the stream and set it into memory buffer
        val source = inputStream.source().buffer()
        //then create an instance of MockWebServer
        val mockResponse = MockResponse()
        //After that set the body of the MockResponse passing the string format of the source
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        //Finally enqueue the mock response to the mock web server instance
        server.enqueue(mockResponse)
    }

    @Test
    fun getTopHeadlines_sentRequest_receivedExpected() {
        //runBlocking is the coroutine builder we use for testing
        //this runs a new coroutine and blocks the current thread until its completion
        runBlocking {
            //First of all, we need to enqueue the mock response passing the local json file name
            enqueueMockServer("newsresponse.json")
            val responseBody = service.getTopHeadlines("us", 1).body()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/v2/top-headlines?country=us&page=1&apiKey=92e15010e9114f58983243ecfd6e33f4")
        }
    }

    @Test
    fun getTopHeadlines_receivedResponse_correctPageSize() {
        runBlocking {
            enqueueMockServer("newsresponse.json")
            val responseBody = service.getTopHeadlines("us", 1).body()
            //get the list of articles from the response body
            val articlesList = responseBody!!.articles
            assertThat(articlesList.size).isEqualTo(20)
        }
    }

    @Test
    fun getTopHeadlines_receivedResponse_correctContent() {
        runBlocking {
            enqueueMockServer("newsresponse.json")
            val responseBody = service.getTopHeadlines("us", 1).body()
            //get the list of articles from the response body
            val articlesList = responseBody!!.articles
            val article = articlesList[0]
            assertThat(article.author).isEqualTo("Alex Sherman")
            assertThat(article.url).isEqualTo("https://www.cnbc.com/2024/02/03/paramount-sale-talks-why-shari-redstone-needs-the-right-deal.html")
            assertThat(article.publishedAt).isEqualTo("2024-02-03T13:00:01Z")
        }
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}