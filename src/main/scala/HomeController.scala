import com.twitter._
import com.twitter.finatra.http._
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import book._
import bookshelf._
import com.twitter.finatra.response.Mustache

class HomeController extends Controller {
  get("/") { request: Request =>
    response.ok.file("index.html")
  }

  @Mustache("foo")
  case class FooView(name: String)

  get("/foo") { request: Request =>
    FooView("abc")
  }

  post("/create") { inputBook: Book =>
    BookShelf.saveBook(inputBook)
    "Book name : " + inputBook.name +
    "\nBook author : " + inputBook.author +
    "\nBook ISBN-10 : " + inputBook.isbn10 +
    "\nBook ISBN-13 : " + inputBook.isbn13 +
    "\nBook desc : " + inputBook.desc
  }

  post("/find") { inputString: String =>
    val outputBook = BookShelf.findBook(inputString)
    "Book name : " + outputBook.name +
    "\nBook author : " + outputBook.author +
    "\nBook ISBN-10 : " + outputBook.isbn10 +
    "\nBook ISBN-13 : " + outputBook.isbn13 +
    "\nBook desc : " + outputBook.desc
  }

  post("/remove") { inputString: String =>
    BookShelf.removeBook(inputString)
  }

  post("/update") { inputBook: Book =>
    BookShelf.updateBook(inputBook)
    "Book name : " + inputBook.name +
    "\nBook author : " + inputBook.author +
    "\nBook ISBN-10 : " + inputBook.isbn10 +
    "\nBook ISBN-13 : " + inputBook.isbn13 +
    "\nBook desc : " + inputBook.desc
  }
}
