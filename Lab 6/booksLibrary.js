function Library()
{
	var books1 = [new Book("book5", false), new Book("book70", true), new Book("book23", true), new Book("book9", false), new Book("book1", true)];
	var books2 = [new Book("book9", false), new Book("book72", true), new Book("book3", true), new Book("book80", true), new Book("book12", true), new Book("book45", false), new Book("book21", true)];
	var books3 = [new Book("book3", false), new Book("book3", true), new Book("book42", true), new Book("book1", true), new Book("book21", false), new Book("book6", true), new Book("book7", true), new Book("book2", false), new Book("book4", true)];
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
		s += "<td bgcolor = \"#56c61d\" align = \"center\">" + shelfs[i].shelfName + "</td>";
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
				s += "<td id = \"book\" bgcolor = \"ecf475\" align = \"center\">" + shelfs[j].books[i].bookName + "</td>";
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

