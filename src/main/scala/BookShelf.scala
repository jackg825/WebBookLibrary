package bookshelf

import com.mongodb.casbah.Imports._
import com.mongodb.casbah._
import mongoFactory._
import book._


object BookShelf {
  val conn = MongoFactory.getConnection

  def buildMongoDbObject(book: Book): MongoDBObject = {
    val builder = MongoDBObject.newBuilder
    builder += "name" -> book.name
    builder += "author" -> book.author
    builder += "isbn10" -> book.isbn10
    builder += "isbn13" -> book.isbn13
    builder += "desc" -> book.desc
    builder.result
  }

  def convertDbObjectToBook(obj: MongoDBObject): Book = {
    val name = obj.getAs[String]("name").get
    val author = obj.getAs[String]("author").get
    val isbn10 = obj.getAs[String]("isbn10").get
    val isbn13 = obj.getAs[String]("isbn13").get
    val desc = obj.getAs[String]("desc").get
    Book(name, author, isbn10, isbn13, desc)
  }

  def findBook(isbn: String): Book = {
    if(isbn == "")
      throw new Exception("cannot find with empty input")

    var isbnType = "isbn10"
    if(isbn.length() == 15) {
      isbnType = "isbn13"
    }
    val collection = MongoFactory.collection
    val query = MongoDBObject(isbnType -> isbn)
    val result = collection.findOne(query)
    val book = convertDbObjectToBook(result.get)

    book
  }

  def saveBook(book: Book) {
    val mongoObj = buildMongoDbObject(book)
    MongoFactory.collection.save(mongoObj)
    mongoObj.get("_id")
  }

  def removeBook(isbn: String) {
    if(isbn == "")
      throw new Exception("cannot remove empty object")

    var isbnType = "isbn10"
    if(isbn.length() == 15) {
      isbnType = "isbn13"
    }
    val query = MongoDBObject(isbnType -> isbn)
    val result = MongoFactory.collection.findAndRemove(query)
  }

  def updateBook(book : Book){
    val collection = MongoFactory.collection
    val query = MongoDBObject("isbn10" -> book.isbn10)

    val resu1t = collection.findAndModify(query, buildMongoDbObject(book))
    println("findAndModify: " + resu1t)
  }

}