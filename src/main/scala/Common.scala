import com.mongodb.casbah.Imports._
import Book._

object Common {
  def buildMongoDbObject(book: Book): MongoDBObject = {
    val builder = MongoDBObject.newBuilder
    builder += "id" -> book.id
    builder += "name" -> book.name
    builder += "publisher" -> book.publisher
    builder += "author" -> book.author
    builder += "isbn10" -> book.isbn10
    builder += "isbn13" -> book.isbn13
    builder += "lang" -> book.lang
    builder += "version" -> book.version
    builder += "pages" -> book.pages
    builder += "desc" -> book.desc
    builder.result
  }
}