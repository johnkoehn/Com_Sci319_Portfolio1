function Library()
{
	this.books1 = [new Book("test1", false), new Book("test2", true)];
	this.shelf1 = new Shelf("Shelf1", "test", 5);
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