import com.twitter.finagle.http.{Response, Request}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter
import bookshelf._

object ExampleServerMain extends ExampleServer

class ExampleServer extends HttpServer {

  override val disableAdminHttpServer = true

  override def defaultFinatraHttpPort = ":7070"

  override def configureHttp(router: HttpRouter) {
    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .add[HomeController]
  }

}
