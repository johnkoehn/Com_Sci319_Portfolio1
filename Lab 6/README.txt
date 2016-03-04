We created three classes, library, shelf and books


Library:
shelfs is an array of Shelf objects

--Library creates three shelf objects

Methods:
getShelfs returns the array of shelfs

isBorrowed takes in a book name and checks if the book is avilable in the library (i.e. not checked out).
if it is avilable it returns the shelf its avilable in

display builds the html code to repersent the library

Shelf:
shelfName is the name of the shelf
books is an array of Book objects
size is the number of books the shelf can hold

Books:
bookName is the name of the book
avilable is weather the book is avilable
