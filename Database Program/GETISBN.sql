CREATE FUNCTION `searchbook` (itemnum int) RETURNS varchar(45)

BEGIN
declare ISBN varchar(45);
SET ISBN = null;
SELECT 
    ISBN
FROM
    book
WHERE
    itemnum = itemnumber INTO ISBN;
RETURN ISBN;
END
