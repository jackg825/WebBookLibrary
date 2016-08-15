import com.twitter.finagle.http.Status._
import com.twitter.finatra.http.EmbeddedHttpServer
import com.twitter.inject.server.FeatureTest
import bookshelf.BookShelf
import book._

class ExampleFeatureTest extends FeatureTest {

  override val server = new EmbeddedHttpServer(new ExampleServer)

  // create some Book instances
  val introToAlgorithms = new Book("Introduction to Algorithms", "Thomas H. Cormen",
    "0262033844", "978-0262033848", "Algorithms")
  val operatingSystem = new Book("Operating System Concepts", "Abraham Silberschatz",
    "1118129385", "978-1118129388", "Operating System")
  val artificialIntel = new Book("Artificial Intelligence: A Modern Approach", "Stuart Russell",
    "0136042597", "978-0136042594", "Artificial Intelligence")

  // save them to the mongodb database
  BookShelf.saveBook(introToAlgorithms)
  BookShelf.saveBook(operatingSystem)
  BookShelf.saveBook(artificialIntel)

  "Create Book with Post" in {
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

  "Find Book with Post" in {
    server.httpPost(
      path = "/find",
      postBody =
        """1118129385""",
      andExpect = Ok,
      withBody = "Book name : Operating System Concepts\n" +
        "Book author : Abraham Silberschatz\n" +
        "Book ISBN-10 : 1118129385\n" +
        "Book ISBN-13 : 978-1118129388" +
        "\nBook desc : Operating System")
  }
}
