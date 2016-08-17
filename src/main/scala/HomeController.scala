import scala.collection.mutable.ArrayBuffer
import com.twitter._
import com.twitter.finatra.http._
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import book._
import bookshelf._
import com.twitter.finatra.response.Mustache
import com.twitter.server.util.JsonConverter


class HomeController extends Controller {
  val address = "http://localhost:7070"

  @Mustache("indexView")
  case class indexView(arrayOfBookArray: ArrayBuffer[ArrayBuffer[Book]])
  get("/") { request: Request =>
    indexView(BookShelf.allBook())
  }

  post("/") { request: Request =>
    response.
      temporaryRedirect.
      location(address + "/b/" + request.params("isbn")).toFuture
  }

  @Mustache("bookInfo")
  case class infoView(book: Book)
  @Mustache("bookAdd")
  case class addView()
  @Mustache("bookEdit")
  case class editView(book: Book)

  get("/b/add") { request: Request =>
      response.
        temporaryRedirect.
        location(address + "/add")
  }

  get("/b/:isbn") { request: Request =>
    infoView(BookShelf.findBook(request.params("isbn")))
  }

  post("/b/:isbn") { request: Request =>
    infoView(BookShelf.findBook(request.params("isbn")))
  }


  get("/add") { request: Request =>
    addView()
  }

  post("/add") { request: Request =>
    val inputBook = Book(
                      request.params("name"),
                      request.params("author"),
                      request.params("isbn10"),
                      request.params("isbn13"),
                      request.params("desc"),
                      request.params("img"))
    BookShelf.saveBook(inputBook)

    response.
      temporaryRedirect.
      location(address + "/b/" + inputBook.isbn13).toFuture
  }

  get("/delete/:isbn") { request: Request =>
    BookShelf.removeBook(request.params("isbn"))

    response.
      temporaryRedirect.
      location(address + "/")
  }

  get("/edit/:isbn") { request: Request =>
    editView(BookShelf.findBook(request.params("isbn")))
  }

  post("/edit/:isbn") { request: Request =>
    val editBook = Book(
                  request.params("name"),
                  request.params("author"),
                  request.params("isbn10"),
                  request.params("isbn13"),
                  request.params("desc"),
                  request.params("img"))

    BookShelf.updateBook(editBook)

    response.
      temporaryRedirect.
      location(address + "/b/" + editBook.isbn13).toFuture
  }

  post("/create") { inputBook: Book =>
    BookShelf.saveBook(inputBook)
    "Book name : " + inputBook.name +
    "\nBook author : " + inputBook.author +
    "\nBook ISBN-10 : " + inputBook.isbn10 +
    "\nBook ISBN-13 : " + inputBook.isbn13 +
    "\nBook desc : " + inputBook.desc +
    "\nBook img : " + inputBook.img
  }

  post("/find") { inputString: String =>
    val outputBook = BookShelf.findBook(inputString)
    "Book name : " + outputBook.name +
    "\nBook author : " + outputBook.author +
    "\nBook ISBN-10 : " + outputBook.isbn10 +
    "\nBook ISBN-13 : " + outputBook.isbn13 +
    "\nBook desc : " + outputBook.desc +
    "\nBook img : " + outputBook.img
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
    "\nBook desc : " + inputBook.desc +
    "\nBook img : " + inputBook.img
  }
}
