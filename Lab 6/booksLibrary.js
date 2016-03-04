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

Library.prototype.isBorrowed = function(book)
{
	
}

Library.prototype.display = function()
{
	var books = 0;
	var s;
	
	for(var i = 0; i < shelfs.length; ++i)
	{
		if(shelfs[i].size > books)
			books = shelfs[i].size;
	}
	s = "<table id=\"myTable\" border=3 width = 300px height = 500px>"
	for(var i = 0; i < books; ++i)
	{
		s += "<tr>";
		for(var j = 0; j < shelfs.length; ++j)
		{
			s += "<td>" + shelfs[j].books[i].bookName + "</td>";
		}
		s += "</tr>";
	}
	s += "</table>";
	return s;
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

