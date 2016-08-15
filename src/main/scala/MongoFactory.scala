package mongoFactory

import com.mongodb.casbah.MongoCollection
import com.mongodb.casbah.MongoConnection

object MongoFactory {
  private val SERVER = "localhost"
  private val PORT   = 27017
  private val DATABASE = "library"
  private val COLLECTION = "books"
  val connection = MongoConnection(SERVER)
  val collection = connection(DATABASE)(COLLECTION)

  def getConnection: MongoConnection = return MongoConnection(SERVER)
  def getCollection(conn: MongoConnection): MongoCollection = return conn(DATABASE)(COLLECTION)
  def closeConnection(conn: MongoConnection) { conn.close }
}