CREATE DATABASE JAVA;
GO

USE JAVA;
GO

------- USERS --------

CREATE TABLE Users (
	IDUser INT PRIMARY KEY IDENTITY,
	Username NVARCHAR(100),
	Password NVARCHAR(100),
	Administrator BIT DEFAULT 0
);
GO

CREATE OR ALTER PROC createUser
(
	@Username NVARCHAR(100),
	@Password NVARCHAR(100),
	@Administrator BIT = 0,
	@Success BIT OUT,
	@UserID INT OUT
)
AS
BEGIN
	IF EXISTS(SELECT * FROM Users WHERE Username=@Username)
	BEGIN
		SET @Success = 0
		RETURN
	END

	INSERT INTO Users (Username, Password, Administrator)
		VALUES (@Username, @Password, @Administrator)

	SET @UserID = SCOPE_IDENTITY()
	set @Success = 1
END
GO

CREATE OR ALTER PROC updateUser
(
	@IDUser INT,
	@Username NVARCHAR(100) = NULL,
	@Password NVARCHAR(100) = NULL,
	@Administrator BIT = NULL,
	@Success BIT OUT
)
AS
BEGIN
	IF NOT EXISTS(SELECT * FROM Users WHERE IDUser=@IDUser)
	begin
		SET @Success = 0
		RETURN
	end
	
	IF @Username IS NOT NULL
		UPDATE Users SET Username=@Username WHERE IDUser=IDUser

	IF @Password IS NOT NULL
		UPDATE Users SET Password=@Password WHERE IDUser=IDUser

	IF @Administrator IS NOT NULL
		UPDATE Users SET Administrator=@Administrator WHERE IDUser=IDUser
		
	set @Success = 1
END
GO

CREATE OR ALTER PROC deleteUser
(
	@IDUser INT,
	@Success BIT OUT
)
AS
BEGIN
	DELETE FROM Users WHERE IDUser=@IDUser
	set @Success = 1
END
GO

CREATE OR ALTER PROCEDURE selectUser
	@IDUser INT
AS 
BEGIN 
	SELECT 
		* 
	FROM 
		Users
	WHERE 
		IDUser = @IDUser
END
GO

CREATE OR ALTER PROCEDURE selectUsers
AS 
BEGIN 
	SELECT * FROM Users
END
GO

-- create user --
EXEC createUser 'admin', 'admin', 1, NULL, NULL;
------------------------------------------------------------


