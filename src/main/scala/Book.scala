package book

case class Book (var name : String,var author : String, var isbn10 : String, var isbn13 : String, var desc : String)
{
  def setName(name : String) {
    this.name = name
  }
  def setAuthor(name : String) {
    this.author = author
  }
  def setISBN10(isbn10 : String) {
    this.isbn10 = isbn10
  }
  def setISBN13(isbn13 : String) {
    this.isbn13 = isbn13
  }
  def setDesc(name : String) {
    this.desc = desc
  }
}
