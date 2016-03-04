function Library()
{
	var books1 = [new Book("book1", false), new Book("book1", true), new Book("book2", true), new Book("book3", true), new Book("book3", true), new Book("book4", false), new Book("book4", true), new Book("book4", true), new Book("book5", false), new Book("book1", true)];
	var books2 = [new Book("book1", false), new Book("book1", true), new Book("book2", true), new Book("book3", true), new Book("book3", true), new Book("book4", false), new Book("book4", true), new Book("book4", true), new Book("book5", false), new Book("book1", true)];
	var books3 = [new Book("book1", false), new Book("book1", true), new Book("book2", true), new Book("book3", true), new Book("book3", true), new Book("book4", false), new Book("book4", true), new Book("book4", true), new Book("book5", false), new Book("book1", true)];
	this.shelfs = [new Shelf("Shelf1", books1, 10), new Shelf("Shelf2", books2, 10), new Shelf("Shelf3", books3, 10)];
	
}

Library.prototype.getShelfs = function() 
{
	return  this.shelfs;
}

Library.prototype.isBorrowed(book) = function()
{
	
}

Library.prototype.display = function()
{
	
}


function Shelf(shelfName, books, size)
{
	this.shelfName = shelfName;
	this.books = books;
	this.size = size;
}

function Book(bookName, available)
{
	this.bookName = bookName;
	this.available = available;
}