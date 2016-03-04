function Library()
{
	var books1 = [new Book("book1", false), new Book("book1", true), new Book("book2", true), new Book("book3", true), new Book("book4", false), new Book("book4", true), new Book("book4", true), new Book("book5", false), new Book("book1", true)];
	var books2 = [new Book("book1", false), new Book("book1", true), new Book("book2", true), new Book("book3", true), new Book("book3", true), new Book("book4", false), new Book("book4", true), new Book("book4", true), new Book("book5", false), new Book("book1", true)];
	var books3 = [new Book("book1", false), new Book("book1", true), new Book("book2", true), new Book("book3", true), new Book("book3", true), new Book("book4", false), new Book("book4", true), new Book("book4", true), new Book("book5", false), new Book("book1", true)];
	this.shelfs = [new Shelf("Shelf1", books1, 10), new Shelf("Shelf2", books2, 10), new Shelf("Shelf3", books3, 10)];
	
}

Library.prototype.getShelfs = function() 
{
	return  this.shelfs;
}

Library.prototype.isBorrowed = function(bookName)
{
	//we need to find a book with the same name and than see if it is available
	for(i = 0; i < shelfs.length; i++)
	{
		for(j = 0; j < shelfs[i].books.length; j++)
		{
			if(shelfs[i].books[j].bookName == bookName)
			{
				return shelfs[i];
			}
		}
		
	}
	return false;
}

Library.prototype.display = function()
{
	var books = 0;
	var s;
	
	
	s = "<table id=\"myTable\" border=3 width = 300px height = 500px>"
	s += "<tr>";
	for(var i = 0; i < shelfs.length; ++i)
	{
		if(shelfs[i].size > books)
			books = shelfs[i].size;
		s += "<td>" + shelfs[i].shelfName + "</td>";
	}
	s+= "</tr>";
	for(i = 0; i < books; ++i)
	{
		s += "<tr>";
		for(var j = 0; j < shelfs.length; ++j)
		{
			if(i >= shelfs[j].books.length)
				s += "<td></td>";
			else
				s += "<td id = \"book\">" + shelfs[j].books[i].bookName + "</td>";
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

