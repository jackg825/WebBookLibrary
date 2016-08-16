import com.twitter._
import com.twitter.finatra.http._
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.finatra.response.Mustache


class ResourcesController extends Controller {
  get("/resources/:*") { request: Request =>
    response.ok.file(request.params("*"))
  }
}
