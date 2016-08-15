import com.twitter.finagle.http.Status._
import com.twitter.finatra.http.EmbeddedHttpServer
import com.twitter.inject.server.FeatureTest

class ExampleFeatureTest extends FeatureTest {

  override val server = new EmbeddedHttpServer(new ExampleServer)

  "Create Book for Post" in {
    server.httpPost(
      path = "/create",
      postBody =
        """
          {
            "name" : "Introduction to Algorithms",
            "author" : "Thomas H. Cormen",
            "isbn10" : "0262033844",
            "isbn13" : "978-0262033848",
            "desc" : "Algorithms"
          }
        """,
      andExpect = Ok,
      withBody = "Book name : Introduction to Algorithms\nBook author : Thomas H. Cormen\n" +
        "Book ISBN-10 : 0262033844\nBook ISBN-13 : 978-0262033848\nBook desc : Algorithms")
  }

}
