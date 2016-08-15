package bookshelf

import com.mongodb.casbah.Imports._
import com.mongodb.casbah._
import mongoFactory._
import book._


object BookShelf {

  def buildMongoDbObject(book: Book): MongoDBObject = {
    val builder = MongoDBObject.newBuilder
    builder += "name" -> book.name
    builder += "author" -> book.author
    builder += "isbn10" -> book.isbn10
    builder += "isbn13" -> book.isbn13
    builder += "desc" -> book.desc
    builder.result
  }

  // our 'save' method
  def saveBook(book: Book) {
    val mongoObj = buildMongoDbObject(book)
    MongoFactory.collection.save(mongoObj)
  }

  def exampleCreate() {
    // create some Book instances
    val introToAlgorithms = new Book("Introduction to Algorithms", "Thomas H. Cormen",
      "0262033844", "978-0262033848", "Algorithms")
    val operatingSystem = new Book("Operating System Concepts", "Abraham Silberschatz",
      "1118129385", "978-1118129388", "Operating System")
    val artificialIntel = new Book("Artificial Intelligence: A Modern Approach", "Stuart Russell",
      "0136042597", "978-0136042594", "Artificial Intelligence")

    // save them to the mongodb database
    saveBook(introToAlgorithms)
    saveBook(operatingSystem)
    saveBook(artificialIntel)
  }

}