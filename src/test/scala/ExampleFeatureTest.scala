import com.twitter.finagle.http.Status._
import com.twitter.finatra.http.EmbeddedHttpServer
import com.twitter.inject.server.FeatureTest
import bookshelf.BookShelf
import book._

class ExampleFeatureTest extends FeatureTest {

  override val server = new EmbeddedHttpServer(new ExampleServer)

//  Create example books to database = Start
  val programInScala = new Book("Programming in Scala: A Comprehensive Step-by-Step Guide, 2nd Edition", "Martin Odersky",
    "0981531644", "978-0981531649", "Scala", "http://bit.ly/2bfF3f3")
  val operatingSystem = new Book("Operating System Concepts", "Abraham Silberschatz",
    "1118129385", "978-1118129388", "Operating System", "http://bit.ly/2bfF96a")
  val artificialIntel = new Book("Artificial Intelligence: A Modern Approach", "Stuart Russell",
    "0136042597", "978-0136042594", "Artificial Intelligence", "http://bit.ly/2bDwzDD")

  val itaId = BookShelf.saveBook(programInScala)
  val osId = BookShelf.saveBook(operatingSystem)
  var aiId = BookShelf.saveBook(artificialIntel)
//  Create example books to database - End

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
            "desc" : "Algorithms",
            "img" : "http://bit.ly/2aYdAOm"
          }
        """,
      andExpect = Ok,
      withBody = "Book name : Introduction to Algorithms\n" +
                "Book author : Thomas H. Cormen\n" +
                "Book ISBN-10 : 0262033844\n" +
                "Book ISBN-13 : 978-0262033848\n" +
                "Book desc : Algorithms\n" +
                "Book img : http://bit.ly/2aYdAOm")
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
                "Book ISBN-13 : 978-1118129388\n" +
                "Book desc : Operating System\n" +
                "Book img : http://bit.ly/2bfF96a"
        )
  }

  "Remove Book with Post" in {
    server.httpPost(
      path = "/remove",
      postBody =
        """1118129385""",
      andExpect = Ok)
  }

  "Update Book with Post" in {
    server.httpPost(
      path = "/update",
      postBody =
        """
          {
            "name" : "Artificial Intelligence",
            "author" : "Stuart R.",
            "isbn10" : "0136042597",
            "isbn13" : "978-0136042594",
            "desc" : "Artificial Intelligence: A Modern Approach",
            "img" : "http://bit.ly/2bDwzDD"
          }
        """,
      andExpect = Ok,
      withBody = "Book name : Artificial Intelligence\n" +
                "Book author : Stuart R.\n" +
                "Book ISBN-10 : 0136042597\n" +
                "Book ISBN-13 : 978-0136042594\n" +
                "Book desc : Artificial Intelligence: A Modern Approach\n" +
                "Book img : http://bit.ly/2bDwzDD")

//    Remove Example Books in database - Start
    BookShelf.removeBook("0981531644")
    BookShelf.removeBook("0262033844")
    BookShelf.removeBook("0136042597")
//    Remove Example Books in database - End
  }
}
