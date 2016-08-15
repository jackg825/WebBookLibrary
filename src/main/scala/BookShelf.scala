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

  // warning: don't use the 'get' method in real-world code
  def convertDbObjectToBook(obj: MongoDBObject): Book = {
    val name = obj.getAs[String]("name").get
    val author = obj.getAs[String]("author").get
    val isbn10 = obj.getAs[String]("isbn10").get
    val isbn13 = obj.getAs[String]("isbn13").get
    val desc = obj.getAs[String]("desc").get
    Book(name, author, isbn10, isbn13, desc)
  }

  def findBook(isbn: String) = {
    var isbnType = "isbn10"
    if(isbn.length() == 15) {
      isbnType = "isbn13"
    }
    val collection = MongoFactory.collection
    val query = MongoDBObject(isbnType -> isbn)
    val result = collection.findOne(query)          // Some
    val book = convertDbObjectToBook(result.get)  // convert it to a Stock

    book
  }

  // our 'save' method
  def saveBook(book: Book) {
    val mongoObj = buildMongoDbObject(book)
    MongoFactory.collection.save(mongoObj)
  }

  def removeBook(objectId : String = "") {
    if(objectId == "")
      throw new Exception("cannot remove empty object")
    val query = MongoDBObject("_id" -> objectId)
    val result = MongoFactory.collection.findAndRemove(query)
  }
/*
  def updateBook(book : Book): Book = {
    // create a new Stock object
    val google = Stock("GOOG", 500)

    // search for an existing document with this symbol
    var query = MongoDBObject("symbol" -> "GOOG")

    // replace the old document with one based on the 'google' object
    val res1 = collection.findAndModify(query, buildMongoDbObject(google))
    println("findAndModify: " + res1)
  }*/

}