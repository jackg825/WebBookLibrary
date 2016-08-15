import com.mongodb.casbah.Imports._
import com.mongodb.casbah._
import Book._
import Common._

class Create {

  // our 'save' method
  def saveBook(book: Book) {
    val mongoObj = buildMongoDbObject(book)
    MongoFactory.collection.save(mongoObj)
  }

  def exampleCreate() {
    // create some Book instances
    val introToAlgorithms = Book(100, "Introduction to Algorithms", "The MIT Press", "Thomas H. Cormen",
      "0262033844", "978-0262033848", "English", 3.0, 1312, "Algorithms")
    val operatingSystem = Book(101, "Operating System Concepts", "Wiley", "Abraham Silberschatz",
      "1118129385", "978-1118129388", "English", 9.0, 944, "Operating System")
    val artificialIntel = Book(102, "Artificial Intelligence: A Modern Approach", "Pearson", "Stuart Russell",
      "0136042597", "978-0136042594", "English", 3.0, 1152, "Artificial Intelligence")

    // save them to the mongodb database
    saveBook(introToAlgorithms)
    saveBook(operatingSystem)
    saveBook(artificialIntel)
  }

}