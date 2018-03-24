DROP TABLE Account
DROP TABLE Package

CREATE TABLE Account (
	ID		INT		IDENTITY(1, 1)	PRIMARY KEY,
	username	NVARCHAR(450)	NOT NULL UNIQUE,
	password	NVARCHAR(MAX)	NOT NULL,
	isEmployee	BIT		NOT NULL DEFAULT 0,
	address		NVARCHAR(MAX)	NOT NULL,
	emailaddress	NVARCHAR(450)	NOT NULL UNIQUE,
	CONSTRAINT EmailConstraint	CHECK (emailaddress LIKE '%_@__%.__%')
);

CREATE TABLE Package (
	ID			INT		IDENTITY(1,1)	PRIMARY KEY,
	accountID		INT		NOT NULL	REFERENCES Account(ID),
	name			NVARCHAR(MAX),
	fromCompany		NVARCHAR(MAX),
	shippingType		NVARCHAR(MAX)	NOT NULL,
	status			NVARCHAR(MAX)	NOT NULL,
	size			NVARCHAR(MAX)	NOT NULL,
	weight			INT		NOT NULL,
	contents		NVARCHAR(MAX),
	expectedDeliveryDate	DATE		NOT NULL,
	locationLat		DECIMAl(8,6)	NOT NULL,
	locationLong		DECIMAL(9,6)	NOT NULL
);