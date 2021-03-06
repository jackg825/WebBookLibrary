package bookshelf

import scala.collection.mutable.ArrayBuffer
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
    builder += "img" -> book.img
    builder.result
  }

  def convertDbObjectToBook(obj: MongoDBObject): Book = {
    val name = obj.getAs[String]("name").get
    val author = obj.getAs[String]("author").get
    val isbn10 = obj.getAs[String]("isbn10").get
    val isbn13 = obj.getAs[String]("isbn13").get
    val desc = obj.getAs[String]("desc").get
    val img = obj.getAs[String]("img").get
    Book(name, author, isbn10, isbn13, desc, img)
  }

  def allBook(): ArrayBuffer[ArrayBuffer[Book]] = {
    val collection = MongoFactory.collection
    val result = collection.find()
    type Row = ArrayBuffer[Book]
    var RowOfBook = new Row
    val ArrayOfRows = new ArrayBuffer[Row]

    var count = 0
    for( obj <- result ) {
      RowOfBook += convertDbObjectToBook(obj)
      if(count % 4 == 3) {
        ArrayOfRows += RowOfBook
        RowOfBook = new Row
      }
      count += 1
    }

    if(count%4 == 0)  RowOfBook = new Row
    RowOfBook += Book("addSign","add","add","add","add","http://i.imgur.com/8aCUyuI.jpg")
    ArrayOfRows += RowOfBook

    ArrayOfRows
  }

  def findBook(isbn: String): Book = {
    if(isbn == "")
      throw new Exception("cannot find with empty input")

    var isbnType = "isbn13"
    if(isbn.length() == 10) {
      isbnType = "isbn10"
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

    var isbnType = "isbn13"
    if(isbn.length() == 11) {
      isbnType = "isbn10"
    }
    val query = MongoDBObject(isbnType -> isbn)
    val result = MongoFactory.collection.findAndRemove(query)
  }

  def updateBook(book : Book){
    val collection = MongoFactory.collection
    val query = MongoDBObject("isbn10" -> book.isbn10)

    collection.findAndModify(query, buildMongoDbObject(book))
  }

}