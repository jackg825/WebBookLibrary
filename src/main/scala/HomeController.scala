import com.twitter._
import com.twitter.finatra.http._
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import book._
import bookshelf._

class HomeController extends Controller {

  get("/:*") { request: Request =>
    response.ok.fileOrIndex(
      filePath = request.params("*"),
      indexPath = "index.html")
  }

  post("/create") { inputBook: Book =>
      BookShelf.saveBook(inputBook)
      "Book name : " + inputBook.name +
      "\nBook author : " + inputBook.author +
      "\nBook ISBN-10 : " + inputBook.isbn10 +
      "\nBook ISBN-13 : " + inputBook.isbn13 +
      "\nBook desc : " + inputBook.desc
  }

  post("/test") { request: Request =>
    "you searched for " + request.params("val1")
  }
}
