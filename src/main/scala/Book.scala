package book

case class Book (var name : String,var author : String, var isbn10 : String, var isbn13 : String, var desc : String, var img : String) {
  def setName(name : String) {
    this.name = name
  }
  def setAuthor(author : String) {
    this.author = author
  }
  def setISBN10(isbn10 : String) {
    this.isbn10 = isbn10
  }
  def setISBN13(isbn13 : String) {
    this.isbn13 = isbn13
  }
  def setDesc(desc : String) {
    this.desc = desc
  }
  def setImg(img : String) {
    this.img = img
  }
}
