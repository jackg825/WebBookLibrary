import com.twitter._
import com.twitter.finatra.http._
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

class HomeController extends Controller {

  get("/:*") { request: Request =>
    response.ok.fileOrIndex(
      filePath = request.params("*"),
      indexPath = "index.html")
  }

  post("/test") { request: Request =>
    "you searched for " + request.params("val1")
  }
}
