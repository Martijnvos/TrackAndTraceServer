DROP PROCEDURE InsertAccount
DROP PROCEDURE InsertPackage
DROP PROCEDURE GetAccount
DROP PROCEDURE LogAccountIn

CREATE PROCEDURE GetAllPackages AS
	SELECT * FROM Package
GO

CREATE PROCEDURE InsertAccount(
	@Username NVARCHAR(450),
	@Password NVARCHAR(MAX),
	@IsEmployee BIT,
	@Address NVARCHAR(MAX),
	@Emailaddress NVARCHAR(450)
) AS
	INSERT INTO Account (username, password, isEmployee, address, emailaddress) 
	VALUES(@Username, @Password, @IsEmployee, @Address, @Emailaddress)
GO

CREATE PROCEDURE GetAccount(
	@ID INT
) AS
	SELECT * FROM Account WHERE ID = @ID
GO

CREATE PROCEDURE GetAccountByUserName(
	@UserName NVARCHAR(MAX)
) AS
	SELECT * FROM Account WHERE username = @UserName
GO

CREATE PROCEDURE UpdateAccount(
	@ID INT,
	@Username NVARCHAR(450),
	@Password NVARCHAR(MAX),
	@IsEmployee BIT,
	@Address NVARCHAR(MAX),
	@Emailaddress NVARCHAR(450)
) AS
	UPDATE Account SET username = @Username, password = @Password, isEmployee = @IsEmployee, address = @Address, emailaddress = @Emailaddress
	WHERE ID = @ID
GO

CREATE PROCEDURE DeleteAccount(
	@ID INT
) AS
	DELETE FROM Account WHERE ID = @ID
GO

CREATE PROCEDURE LogAccountIn(
	@Username NVARCHAR(450),
	@Password NVARCHAR(MAX)
) AS
	SELECT * FROM Account WHERE username = @Username AND password = @Password
GO

CREATE PROCEDURE GetPackage(
	@ID INT
) AS
	SELECT * FROM Package WHERE ID = @ID
GO

CREATE PROCEDURE GetPackageByName(
	@Name NVARCHAR(MAX)
) AS
	SELECT * FROM Package WHERE name = @Name
GO

CREATE PROCEDURE GetAllPackagesOfAccount(
	@ID INT
)
AS
	SELECT * FROM Package WHERE accountID = @ID
GO

CREATE PROCEDURE InsertPackage(
	@AccountID INT,
	@Name NVARCHAR(MAX),
	@FromCompany NVARCHAR(MAX),
	@ShippingType NVARCHAR(MAX),
	@Status NVARCHAR(MAX),
	@Size NVARCHAR(MAX),
	@Weight INT,
	@Contents NVARCHAR(MAX),
	@ExpectedDeliveryDate DATE,
	@LocationLat DECIMAL(8,6),
	@LocationLong DECIMAL(9,6)
) AS
	INSERT INTO Package (accountID, name, fromCompany, shippingType, status, size, weight, 
			contents, expectedDeliveryDate, locationLat, locationLong)
	VALUES(@AccountID, @Name, @FromCompany, @ShippingType, @Status, @Size, @Weight,
		@Contents, @ExpectedDeliveryDate, @LocationLat, @LocationLong)
GO

CREATE PROCEDURE UpdatePackage(
	@ID INT,
	@AccountID INT,
	@Name NVARCHAR(MAX),
	@FromCompany NVARCHAR(MAX),
	@ShippingType NVARCHAR(MAX),
	@Status NVARCHAR(MAX),
	@Size NVARCHAR(MAX),
	@Weight INT,
	@Contents NVARCHAR(MAX),
	@ExpectedDeliveryDate DATE,
	@LocationLat DECIMAL(8,6),
	@LocationLong DECIMAL(9,6)
) AS
	UPDATE Package SET accountID = @AccountID, name = @Name, fromCompany = @FromCompany, shippingType = @ShippingType, status = @Status,
		size = @Size, weight = @Weight, contents = @Contents, expectedDeliveryDate = @ExpectedDeliveryDate, locationLat = @LocationLat,
		locationLong = @LocationLong
	WHERE ID = @ID
GO

CREATE PROCEDURE DeletePackage(
	@ID INT
) AS
	DELETE FROM Package WHERE ID = @ID
GO